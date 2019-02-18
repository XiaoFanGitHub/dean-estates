package com.zooms.dean.expenses;




import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * 中央气象台的天气预报API 
 * */
public class Test {
    public static void main(String[] args) {
        String host = "https://carwz.shumaidata.com";
        String path = "/post/carwz";
        String method = "POST";
        String appcode = "你自己的AppCode";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("car_no", "粤AJ44E6");
        bodys.put("car_type", "02");
        bodys.put("engine_number", "AD50EG081600062");
        bodys.put("frame_number", "LNBSCC3H8GF308384");



    }


}