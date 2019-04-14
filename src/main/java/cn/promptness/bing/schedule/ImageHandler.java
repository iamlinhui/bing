package cn.promptness.bing.schedule;

import cn.promptness.bing.pojo.ImageDO;
import cn.promptness.bing.service.ImageService;
import cn.promptness.bing.utlis.ImageUtils;
import cn.promptness.bing.utlis.QiniuUtils;
import cn.promptness.bing.vo.ImageVO;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

/**
 * @author : Lynn
 * @date : 2019-03-30 04:58
 */
@Component
@JobHandler(value = "imageHandler")
public class ImageHandler extends IJobHandler {


    @Autowired
    private ImageUtils imageUtils;
    @Autowired
    private ImageService imageService;
    @Autowired
    private QiniuUtils qiniuUtils;


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
        boolean upload = qiniuUtils.upload(file, fileName);

        if (upload) {
            ImageDO imageDO = new ImageDO();
            imageDO.setName(fileName);
            imageDO.setInfo(fileInfo.getCopyright());
            imageService.saveImage(imageDO);
        }


    }


    @Override
    public ReturnT<String> execute(String param) throws Exception {
        keepImage();
        return SUCCESS;
    }
}
