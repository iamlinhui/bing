package cn.promptness.bing.listener;

import cn.promptness.bing.dao.ImageDao;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : Lynn
 * @date : 2019-04-14 14:54
 */
@Component
public class BingListener {

    @Resource
    private ImageDao imageDao;

    @Async
    @EventListener
    public void method(BingEvent bingEvent) {

        bingEvent.getImageDO();

        //TODO


    }

}
