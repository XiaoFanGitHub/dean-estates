package com.zooms.dean.auth.controller.api;

import com.zooms.dean.auth.exceptions.RequestValidateException;
import com.zooms.dean.auth.exceptions.SendLimitException;
import com.zooms.dean.auth.provider.UserManager;
import com.zooms.dean.common.annotation.ApiVersion;
import com.zooms.dean.common.web.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * project：dean-cloud
 *
 * @author linfeng @ nondo
 * @date 2018/5/9
 */
@RestController
@RequestMapping("/{v}/verification_code")
public class VerificationCodeController {

    @Autowired
    private UserManager userManager;

    /**
     * 验证码接口
     *
     * @param params 请求参数
     *               {
     *               username:"",
     *               send_type:"login/register/modify"
     *               }
     * @return 返回验证码发送消息
     * @throws RequestValidateException 请求验证异常
     */
    @ApiVersion(1)
    @RequestMapping(
            value = "/send",
            method = {RequestMethod.POST}
    )
    public View<String> registerVerificationCodeEndpoint(@RequestBody Map<String, String> params) throws RequestValidateException, SendLimitException {

        return userManager.requestVerificationCode(params);
    }

    /**
     * 验证码校验
     *
     * @param params 请求参数
     *               {
     *               "username":"手机号或者邮箱",
     *               "code":"验证码"
     *               }
     * @return 返回验证码发送消息
     * @throws RequestValidateException 请求验证异常
     */
    @ApiVersion(1)
    @RequestMapping(
            value = "/check",
            method = {RequestMethod.POST}
    )
    public View<String> checkVerificationCodeEndpoint(@RequestBody Map<String, String> params) throws RequestValidateException, SendLimitException {

        return userManager.checkVerificationCode(params);
    }

}
