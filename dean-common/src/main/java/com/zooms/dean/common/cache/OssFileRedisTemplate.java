package com.zooms.dean.common.cache;

import com.zooms.dean.common.thirdpart.ueditor.oss.OssFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OssFileRedisTemplate extends RedisTemplate<String, OssFile> {

    public static final String OSS_FILE_CACHE_KEY = "aliyun_oss_file_cache_key";
    public static final String OSS_FILE_LIST_CACHE_KEY = "OSS_FILE_LIST:";

    @Autowired
    @Override
    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        super.setConnectionFactory(connectionFactory);
    }

    public void addOssFile(String key, OssFile ossFile) {
        this.opsForList().rightPush(key, ossFile);
    }

    public OssFile getOssFile(String key) {
        return this.opsForList().rightPop(key);
    }

    public void delOssFile(String key) {
        this.opsForList().getOperations().delete(key);
    }

    public List<OssFile> listOssFile(String key, long start, long end) {
        return this.opsForList().range(key, start, end);
    }

    public long totalOssFile(String key) {
        return this.opsForList().size(key);
    }

}
