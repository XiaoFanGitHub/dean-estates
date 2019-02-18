package com.zooms.dean.push.service;

import com.zooms.dean.common.tool.BeanUtils;
import com.zooms.dean.common.web.View;
import com.zooms.dean.push.model.OrderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

/**
 * 订单管理业务类
 * 
 * @author zhaoljin
 * @date 2018年5月12日
 */
@Component
public class OrderService {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 根据订单ID查询订单信息
     * 
     * @param userId 订单ID
     * @return 订单基本信息{@link OrderBean}
     */
    public OrderBean getOrderById(Integer orderId) {
        OrderBean userBean = new OrderBean();
        @SuppressWarnings("unchecked")
        View<LinkedHashMap<String, Object>> result =
            restTemplate.postForEntity("http://dean-order/order/v1/inner/order/info/" + orderId, null, View.class)
                .getBody();
        if (result.getCode() == 20000 && result.getData() != null) {
            BeanUtils.copy(result.getData(), userBean);
        }
        return userBean;
    }

}
