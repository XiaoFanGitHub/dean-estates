package com.zooms.dean.push.controller.api.admin;

import com.zooms.dean.common.annotation.ApiVersion;
import com.zooms.dean.common.constants.MessageType;
import com.zooms.dean.common.tool.ValidateUtil;
import com.zooms.dean.common.web.Page;
import com.zooms.dean.common.web.Query;
import com.zooms.dean.common.web.View;
import com.zooms.dean.push.domain.Message;
import com.zooms.dean.push.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 消息管理后台接口
 * @author zlj
 * @date 2018年5月9日
 */
@RestController("apiAdminMessage")
@RequestMapping("/{v}/admin/message")
public class MessageController {
    
    @Autowired
    MessageService messageService;
    
    /**
     * 查看消息详情
     * @param request
     * @param response
     * @param id
     * @return
     */
    @ApiVersion(1)
    @RequestMapping("/info/{id}")
    public View<Message> info(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
        View<Message> view = new View<Message>(20000, "获取数据成功");
        
        Message message = null;
        if (id != null) {
            message = messageService.selectMessage(id);
        } else {
            message = new Message();
        }

        view.setData(message);
        return view;
    }
    
    /**
     * 添加消息
     * @param request
     * @param response
     * @param message 消息信息 {@link Message}
     * @return
     */
    @ApiVersion(1)
    @RequestMapping("/add")
    public View<Message> add(HttpServletRequest request, HttpServletResponse response,
            @Validated @RequestBody Message message, BindingResult result) {
        View<Message> view = new View<Message>(20000, "添加成功");

        if (result.hasErrors()) {
            view.setCode(40000);
            view.setMessage(ValidateUtil.first(result.getAllErrors()));
        } else {
            this.messageService.addMessage(message);
        }
        return view;
    }

    /**
     * 更新消息
     * @param request
     * @param response
     * @param message 消息信息 {@link Message}
     * @return
     */
    @ApiVersion(1)
    @RequestMapping("/update")
    public View<Message> update(HttpServletRequest request,
            HttpServletResponse response,
            @Validated @RequestBody Message message, BindingResult result) {
        View<Message> view = new View<Message>(20000, "更新成功");

        if (result.hasErrors()) {
            view.setCode(40000);
            view.setMessage(ValidateUtil.first(result.getAllErrors()));
        } else {
            this.messageService.updateMessage(message);
        }

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
    public View<Page<Message>> list(HttpServletRequest request, HttpServletResponse response, @RequestBody Query<Message> query) {
        View<Page<Message>> view = new View<Page<Message>>(20000, "获取列表成功");

        Message message = query.getData();
        
        if (message == null) {
            message = new Message();
        }
        message.setType(MessageType.SYSTEM);

        Page<Message> page = new Page<Message>(query.getPage(), query.getSize(), messageService.count(message));
        
        List<Message> messageList = messageService.getMessageList(query.getOffset(), query.getLimit(), message);
        
        page.setContent(messageList);
        
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
    @RequestMapping("/delete/{id}")
    public View delete(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
        View view = new View(20000, "删除成功");

        Message message = messageService.selectMessage(id);
        message.setDelFlag(Message.DEL_FLAG_DELETE);
        this.messageService.updateMessage(message);

        return view;
    }
    
}