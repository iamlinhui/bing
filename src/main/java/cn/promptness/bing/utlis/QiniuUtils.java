package cn.promptness.bing.utlis;

import cn.promptness.bing.config.QiniuProperties;
import cn.promptness.bing.schedule.ImageSchedule;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Lynn
 * @date : 2019-03-31 19:55
 */
@Component
public class QiniuUtils {

    private Logger logger = LoggerFactory.getLogger(ImageSchedule.class);

    private static final Map<String, Object> CACHE_MAP = new HashMap<>();

    @Autowired
    private QiniuProperties qiniuProperties;
    @Autowired
    private Auth auth;
    @Autowired
    private UploadManager uploadManager;

    public boolean upload(File path, String filename) {
        try {
            // 调用put方法上传
            Response res = uploadManager.put(path, filename, getUpToken());
            logger.info(res.bodyString());
            return true;
        } catch (QiniuException e) {
            Response r = e.response;
            logger.error(r.toString());
        }
        return false;
    }

    /**
     * 简单上传，使用默认策略，只需要设置上传的空间名就可以了
     */
    private String getUpToken() {

        Instant expiresDate = (Instant) CACHE_MAP.get("EXPIRES_DATE");
        Instant currentDate = Instant.now();
        if (expiresDate == null || currentDate.isAfter(expiresDate)) {
            String uploadToken = auth.uploadToken(qiniuProperties.getBucketName());
            CACHE_MAP.put("UPLOAD_TOKEN", uploadToken);
            CACHE_MAP.put("EXPIRES_DATE", currentDate.plusSeconds(3600));
            return uploadToken;
        }
        return (String) CACHE_MAP.get("UPLOAD_TOKEN");
    }
}
