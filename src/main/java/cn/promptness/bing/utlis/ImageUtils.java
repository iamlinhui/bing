package cn.promptness.bing.utlis;

import cn.promptness.bing.config.BingProperties;
import cn.promptness.bing.vo.DataVO;
import cn.promptness.bing.vo.ImageVO;
import cn.promptness.core.HttpClientUtil;
import cn.promptness.core.HttpResult;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 获取必应上的图片
 *
 * @author Lynn
 */
@Component
public class ImageUtils {

    private Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private BingProperties bingProperties;

    /**
     * 得到图片
     */
    public File getFile() throws Exception {

        //找图片资源地址
        HttpResult httpResultHtml = httpClientUtil.doGet(bingProperties.getBingHost());

        if (httpResultHtml.isFailed()) {
            logger.error("请求必应官网失败");
            return null;
        }

        String href = Jsoup.parse(httpResultHtml.getMessage()).getElementById("bgLink").attr("href");

        if (StringUtils.isEmpty(href)) {
            logger.error("请求必应官网后解析连接为空");
            return null;
        }

        File file = new File(bingProperties.getBingPath() + getFileName());

        HttpResult httpResultImage = httpClientUtil.doGet(bingProperties.getBingHost() + href, new FileOutputStream(file));
        if (httpResultImage.isFailed()) {
            logger.error("下载图片失败");
            return null;
        }
        return file;
    }

    /**
     * 得到图片信息
     */
    public ImageVO getFileInfo() throws Exception {

        HttpResult httpResultJson = httpClientUtil.doGet(bingProperties.getBingInfoUrl(), ImmutableMap.of("format", "js", "idx", "0", "n", "1"));

        if (httpResultJson.isFailed()) {
            logger.error("请求获取json信息失败");
            return null;
        }

        String message = httpResultJson.getMessage();
        DataVO dataVO = JSON.parseObject(message, DataVO.class);

        List<ImageVO> images = dataVO.getImages();
        if (images == null || images.isEmpty()) {
            logger.error("获取信息为空");
            return null;
        }
        return images.stream().findFirst().get();
    }


    public String getFileName() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date) + ".jpg";
    }
}