package com.zooms.dean.push.service;

import com.zooms.dean.common.tool.StringUtils;
import com.zooms.dean.push.domain.Message;
import com.zooms.dean.push.domain.MessageExample;
import com.zooms.dean.push.mapper.MessageMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息管理业务类
 * 
 * @author zhaoljin
 * @date 2018年3月27日
 */
@Component
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 插入消息
     * @param message
     * @return
     */
    @Transactional
    public int addMessage(Message message) {
        return messageMapper.insertSelective(message);
    }

    /**
     * 修改消息
     * @param message
     * @return
     */
    @Transactional
    public int updateMessage(Message message) {
        return messageMapper.updateByPrimaryKeySelective(message);
    }

    /**
     * 按照主键ID查询消息
     * @param id
     * @return
     */
    public Message selectMessage(Integer id) {
        return messageMapper.selectByPrimaryKey(id);
    }

    /**
     * 消息分页查询列表
     * @param offset
     * @param limit
     * @param message
     * @return
     */
    public List<Message> getMessageList(int offset, int limit, Message message) {
        MessageExample example = new MessageExample();
        example.setOrderByClause("create_date DESC");
        RowBounds rowBounds = new RowBounds(offset, limit);
        MessageExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, message);
        return messageMapper.selectByExampleWithRowbounds(example, rowBounds);
    }

    /**
     * 消息总数统计
     * @param message
     * @return
     */
    public Long count(Message message) {
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, message);
        return messageMapper.countByExample(example);
    }

    /**
     * 组装查询条件
     * @param criteria
     * @param message
     */
    void setCriteria(MessageExample.Criteria criteria, Message message) {
        if (message != null) {
            if (message.getId() != null)
                criteria.andIdEqualTo(message.getId());
            if (message.getType() != null)
                criteria.andTypeEqualTo(message.getType());
            if (StringUtils.isNoneBlank(message.getTitle()))
                criteria.andTitleLike("%" + message.getTitle() + "%");
        }
        criteria.andDelFlagEqualTo(Message.DEL_FLAG_NORMAL);
    }

}
