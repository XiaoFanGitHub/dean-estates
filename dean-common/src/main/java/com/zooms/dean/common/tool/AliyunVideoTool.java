package com.zooms.dean.common.tool;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import com.zooms.dean.common.config.AliyunSettings;
import com.zooms.dean.common.domain.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AliyunVideoTool {

    private final static String regionId = "cn-shanghai";
    private String accessKeyId;
    private String accessSecret;
    private static DefaultAcsClient aliyunClient;

    @Autowired
    public AliyunVideoTool(AliyunSettings aliyunOssConfig) {
        accessKeyId = aliyunOssConfig.getAccessKeyId();
        accessSecret = aliyunOssConfig.getAccessKeySecret();
    }

    private DefaultAcsClient getDefaultAcsClient() {
        if (aliyunClient == null) {
            aliyunClient = new DefaultAcsClient(DefaultProfile.getProfile(regionId,
                    accessKeyId, accessSecret));
        }
        return aliyunClient;
    }

    public CreateUploadVideoResponse createUploadVideo(Video video)
            throws ClientException {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setFileName(video.getFileName());
        // 必选，视频标题
        request.setTitle(video.getTitle());
        // 可选，视频源文件字节数
        request.setFileSize(1024L);
        return getDefaultAcsClient().getAcsResponse(request);
    }

    /**
     * 当网络异常导致文件上传失败时,可刷新上传凭证后再次执行上传操作
     *
     * @param videoId
     * @throws ClientException
     * @throws ServerException
     */
    public RefreshUploadVideoResponse refreshUploadVideo(String videoId)
            throws ClientException {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId(videoId);
        return getDefaultAcsClient().getAcsResponse(request);
    }

    public GetVideoPlayAuthResponse getVideoPlayAuth(String videoId)
            throws ClientException {

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        return getDefaultAcsClient().getAcsResponse(request);
    }
}
