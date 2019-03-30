package cn.promptness.bing.schedule;

import cn.promptness.bing.config.QiniuProperties;
import cn.promptness.bing.pojo.ImageDO;
import cn.promptness.bing.service.ImageService;
import cn.promptness.bing.utlis.ImageUtils;
import cn.promptness.bing.vo.ImageVO;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

/**
 * @author : Lynn
 * @date : 2019-03-30 04:58
 */
@Component
public class ImageSchedule {

    private Logger logger = LoggerFactory.getLogger(ImageSchedule.class);

    @Autowired
    private QiniuProperties qiniuProperties;
    @Autowired
    private Auth auth;
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private ImageUtils imageUtils;
    @Autowired
    private ImageService imageService;


    @Scheduled(cron = "59 0 0 * * ?")
    public void keepImage() throws Exception {


        boolean exist = imageService.isExist(new Date());
        if (exist) {
            return;
        }

        ImageVO fileInfo = imageUtils.getFileInfo();

        if (fileInfo == null) {
            return;
        }

        File file = imageUtils.getFile();

        if (file == null) {
            return;
        }

        String fileName = imageUtils.getFileName();
        boolean upload = upload(file, fileName);

        if (upload) {
            ImageDO imageDO = new ImageDO();
            imageDO.setName(fileName);
            imageDO.setInfo(fileInfo.getCopyright());
            imageService.saveImage(imageDO);
        }


    }

    /**
     * 简单上传，使用默认策略，只需要设置上传的空间名就可以了
     */
    private String getUpToken() {
        return auth.uploadToken(qiniuProperties.getBucketName());
    }

    private boolean upload(File path, String filename) {
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
}
