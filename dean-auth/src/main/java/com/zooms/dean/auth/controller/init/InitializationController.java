package com.zooms.dean.auth.controller.init;

import com.zooms.dean.auth.common.bean.AppItem;
import com.zooms.dean.auth.provider.AppManager;
import com.zooms.dean.common.annotation.ApiVersion;
import com.zooms.dean.common.web.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * APP初始化
 *
 * @author linfeng
 */
@RestController
@RequestMapping("/{v}/auth")
public class InitializationController {


    @Autowired
    private AppManager appManager;

    @ApiVersion(1)
    @RequestMapping(
            value = "/app/init",
            method = RequestMethod.POST
    )
    public View<String> requestAppInit(@Validated @RequestBody AppItem appItem) {

        return appManager.requestAppRegister(appItem);
    }


}
