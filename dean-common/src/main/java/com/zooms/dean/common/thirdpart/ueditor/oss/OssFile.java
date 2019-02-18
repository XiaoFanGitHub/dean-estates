package com.zooms.dean.common.thirdpart.ueditor.oss;

import com.aliyun.oss.model.OSSObjectSummary;

import java.io.Serializable;

public class OssFile implements Serializable {

    private static final long serialVersionUID = 7166317177994872529L;

    private String bucketName;
    private String key;
    private long size;

    public OssFile(OSSObjectSummary summary) {
        this.setBucketName(summary.getBucketName());
        this.setKey(summary.getKey());
        this.setSize(summary.getSize());
    }

    public OssFile(String key, long size) {
        this.key = key;
        this.size = size;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
