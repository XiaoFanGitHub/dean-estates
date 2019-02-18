package com.zooms.dean.common.thirdpart.ueditor.upload;


import com.zooms.dean.common.thirdpart.ueditor.PathFormat;
import com.zooms.dean.common.thirdpart.ueditor.define.*;
import com.zooms.dean.common.tool.PropertiesTools;
import org.apache.commons.codec.binary.Base64;

import java.util.Map;

import static com.zooms.dean.common.thirdpart.ueditor.define.MIMEType.CONTENT_TYPE;

public final class Base64Uploader {

    public static State save(String content, Map<String, Object> conf) {

        if (content.startsWith("data:image/jpg;base64,")) {
            content = content.substring(22);
        }

        byte[] data = decode(content);

        long maxSize = (Long) conf.get("maxSize");

        if (!validSize(data, maxSize)) {
            return new BaseState(false, AppInfo.MAX_SIZE);
        }

        String suffix = FileType.getSuffix("JPG");

        String savePath = PathFormat.parse((String) conf.get("savePath"),
                (String) conf.get("filename"));

        savePath = savePath + suffix;
        String physicalPath = conf.get("rootPath") + savePath;

        conf.put(CONTENT_TYPE, MIMEType.getMimeType(suffix));
        State storageState = StorageManager.saveBinaryFile(data, physicalPath, conf);

        if (storageState.isSuccess()) {
            storageState.putInfo("url", PropertiesTools.getPropertyValue("aliyun_oss.endpoint") + "/" + PathFormat.format(physicalPath));
            storageState.putInfo("type", suffix);
            storageState.putInfo("original", "");
        }

        return storageState;
    }

    private static byte[] decode(String content) {
        return Base64.decodeBase64(content);
    }

    private static boolean validSize(byte[] data, long length) {
        return data.length <= length;
    }

}