package com.zooms.dean.push.service;

import com.alibaba.fastjson.JSON;
import com.zooms.dean.common.constants.GoType;
import com.zooms.dean.common.constants.MessageTips;
import com.zooms.dean.common.constants.MessageType;
import com.zooms.dean.common.tool.BeanUtils;
import com.zooms.dean.common.tool.DateProcessUtils;
import com.zooms.dean.push.common.AliyunPushTool;
import com.zooms.dean.push.common.Constants;
import com.zooms.dean.push.common.MessageCoverter;
import com.zooms.dean.push.domain.Message;
import com.zooms.dean.push.domain.UserMessage;
import com.zooms.dean.push.domain.UserMessageExample;
import com.zooms.dean.push.mapper.MessageMapper;
import com.zooms.dean.push.mapper.UserMessageExtendsMapper;
import com.zooms.dean.push.mapper.UserMessageMapper;
import com.zooms.dean.push.model.AuthUserBean;
import com.zooms.dean.push.model.Notification;
import com.zooms.dean.push.model.OrderBean;
import com.zooms.dean.push.model.param.MessageParam;
import com.zooms.dean.push.model.result.UserMessageResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户消息管理业务类
 * 
 * @author zhaoljin
 * @date 2018年3月27日
 */
@Component
public class UserMessageService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserMessageService.class);

    @Autowired
    private MessageMapper messageMapper;
    
    @Autowired
    private UserMessageMapper userMessageMapper;
    
    @Autowired
    private UserMessageExtendsMapper userMessageExtendsMapper;
    
    @Autowired
    UserService userService;
    
    @Autowired
    OrderService orderService;
    
    @Autowired
    AliyunPushTool aliyunPushTool;

    /**
     * 插入用户消息
     * @param userMessage
     * @return
     */
    @Transactional
    public int addUserMessage(UserMessage userMessage) {
        return userMessageMapper.insertSelective(userMessage);
    }

    /**
     * 修改用户消息
     * @param userMessage
     * @return
     */
    @Transactional
    public int updateUserMessage(UserMessage userMessage) {
        return userMessageMapper.updateByPrimaryKeySelective(userMessage);
    }

    /**
     * 按照主键ID查询用户消息
     * @param id
     * @return
     */
    public UserMessage selectUserMessage(Integer id) {
        return userMessageMapper.selectByPrimaryKey(id);
    }

    /**
     * 用户消息分页查询列表
     * @param offset
     * @param limit
     * @param userMessage
     * @return
     */
    public List<UserMessage> getUserMessageList(int offset, int limit, UserMessage userMessage) {
        UserMessageExample example = new UserMessageExample();
        example.setOrderByClause("create_date DESC");
        RowBounds rowBounds = new RowBounds(offset, limit);
        UserMessageExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, userMessage);
        return userMessageMapper.selectByExampleWithRowbounds(example, rowBounds);
    }

    /**
     * 用户消息总数统计
     * @param userMessage
     * @return
     */
    public Long count(UserMessage userMessage) {
        UserMessageExample example = new UserMessageExample();
        UserMessageExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, userMessage);
        return userMessageMapper.countByExample(example);
    }

    /**
     * 组装查询条件
     * @param criteria
     * @param userMessage
     */
    void setCriteria(UserMessageExample.Criteria criteria, UserMessage userMessage) {
        if (userMessage != null) {
            if (userMessage.getId() != null)
                criteria.andIdEqualTo(userMessage.getId());
            if (userMessage.getFromUserId() != null)
                criteria.andFromUserIdEqualTo(userMessage.getFromUserId());
            if (userMessage.getToUserId() != null)
                criteria.andToUserIdEqualTo(userMessage.getToUserId());
        }
        criteria.andDelFlagEqualTo(UserMessage.DEL_FLAG_NORMAL);
    }

    /**
     * 获取用户消息
     * @param userMessage
     * @return {@link UserMessageResult}
     */
    public UserMessageResult getUserMessageResult(UserMessage userMessage) {
        UserMessageResult messageResult = new UserMessageResult();
        Message message = messageMapper.selectByPrimaryKey(userMessage.getMessageId());
        BeanUtils.copy(message, messageResult);
        BeanUtils.copy(userMessage, messageResult);
        if (message.getCreateDate() != null) {
            messageResult.setCreateDate(DateProcessUtils.showTimeText(message.getCreateDate()));
        }
        return messageResult;
    }

    /**
     * 全部标记为已读
     * 
     * @param userId 登录用户ID
     */
    @Transactional
    public void remarkAllRead(int userId) {
        UserMessageExample example = new UserMessageExample();
        UserMessageExample.Criteria criteria = example.createCriteria();
        UserMessage userMessage = new UserMessage();
        userMessage.setToUserId(userId);
        userMessage.setStatus(Constants.IS_READ);
        setCriteria(criteria, userMessage);
        userMessageMapper.updateByExampleSelective(userMessage, example);
    }

    /**
     * 获取用户的系统消息(公告等)
     * @param userId 登录用户ID
     * @return List<{@link UserMessageResult}>
     */
    public List<UserMessageResult> getSysMessageList(int userId) {
        List<UserMessage> messageList = userMessageExtendsMapper.getUserMessageListByType(userId, MessageType.SYSTEM);

        List<UserMessageResult> messageResultList = new ArrayList<UserMessageResult>();

        for (UserMessage message : messageList) {
            UserMessageResult messageResult = getUserMessageResult(message);
            messageResultList.add(messageResult);
        }
        return messageResultList;
    }

    /**
     * 获取经销商首页消息列表
     * @param userId 用户ID
     * @return
     */
    public List<UserMessageResult> getDealerIndexMessageList(Integer userId) {
        UserMessageExample example = new UserMessageExample();
        UserMessageExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("ID DESC");
        RowBounds rowBounds = new RowBounds(0, 5);
        UserMessage userMessage = new UserMessage();
        userMessage.setToUserId(userId);
        setCriteria(criteria, userMessage);
        List<UserMessage> messageList = userMessageMapper.selectByExampleWithRowbounds(example, rowBounds);
        
        List<UserMessageResult> messageResultList = new ArrayList<UserMessageResult>();
        
        if (CollectionUtils.isEmpty(messageList)) {
            UserMessageResult messageResult = new UserMessageResult();
            messageResult.setTitle(MessageTips.WELCOME_MESSAGE_TITLE);
            messageResult.setBody(MessageTips.WELCOME_MESSAGE_BODY);
            messageResult.setGoType(GoType.NO_JUMP);
            messageResult.setType(MessageType.SYSTEM);
            messageResultList.add(messageResult);
            return messageResultList;
        }

        for (UserMessage message : messageList) {
            UserMessageResult messageResult = getUserMessageResult(message);
            Integer type = messageResult.getType();
            String value = messageResult.getGoValue();
            String extra = getMessageExtra(type, value);
            messageResult.setExtra(extra);
            messageResultList.add(messageResult);
        }
        return messageResultList;
    }

    /**
     * 根据类型获取扩展参数
     * @param type 消息类型
     * @return 组装后的json字符串
     */
    private String getMessageExtra(Integer type, String value) {
        String extra = null;
        if (type == MessageType.ORDER) { // 订单详情
            Integer orderId = Integer.parseInt(value);
            OrderBean order = orderService.getOrderById(orderId);
            extra = JSON.toJSONString(order);
        } else if (type == MessageType.LOGISTICS) { // 物流详情
            extra = null;
        }
        return extra;
    }
    
    /**
     * 消息入库操作
     * @param messageParam 消息信息 {@link MessageParam}
     * @return
     */
    @Transactional
    public void addUserMessage(MessageParam messageParam) {
        Message message = new Message();
        BeanUtils.copy(messageParam, message);
        UserMessage userMessage = new UserMessage();
        BeanUtils.copy(messageParam, userMessage);
        messageMapper.insertSelective(message);
        userMessage.setMessageId(message.getId());
        userMessageMapper.insertSelective(userMessage);
    }

    /**
     * 消息推送并入库
     * @param message
     */
    public void addAndPush(MessageParam message) {
        Notification notification = MessageCoverter.toNotification(message);
        AuthUserBean userBean = userService.getAuthUserById(message.getToUserId());
        if (StringUtils.isNotEmpty(userBean.getDeviceId())) {
            notification.setDeviceId(userBean.getDeviceId());
            try {
                // 消息推送服务
                aliyunPushTool.pushNoticeToAndroid(notification);
                aliyunPushTool.pushNoticeToIOS(notification);
                // 用户消息入库
                addUserMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("消息推送失败:"+notification.toString());
            }
        }else{
            logger.info("设备ID为空:"+notification.toString());

        }

    }

}
