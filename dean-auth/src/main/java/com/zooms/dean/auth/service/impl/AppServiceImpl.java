package com.zooms.dean.auth.service.impl;

import com.zooms.dean.auth.domain.App;
import com.zooms.dean.auth.domain.AppExample;
import com.zooms.dean.auth.mapper.AppMapper;
import com.zooms.dean.auth.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AppServiceImpl implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Override
    public App findById(Long id) {
        return appMapper.selectByPrimaryKey(id);
    }

    @Override
    public App findByAppKey(String appKey) {
        AppExample example = new AppExample();
        List<App> appList = appMapper.selectByExample(example);
        return appList.isEmpty() ? null : appList.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(App record) {

        App oldRecord = appMapper.selectByPrimaryKey(record.getId());
        if (oldRecord == null) {
            appMapper.insertSelective(record);
            return "new";
        } else {
            appMapper.updateByPrimaryKeySelective(record);
            return "update";
        }

    }

}
