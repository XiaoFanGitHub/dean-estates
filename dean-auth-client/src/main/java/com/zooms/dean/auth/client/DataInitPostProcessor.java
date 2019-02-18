package com.zooms.dean.auth.client;

import com.zooms.dean.auth.common.bean.AppItem;
import com.zooms.dean.common.tool.StringUtils;
import com.zooms.dean.common.web.View;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * springboot启动时执行任务CommandLineRunner
 */
@Component
@Order(value = 100)
public class DataInitPostProcessor implements CommandLineRunner {

    private static Logger logger = Logger.getLogger(DataInitPostProcessor.class);

    private JsonConfig jsonDataInit;
    private DeanAuthClient deanAuthClient;

    public DataInitPostProcessor(JsonConfig jsonDataInit, DeanAuthClient deanAuthClient) {
        this.jsonDataInit = jsonDataInit;
        this.deanAuthClient = deanAuthClient;
    }

    @Override
    public void run(String... strings) throws Exception {
        AppItem jsonData = jsonDataInit.getData();
        if (jsonData != null && StringUtils.isNotBlank(jsonData.getAppKey())) {
            View<String> view = deanAuthClient.requestAppInit(jsonData);
            logger.info(view.getMessage());
        }
    }
}