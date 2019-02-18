package com.zooms.dean.auth.provider.impl;


import com.zooms.dean.auth.common.BusinessUtils;
import com.zooms.dean.auth.common.bean.AppItem;
import com.zooms.dean.auth.common.bean.AuthorityItem;
import com.zooms.dean.auth.provider.AppManager;
import com.zooms.dean.auth.service.AppService;
import com.zooms.dean.common.web.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.zooms.dean.auth.common.BusinessUtils.OPERA_REMOVE;

@Service
public class AppManagerImpl implements AppManager {

    @Autowired
    private AppService appService;



    @Override
    public View<String> requestAppRegister(AppItem appItem) {


        String status = appService.save(BusinessUtils.toApp(appItem));
        if ("new".equals(status)) {
            appItem.setNotUpdate(0);
        }

        return View.ofOk("应用注册成功", "0");
    }
}
