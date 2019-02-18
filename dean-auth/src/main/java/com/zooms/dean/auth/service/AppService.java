package com.zooms.dean.auth.service;

import com.zooms.dean.auth.domain.App;

public interface AppService {

    /**
     * 根据应用Key查询出一条应用记录
     *
     * @param id 应用Key
     * @return 应用记录
     */
    App findById(Long id);

    /**
     * 根据应用Key查询出一条应用记录
     *
     * @param appKey 应用Key
     * @return 应用记录
     */
    App findByAppKey(String appKey);

    /**
     * 保存App记录
     *
     * @param record 应用记录
     * @return 是保存还是修改，true为保存，false为修改
     */
    String save(App record);

}
