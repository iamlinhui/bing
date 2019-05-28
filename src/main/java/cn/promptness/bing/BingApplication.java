package cn.promptness.bing;

import cn.promptness.bing.config.QiniuProperties;
import cn.promptness.bing.config.XxlJobProperties;
import cn.promptness.core.HttpClientUtil;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Lynn
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.promptness.bing.dao"})
@EnableApolloConfig
@EnableAsync
public class BingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BingApplication.class, args);
    }

    @Autowired
    private XxlJobProperties xxlJobProperties;

    /**
     * 密钥配置
     */
    @Bean
    public Auth auth(QiniuProperties qiniuProperties) {
        return Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
    }

    /**
     * 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。 创建上传对象
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(new Configuration(Zone.autoZone()));
    }

    @Bean
    public HttpClientUtil httpClientUtil() {
        return new HttpClientUtil();
    }

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAppName(xxlJobProperties.getExecutorAppName());
        xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutorIp());
        xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutorPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutorLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutorLogRetentionDays());

        return xxlJobSpringExecutor;
    }
}
