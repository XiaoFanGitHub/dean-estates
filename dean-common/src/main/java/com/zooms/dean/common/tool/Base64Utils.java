package com.zooms.dean.common.tool;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author ken.
 * @version 1.0
 * @email 695093513@qq.com
 * @date 2018/3/27
 */
public class Base64Utils {
    /**
     * base64编码
     *
     * @param string
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode(String string) {
        Base64.Encoder encoder = Base64.getEncoder();

        byte[] textByte = new byte[0];
        try {
            textByte = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encoder.encodeToString(textByte);
    }
}
