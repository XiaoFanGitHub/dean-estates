package com.zooms.dean.push.controller.api.inner;

import com.zooms.dean.common.annotation.ApiVersion;
import com.zooms.dean.common.tool.ValidateUtil;
import com.zooms.dean.common.web.View;
import com.zooms.dean.push.model.param.MessageParam;
import com.zooms.dean.push.model.result.UserMessageResult;
import com.zooms.dean.push.service.UserMessageService;
import org.apache.commons.lang3.StringUtils;
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
 * 消息管理内部接口
 * @author zlj
 * @date 2018年5月10日
 */
@RestController("apiInnerMessage")
@RequestMapping("/{v}/inner/message")
public class MessageController {
    
    @Autowired
    UserMessageService userMessageService;
    
    /**
     * 添加并推送消息
     * @param request
     * @param response
     * @param message 消息信息 {@link MessageParam}
     * @return
     */
    @ApiVersion(1)
    @RequestMapping("/push")
    @SuppressWarnings("rawtypes")
    public View addAndPush(HttpServletRequest request, HttpServletResponse response,
            @Validated @RequestBody MessageParam message, BindingResult result) {
        View view = new View(20000, "添加成功");

        if (result.hasErrors()) {
            view.setCode(40000);
            view.setMessage(ValidateUtil.first(result.getAllErrors()));
            return view;
        } else {
            if (StringUtils.isEmpty(message.getGoValue())) {
                return View.ofParamsError("必须要求一个value值");
            }
            userMessageService.addAndPush(message);
        }
        return view;
    }
    
    /**
     * 获取经销商首页消息列表
     * @param request
     * @param response
     * @return
     */
    @ApiVersion(1)
    @RequestMapping("/list/{userId}")
    public View<List<UserMessageResult>> list(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer userId) {
        View<List<UserMessageResult>> view = new View<List<UserMessageResult>>(20000, "获取列表成功");

        List<UserMessageResult> messageResultList = userMessageService.getDealerIndexMessageList(userId);
       
        view.setData(messageResultList);
        
        return view;
    }
    
}
