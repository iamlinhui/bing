package cn.promptness.bing.listener;

import cn.promptness.bing.pojo.ImageDO;
import org.springframework.context.ApplicationEvent;

/**
 * @author : Lynn
 * @date : 2019-04-14 14:55
 */
public class BingEvent extends ApplicationEvent {

    private ImageDO imageDO;

    public BingEvent(Object source) {
        super(source);
        imageDO = (ImageDO) source;
    }

    public ImageDO getImageDO() {
        return imageDO;
    }

    public static BingEvent getInstance() {
        ImageDO imageDO = new ImageDO();
        return new BingEvent(imageDO);
    }


}
