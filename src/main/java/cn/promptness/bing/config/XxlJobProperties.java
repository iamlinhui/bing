package cn.promptness.bing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Lynn
 * @date : 2019-04-14 18:24
 */
@Component
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    private String adminAddresses;

    private String executorAppName;

    private String executorIp;

    private int executorPort;

    private String accessToken;

    private String executorLogPath;

    private int executorLogRetentionDays;

    public String getAdminAddresses() {
        return adminAddresses;
    }

    public void setAdminAddresses(String adminAddresses) {
        this.adminAddresses = adminAddresses;
    }

    public String getExecutorAppName() {
        return executorAppName;
    }

    public void setExecutorAppName(String executorAppName) {
        this.executorAppName = executorAppName;
    }

    public String getExecutorIp() {
        return executorIp;
    }

    public void setExecutorIp(String executorIp) {
        this.executorIp = executorIp;
    }

    public int getExecutorPort() {
        return executorPort;
    }

    public void setExecutorPort(int executorPort) {
        this.executorPort = executorPort;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExecutorLogPath() {
        return executorLogPath;
    }

    public void setExecutorLogPath(String executorLogPath) {
        this.executorLogPath = executorLogPath;
    }

    public int getExecutorLogRetentionDays() {
        return executorLogRetentionDays;
    }

    public void setExecutorLogRetentionDays(int executorLogRetentionDays) {
        this.executorLogRetentionDays = executorLogRetentionDays;
    }
}
