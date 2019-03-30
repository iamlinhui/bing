package cn.promptness.bing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Lynn
 * @date : 2019-03-30 04:55
 */
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuProperties {

    /**
     * 设置好账号的ACCESS_KEY和SECRET_KEY
     */
    private String accessKey = "***************";

    private String secretKey = "***********";
    /**
     * 要上传的空间
     */
    private String bucketName = "***********";

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
