package com.zooms.dean.common.thirdpart.ueditor.hunter;

import com.aliyun.oss.model.OSSObjectSummary;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.zooms.dean.common.cache.OssFileRedisTemplate;
import com.zooms.dean.common.thirdpart.ueditor.define.BaseState;
import com.zooms.dean.common.thirdpart.ueditor.define.MultiState;
import com.zooms.dean.common.thirdpart.ueditor.define.State;
import com.zooms.dean.common.thirdpart.ueditor.oss.OssFile;
import com.zooms.dean.common.tool.AliyunOssTool;
import com.zooms.dean.common.tool.PropertiesTools;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.zooms.dean.common.cache.OssFileRedisTemplate.OSS_FILE_CACHE_KEY;
import static com.zooms.dean.common.cache.OssFileRedisTemplate.OSS_FILE_LIST_CACHE_KEY;
import static com.zooms.dean.common.tool.AliyunOssTool.OSS_TOOL_KEY;

public class FileManager {

    private String dir = null;
    private String rootPath = null;
    private String[] allowFiles = null;
    private int count = 0;
    private AliyunOssTool ossTool = null;
    private OssFileRedisTemplate ossFileRedisTemplate;

    public FileManager(Map<String, Object> conf) {

        this.rootPath = (String) conf.get("rootPath");
        this.dir = this.rootPath + (String) conf.get("dir");
        this.allowFiles = this.getAllowFiles(conf.get("allowFiles"));
        this.count = (Integer) conf.get("count");
        this.ossTool = (AliyunOssTool) conf.get(OSS_TOOL_KEY);
        this.ossFileRedisTemplate = (OssFileRedisTemplate) conf.get(OSS_FILE_CACHE_KEY);
    }

    public State listFile(int index) {

        if (dir.startsWith("/")) {
            dir = dir.substring(1);
        }

        long total = ossFileRedisTemplate.totalOssFile(OSS_FILE_LIST_CACHE_KEY);
        State state = null;
        if (index < 0 || index > total) {
            state = new MultiState(true);
        } else {

            List<OssFile> objectSummaries = ossFileRedisTemplate.listOssFile(OSS_FILE_LIST_CACHE_KEY, index, index + this.count);
            if (objectSummaries.isEmpty()) {
                objectSummaries = ossTool.listObjects(this.dir, true);
                Collection<OssFile> list = Collections2.filter(objectSummaries, new Predicate<OssFile>() {
                    @Override
                    public boolean apply(OssFile ossObjectSummary) {
                        boolean allow = false;
                        for (String allowFile : allowFiles) {
                            allow = ossObjectSummary.getKey().endsWith(allowFile);
                            if (allow) {
                                break;
                            }
                        }
                        return allow;
                    }
                });
                for (OssFile ossFile : list) {
                    ossFileRedisTemplate.addOssFile(OSS_FILE_LIST_CACHE_KEY, ossFile);
                }
                objectSummaries = ossFileRedisTemplate.listOssFile(OSS_FILE_LIST_CACHE_KEY, index, index + this.count);
            }

            state = this.getState(objectSummaries);
        }
        state.putInfo("start", index);
        state.putInfo("total", total);

        return state;

    }

    private State getState(List<OssFile> files) {

        MultiState state = new MultiState(true);
        BaseState fileState;
        for (OssFile ossFile : files) {
            if (ossFile == null) {
                break;
            }
            fileState = new BaseState(true);
            fileState.putInfo("url", PropertiesTools.getPropertyValue("aliyun_oss.endpoint") + "/" + ossFile.getKey());
            state.addState(fileState);
        }

        return state;

    }

    private String getPath(OSSObjectSummary file) {

        String path = file.getKey();

        return path.replace(this.rootPath, "/");

    }

    private String[] getAllowFiles(Object fileExt) {

        String[] exts = null;
        String ext = null;

        if (fileExt == null) {
            return new String[0];
        }

        exts = (String[]) fileExt;

        for (int i = 0, len = exts.length; i < len; i++) {

            ext = exts[i];
            exts[i] = ext.replace(".", "");

        }

        return exts;

    }

}
