package cn.promptness.bing;

import cn.promptness.bing.config.QiniuProperties;
import com.promptness.core.HttpClientUtil;
import com.promptness.core.HttpResult;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SpringBootApplication
@MapperScan(basePackages = {"cn.promptness.bing.dao"})
@RestControllerAdvice
public class BingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BingApplication.class, args);
    }


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

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpResult> error(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.ok(HttpResult.getErrorHttpResult(e.getMessage()));
    }

    @ConditionalOnProperty(name = "apr", havingValue = "true")
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {

        AprLifecycleListener aprLifecycleListener = new AprLifecycleListener();
        aprLifecycleListener.setSSLEngine("off");

        TomcatServletWebServerFactory webServerFactory = new TomcatServletWebServerFactory() {

            @Override
            protected void customizeConnector(Connector connector) {
                connector.setAttribute("address", "127.0.0.1");
                super.customizeConnector(connector);
            }
        };
        webServerFactory.setProtocol("org.apache.coyote.http11.Http11AprProtocol");

        webServerFactory.addContextLifecycleListeners(aprLifecycleListener);


        return webServerFactory;
    }
}
