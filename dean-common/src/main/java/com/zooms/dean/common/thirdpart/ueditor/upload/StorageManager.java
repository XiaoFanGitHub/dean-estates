package com.zooms.dean.common.thirdpart.ueditor.upload;


import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.zooms.dean.common.cache.OssFileRedisTemplate;
import com.zooms.dean.common.thirdpart.ueditor.PathFormat;
import com.zooms.dean.common.thirdpart.ueditor.define.AppInfo;
import com.zooms.dean.common.thirdpart.ueditor.define.BaseState;
import com.zooms.dean.common.thirdpart.ueditor.define.State;
import com.zooms.dean.common.thirdpart.ueditor.oss.OssFile;
import com.zooms.dean.common.tool.AliyunOssTool;
import com.zooms.dean.common.tool.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Map;

import static com.zooms.dean.common.cache.OssFileRedisTemplate.OSS_FILE_CACHE_KEY;
import static com.zooms.dean.common.cache.OssFileRedisTemplate.OSS_FILE_LIST_CACHE_KEY;
import static com.zooms.dean.common.thirdpart.ueditor.define.MIMEType.CONTENT_TYPE;
import static com.zooms.dean.common.tool.AliyunOssTool.OSS_TOOL_KEY;

public class StorageManager {
    public static final int BUFFER_SIZE = 8192;

    public StorageManager() {
    }

    private static String getNameOfPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static State saveBinaryFile(byte[] data, String path, Map<String, Object> conf) {

        String savePath = PathFormat.format(path);
        String title = getNameOfPath(savePath);
        String contentType = (String) conf.get(CONTENT_TYPE);
        AliyunOssTool ossTool = (AliyunOssTool) conf.get(OSS_TOOL_KEY);
        ObjectMetadata meta = initObjectMetadata(data, title, contentType);

        State state;
        try {
            if (savePath.startsWith("/")) {
                savePath = savePath.substring(1);
            }
            state = new BaseState(ossTool.uploadFileByByte(data, savePath, meta), savePath);
        } catch (Exception e) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

        state.putInfo("size", data.length);
        state.putInfo("title", title);
        return state;
    }

    /**
     * 初始化
     *
     * @param len
     * @param title
     * @param contentType
     * @return
     */
    private static ObjectMetadata initObjectMetadata(byte[] data, String title, String contentType) {
        ObjectMetadata meta = new ObjectMetadata();
        // 设置上传文件长度
        meta.setContentLength(data.length);
        if (StringUtils.isNoneEmpty(contentType)) {
            meta.setContentType(contentType);
        }
        meta.addUserMetadata("size", data.length + "");
        if (StringUtils.isNoneEmpty(title)) {
            meta.addUserMetadata("title", title);
        }

        String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(data));
        meta.setContentMD5(md5);
        meta.setContentType(contentType);
        meta.setContentEncoding("utf-8");

        return meta;
    }

    public static State saveFileByInputStream(InputStream is, String path,
                                              long maxSize, Map<String, Object> conf) {
        State state = null;

        File tmpFile = getTmpFile();

        byte[] dataBuf = new byte[2048];
        BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

            int count = 0;
            while ((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            bos.close();

            if (tmpFile.length() > maxSize) {
                tmpFile.delete();
                return new BaseState(false, AppInfo.MAX_SIZE);
            }

            state = saveTmpFile(tmpFile, path, conf);

            if (!state.isSuccess()) {
                tmpFile.delete();
            }

            return state;

        } catch (IOException ignored) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    public static State saveFileByInputStream(InputStream is, String path, Map<String, Object> conf) {
        State state = null;

        File tmpFile = getTmpFile();

        byte[] dataBuf = new byte[2048];
        BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

            int count = 0;
            while ((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            bos.close();

            state = saveTmpFile(tmpFile, path, conf);

            if (!state.isSuccess()) {
                tmpFile.delete();
            }

            return state;
        } catch (IOException e) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    private static File getTmpFile() {
        File tmpDir = FileUtils.getTempDirectory();
        String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
        return new File(tmpDir, tmpFileName);
    }

    private static State saveTmpFile(File tmpFile, String path, Map<String, Object> conf) {

        String savePath = PathFormat.format(path);
        if (savePath.startsWith("/")) {
            savePath = savePath.substring(1);
        }
        String title = getNameOfPath(savePath);
        AliyunOssTool ossTool = (AliyunOssTool) conf.get(OSS_TOOL_KEY);

        State state = null;
        try {
            ossTool.uploadFile(tmpFile.getAbsolutePath(), savePath);

            OssFileRedisTemplate ossFileRedisTemplate = (OssFileRedisTemplate) conf.get(OSS_FILE_CACHE_KEY);
            if (ossFileRedisTemplate != null) {
                ossFileRedisTemplate.addOssFile(OSS_FILE_LIST_CACHE_KEY, new OssFile(savePath, tmpFile.length()));
            }

        } catch (Exception e) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

        state = new BaseState(true, savePath);
        state.putInfo("size", tmpFile.length());
        state.putInfo("title", title);

        return state;
    }

}
