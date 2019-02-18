package com.zooms.dean.common.tool;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;

import com.zooms.dean.common.config.AliyunSettings;
import com.zooms.dean.common.exceptions.PropertiesConfigException;
import com.zooms.dean.common.thirdpart.ueditor.oss.OssFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nondo on 2017/8/2.
 */
@Component
public final class AliyunOssTool {

    public static final String OSS_TOOL_KEY = "aliyun_oss_tool";
    
    public static String ossAttachServerDomain = PropertiesTools.getPropertyValue("aliyun_oss.endpoint");

    private Logger log = LoggerFactory.getLogger(AliyunOssTool.class);

    private String endpoint;
    private String accessKeyId;
    private String accessSecret;
    private String bucketName;
    private String bucketNameViedeo;
    private boolean enable;

    @Autowired
    public AliyunOssTool(AliyunSettings aliyunOssConfig) {
        if (aliyunOssConfig.getOss() == null) {
            throw new PropertiesConfigException("dean.aliyun.oss 沒有配置");
        }
        this.endpoint = aliyunOssConfig.getOss().getEndpoint();
        if (StringUtils.isBlank(this.endpoint)) {
            throw new PropertiesConfigException("dean.aliyun.oss.endpoint 沒有配置");
        }
        this.accessKeyId = aliyunOssConfig.getAccessKeyId();
        if (StringUtils.isBlank(this.accessKeyId)) {
            throw new PropertiesConfigException("dean.aliyun.oss.access_key_id 沒有配置");
        }
        this.accessSecret = aliyunOssConfig.getAccessKeySecret();
        if (StringUtils.isBlank(this.accessKeyId)) {
            throw new PropertiesConfigException("dean.aliyun.oss.access_secret 沒有配置");
        }
        this.bucketName = aliyunOssConfig.getOss().getBucketName();
        this.bucketNameViedeo = aliyunOssConfig.getOss().getBucketNameVideo();
        this.enable = aliyunOssConfig.getOss().getEnable();
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean uploadFile(String sourceFile, String storeFileName) {
        return uploadFile(sourceFile, storeFileName, null);
    }

    public boolean uploadFile(String sourceFile, String storeFileName, ObjectMetadata metadata) {

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);

        try {
            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, storeFileName);
            // 待上传的本地文件
            uploadFileRequest.setUploadFile(sourceFile);
            // 设置并发下载数，默认1
            uploadFileRequest.setTaskNum(5);
            // 设置分片大小，默认100KB
            uploadFileRequest.setPartSize(1024 * 1024);
            // 开启断点续传，默认关闭
            uploadFileRequest.setEnableCheckpoint(true);
            if (metadata != null) {
                uploadFileRequest.setObjectMetadata(metadata);
            }

            ossClient.uploadFile(uploadFileRequest);

        } catch (OSSException oe) {

            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message: {}, Error Code: {}, Request ID: {}, Host ID: {}", oe.getMessage(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
            return false;
        } catch (ClientException ce) {

            log.error("Caught an ClientException, which means the com.zooms.dean.auth.client.client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.", ce);
            return false;
        } catch (Throwable e) {

            log.error(e.getMessage(), e);
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;
    }

    /**
     * 根据输入流上传文件
     *
     * @param inputStream 输入流
     * @param key         文件名
     * @return 是否上传成功
     */
    public boolean uploadFileByInputStream(InputStream inputStream, String key) {

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);
        try {
            ossClient.putObject(bucketName, key, inputStream);
        } catch (Exception e) {
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;

    }

    /**
     * 根据输入流上传文件
     *
     * @param inputStream 输入流
     * @param key         文件名
     * @param metadata    对象元信息
     * @return 是否上传成功
     */
    public boolean uploadFileByInputStream(InputStream inputStream, String key, ObjectMetadata metadata) {

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);
        try {
            ossClient.putObject(bucketName, key, inputStream, metadata);
        } catch (Exception e) {
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;

    }

    /**
     * 根据输入流上传文件
     *
     * @param bytes    字节数组
     * @param key      文件名
     * @param metadata 对象元信息
     * @return 是否上传成功
     */
    public boolean uploadFileByByte(byte[] bytes, String key, ObjectMetadata metadata) {

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);
        try {
            ossClient.putObject(bucketName, key, new ByteArrayInputStream(bytes), metadata);
        } catch (Exception e) {
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;

    }

