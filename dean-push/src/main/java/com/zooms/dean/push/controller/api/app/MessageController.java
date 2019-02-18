package com.zooms.dean.push.controller.api.app;

import com.zooms.dean.common.annotation.ApiVersion;
import com.zooms.dean.common.web.Page;
import com.zooms.dean.common.web.Query;
import com.zooms.dean.common.web.View;
import com.zooms.dean.push.common.Constants;
import com.zooms.dean.push.domain.Message;
import com.zooms.dean.push.domain.UserMessage;
import com.zooms.dean.push.model.result.UserMessageResult;
import com.zooms.dean.push.service.MessageService;
import com.zooms.dean.push.service.UserMessageService;
import com.zooms.dean.push.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息管理APP接口
 * @author zlj
 * @date 2018年5月9日
 */
@RestController("apiAppMessage")
@RequestMapping("/{v}/app/message")
public class MessageController {
    
    @Autowired
    UserMessageService userMessageService;
    
    @Autowired
    MessageService messageService;
    
    @Autowired
    UserService userService;
    
    /**
     * 查看消息详情
     * @param request
     * @param response
     * @param id
     * @return
     */
    @ApiVersion(1)
    @RequestMapping("/info/{id}")
    public View<UserMessageResult> info(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
        View<UserMessageResult> view = new View<UserMessageResult>(20000, "获取数据成功");
        
        UserMessageResult message = null;
        if (id != null) {
            UserMessage userMessage = userMessageService.selectUserMessage(id);
            message = userMessageService.getUserMessageResult(userMessage);
            userMessage.setStatus(Constants.IS_READ);
            userMessageService.updateUserMessage(userMessage);
        } else {
            message = new UserMessageResult();
        }

        view.setData(message);
        return view;
    }

    /**
     * 获取消息分页查询列表
     * @param request
     * @param response
     * @param query
     * @return
     */
    @ApiVersion(1)
    @RequestMapping("/list")
    public View<Page<UserMessageResult>> list(HttpServletRequest request, HttpServletResponse response, @RequestBody Query<UserMessage> query) {
        View<Page<UserMessageResult>> view = new View<Page<UserMessageResult>>(20000, "获取列表成功");

        UserMessage userMessage = query.getData();
        
        Integer userId = userService.getLoginUserId();
        if (userMessage == null) {
            userMessage = new UserMessage();
        }
        userMessage.setToUserId(userId);
        
        Page<UserMessageResult> page = new Page<UserMessageResult>(query.getPage(), query.getSize(), userMessageService.count(userMessage));
        
        List<UserMessage> messageList = userMessageService.getUserMessageList(query.getOffset(), query.getLimit(), userMessage);
        
        List<UserMessageResult> messageResultList = new ArrayList<UserMessageResult>();
        
        for (UserMessage message : messageList) {
            UserMessageResult messageResult = userMessageService.getUserMessageResult(message);
            messageResultList.add(messageResult);
        }
        
        page.setContent(messageResultList);
        
        view.setData(page);
        
        return view;
    }

    /**
     * 删除消息
     * @param request
     * @param response
     * @param id
     * @return
     */
    @ApiVersion(1)
    @SuppressWarnings("rawtypes")
    @RequestMapping("/delete/{ids}")
    public View delete(HttpServletRequest request, HttpServletResponse response, @PathVariable("ids") String ids) {
        View view = new View(20000, "删除成功");
        if (StringUtils.isBlank(ids)) {
            return View.ofParamsError("请选择至少一条记录删除");
        }
        for (String id : ids.split(",")) {
            UserMessage message = userMessageService.selectUserMessage(Integer.parseInt(id));
            message.setDelFlag(UserMessage.DEL_FLAG_DELETE);
            this.userMessageService.updateUserMessage(message);
        }

        return view;
    }
    
    /**
     * 全部标记为已读
     * @param request
     * @param response
     * @param id
     * @return
     */
    @ApiVersion(1)
    @SuppressWarnings("rawtypes")
    @RequestMapping("/read/all")
    public View allRead(HttpServletRequest request, HttpServletResponse response) {
        View view = new View(20000, "全部标记为已读成功");
        
        int userId = userService.getLoginUserId();
        userMessageService.remarkAllRead(userId);

        return view;
    }
    
    /**
     * 获取系统通知列表（平台人工）
     * @param request
     * @param response
     * @param query
     * @return
     */
    @ApiVersion(1)
    @RequestMapping("/sys/list")
    public View<List<UserMessageResult>> list(HttpServletRequest request, HttpServletResponse response) {
        View<List<UserMessageResult>> view = new View<List<UserMessageResult>>(20000, "获取列表成功");

        int userId = userService.getLoginUserId();
        
        List<UserMessageResult> messageResultList = userMessageService.getSysMessageList(userId);
       
        view.setData(messageResultList);
        
        return view;
    }
    
    /**
     * 查看系统通知详情（平台人工）
     * @param request
     * @param response
     * @param id 消息ID
     * @return
     */
    @ApiVersion(1)
    @RequestMapping("/sys/info/{id}")
    public View<Message> sysInfo(HttpServletRequest request, HttpServletResponse response,
        @PathVariable("id") Integer id) {
        View<Message> view = new View<Message>(20000, "获取数据成功");

        Message message = null;
        if (id != null) {
            message = messageService.selectMessage(id);
            if (message != null) {
                Integer userId = userService.getLoginUserId();
                UserMessage userMessage = new UserMessage();
                userMessage.setStatus(Constants.IS_READ);
                userMessage.setToUserId(userId);
                userMessageService.addUserMessage(userMessage);
            } else {
                return View.ofParamsError("消息不存在");
            }
        } else {
            message = new Message();
        }

        view.setData(message);
        return view;
    }
    
}