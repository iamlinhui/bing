package cn.promptness.bing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Lynn
 * @date : 2019-03-30 23:53
 */
@Component
@ConfigurationProperties(prefix = "bing")
public class BingProperties {

    private String imageHost;

    private String imageParam;

    private String bingHost;

    private String bingPath;

    private String bingInfoUrl;


    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public String getBingHost() {
        return bingHost;
    }

    public void setBingHost(String bingHost) {
        this.bingHost = bingHost;
    }

    public String getBingPath() {
        return bingPath;
    }

    public void setBingPath(String bingPath) {
        this.bingPath = bingPath;
    }

    public String getBingInfoUrl() {
        return bingInfoUrl;
    }

    public void setBingInfoUrl(String bingInfoUrl) {
        this.bingInfoUrl = bingInfoUrl;
    }

    public String getImageParam() {
        return imageParam;
    }

    public void setImageParam(String imageParam) {
        this.imageParam = imageParam;
    }
}
