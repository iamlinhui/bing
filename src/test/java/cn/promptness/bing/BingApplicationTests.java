package cn.promptness.bing;

import cn.promptness.bing.config.BingProperties;
import cn.promptness.bing.pojo.ImageDO;
import cn.promptness.bing.schedule.ImageSchedule;
import cn.promptness.bing.service.ImageService;
import cn.promptness.bing.vo.DataVO;
import cn.promptness.bing.vo.ImageVO;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.promptness.core.HttpClientUtil;
import com.promptness.core.HttpResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BingApplicationTests {
    private Logger logger = LoggerFactory.getLogger(BingApplicationTests.class);


    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageSchedule imageSchedule;

    @Autowired
    private BingProperties bingProperties;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Test
    public void contextLoads() throws Exception {


        for (int i = 0; i < 20; i++) {

            HttpResult httpResultJson = httpClientUtil.doGet(bingProperties.getBingInfoUrl(), ImmutableMap.of("format", "js", "idx", String.valueOf(i), "n", "7"));

            if (httpResultJson.isFailed()) {
                logger.error("请求获取json信息失败");
                continue;
            }

            String message = httpResultJson.getMessage();
            DataVO dataVO = JSON.parseObject(message, DataVO.class);

            List<ImageVO> images = dataVO.getImages();
            if (images == null || images.isEmpty()) {
                logger.error("获取信息为空");
                continue;
            }

            for (ImageVO imageVO : images) {

                String enddate = imageVO.getEnddate();
                Date endDate = new SimpleDateFormat("yyyyMMdd").parse(enddate);

                boolean exist = imageService.isExist(endDate);
                if (exist) {
                    logger.info(enddate + "已经存在");
                    continue;
                }

                ImageDO imageDO = new ImageDO();

                imageDO.setInfo(imageVO.getCopyright());
                imageDO.setName(new SimpleDateFormat("yyyy-MM-dd").format(endDate) + ".jpg");
                imageService.saveImage(imageDO);


            }
        }


    }

    @Test
    public void contextLoadsToady() throws Exception {

        imageSchedule.keepImage();

    }

}
