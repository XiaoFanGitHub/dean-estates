package com.zooms.dean.auth.provider;


import com.zooms.dean.auth.common.bean.AppItem;
import com.zooms.dean.common.web.View;

/**
 * @author slacrey
 * @since 2018/1/18
 */
public interface AppManager {

    /**
     * 注册应用APP
     *
     * @param app 应用
     * @return 注册成功或失败提示消息
     */
    View<String> requestAppRegister(AppItem app);

}
