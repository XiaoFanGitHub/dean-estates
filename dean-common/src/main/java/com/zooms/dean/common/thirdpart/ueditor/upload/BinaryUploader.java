package com.zooms.dean.common.thirdpart.ueditor.upload;


import com.zooms.dean.common.thirdpart.ueditor.PathFormat;
import com.zooms.dean.common.thirdpart.ueditor.define.AppInfo;
import com.zooms.dean.common.thirdpart.ueditor.define.BaseState;
import com.zooms.dean.common.thirdpart.ueditor.define.FileType;
import com.zooms.dean.common.thirdpart.ueditor.define.State;
import com.zooms.dean.common.tool.PropertiesTools;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.zooms.dean.common.thirdpart.ueditor.define.MIMEType.CONTENT_TYPE;


public class BinaryUploader {

    public static State save(HttpServletRequest request,
                             Map<String, Object> conf) {

        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }

        try {

            String filedName = (String) conf.get("fieldName");
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileList = multipartRequest.getFiles(filedName);
            MultipartFile fileItem = null;
            for (MultipartFile fil : fileList) {
                fileItem = fil;
                if (fileItem != null && !fileItem.isEmpty()) {
                    break;
                }
            }

            if (fileItem == null) {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }

            String savePath = (String) conf.get("savePath");
            String originFileName = fileItem.getOriginalFilename();
            String suffix = FileType.getSuffixByFilename(originFileName);

            originFileName = originFileName.substring(0,
                    originFileName.length() - suffix.length());
            savePath = savePath + suffix;

            long maxSize = (Long) conf.get("maxSize");
            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
            }

            savePath = PathFormat.parse(savePath, originFileName);

            String physicalPath = conf.get("rootPath") + savePath;

            String contentType = fileItem.getContentType();
            conf.put(CONTENT_TYPE, contentType);

            InputStream is = fileItem.getInputStream();
            State storageState = StorageManager.saveFileByInputStream(is,
                    physicalPath, maxSize, conf);
            is.close();

            if (storageState.isSuccess()) {
                storageState.putInfo("url", PropertiesTools.getPropertyValue("aliyun_oss.endpoint") + "/" + PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
            }

            return storageState;

        } catch (IOException ignored) {
        }

        return new BaseState(false, AppInfo.IO_ERROR);

    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}