    /**
     * 获取文件动态地址
     *
     * @param pathname
     * @return
     */
    public String getDynamicFileUrl(String pathname) {
        if (StringUtils.isEmpty(pathname)) {
            return null;
        }
        int isFull = pathname.indexOf("http");
        if (isFull >= 0) {
            return pathname;
        }
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);
        try {
            // 设置URL过期时间为10年  3600l* 1000*24*365*10
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
            URL url = ossClient.generatePresignedUrl(bucketName, pathname, expiration);
            if (url != null) {
                return url.toString();
            }
        } finally {
            ossClient.shutdown();
        }

        return null;
    }
    
    /**
     * 获取文件url地址
     *
     * @param path
     * @return
     */
    public String getFileUrl(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        int isFull = path.indexOf("http");
        if (isFull >= 0) {
            return path;
        }
        return ossAttachServerDomain + "/" + path;
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            String path = "app/" + split[split.length - 1];
            String fullPath = getFileUrl(path);
            return fullPath;
        }
        return null;
    }

    /**
     * 判断文件是否存在
     *
     * @param fileUrl 相对于阿里云OSS的相对路径
     * @return 是否存在
     */
    public Boolean doesObjectExist(String fileUrl) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);
        try {
            return ossClient.doesObjectExist(bucketName, fileUrl);
        } finally {
            ossClient.shutdown();
        }
    }

    public List<OssFile> listObjects(final String keyPrefix, final Boolean recursive) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);

        List<OssFile> result = new ArrayList<>();
        ObjectListing objectListing;
        String nextMarker = keyPrefix;
        try {
            do {
                ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
                listObjectsRequest.withMarker(nextMarker);
                if (recursive) {
                    if (keyPrefix.endsWith("/")) {
                        listObjectsRequest.setPrefix(keyPrefix);
                    } else {
                        listObjectsRequest.setPrefix(keyPrefix + "/");
                    }
                } else {
                    if (keyPrefix.endsWith("/")) {
                        String prefix = keyPrefix.substring(0, keyPrefix.length() - 1);
                        listObjectsRequest.setPrefix(prefix);
                    } else {
                        listObjectsRequest.setPrefix(keyPrefix);
                    }
                }
                objectListing = ossClient.listObjects(listObjectsRequest);

                List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
                for (OSSObjectSummary summary : sums) {
                    result.add(new OssFile(summary));
                }

                nextMarker = objectListing.getNextMarker();
            } while (objectListing.isTruncated());

        } finally {
            ossClient.shutdown();
        }

        return result;

    }

    public boolean downloadFile(String sourceFile, String storeFileName) {

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);

        try {
            // 下载请求，10个任务并发下载，启动断点续传
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, storeFileName);
            downloadFileRequest.setDownloadFile(sourceFile);
            downloadFileRequest.setTaskNum(10);
            downloadFileRequest.setEnableCheckpoint(true);

            // 下载文件
            DownloadFileResult downloadRes = ossClient.downloadFile(downloadFileRequest);
            // 下载成功时，会返回文件的元信息
            downloadRes.getObjectMetadata();

        } catch (OSSException oe) {

            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message: {}, Error Code: {}, Request ID: {}, Host ID: {}", oe.getMessage(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
            return false;
        } catch (ClientException ce) {

            log.error("Caught an ClientException, which means the com.zooms.dean.auth.client.client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.", ce);
            return false;
        } catch (Throwable e) {

            log.error(e.getMessage(), e);
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;
    }

    public void downloadAttach(HttpServletResponse response, String storeFileName) {

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);

        try {
            OSSObject ossObject = ossClient.getObject(bucketName, storeFileName);

            BufferedInputStream bis = new BufferedInputStream(ossObject.getObjectContent());
            byte[] buffBytes = new byte[1024];

            OutputStream outputStream = response.getOutputStream();

            int read = 0;
            while ((read = bis.read(buffBytes)) != -1) {
                outputStream.write(buffBytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            bis.close();
        } catch (OSSException oe) {

            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message: {}, Error Code: {}, Request ID: {}, Host ID: {}", oe.getMessage(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
        } catch (ClientException ce) {

            log.error("Caught an ClientException, which means the com.zooms.dean.auth.client.client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.", ce);
        } catch (Throwable e) {

            log.error(e.getMessage(), e);
        } finally {
            // 关闭client
            ossClient.shutdown();
        }
    }

    /**
     * 获取视频地址
     *
     * @param pathname
     * @return
     */
    public String getVideoUrl(String pathname) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);
        try {
            // 设置URL过期时间为5分钟
            Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 5);
            URL url = ossClient.generatePresignedUrl(bucketNameViedeo, pathname, expiration);
            if (url != null) {
                return url.toString();
            }
        } finally {
            ossClient.shutdown();
        }

        return null;
    }
}
