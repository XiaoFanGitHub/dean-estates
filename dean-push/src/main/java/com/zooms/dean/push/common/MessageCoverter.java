package com.zooms.dean.push.common;

import com.alibaba.fastjson.JSONObject;
import com.zooms.dean.push.model.Notification;
import com.zooms.dean.push.model.param.MessageParam;

public class MessageCoverter {
    private static final int LENGHT = 120;

    public static Notification toNotification(MessageParam message) {
        Notification notification = new Notification();
        notification.setTitle(message.getTitle());
        String content = message.getBody();
        String body = content.replaceAll("<.*?>", "");
        if (body.length() > LENGHT) {
            notification.setBody(body.substring(0, LENGHT));
        } else {
            notification.setBody(body);
        }
        JSONObject extraParamsJson = new JSONObject();
        extraParamsJson.put("type", message.getType());
        extraParamsJson.put("goType", message.getGoType());
        extraParamsJson.put("goValue", message.getGoValue());
        notification.setExtParameters(extraParamsJson.toJSONString());
        System.out.println(JSONObject.toJSON(notification).toString());
        return notification;
    }

}
