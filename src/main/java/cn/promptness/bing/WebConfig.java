package cn.promptness.bing;

import cn.promptness.core.HttpResult;
import org.apache.catalina.core.AprLifecycleListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Web Config
 *
 * @author Lynn
 */
@Configuration
@RestControllerAdvice
public class WebConfig extends WebMvcConfigurerAdapter {


    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpResult> error(Throwable e) {
        e.printStackTrace();
        return ResponseEntity.ok(HttpResult.getErrorHttpResult(e.getMessage()));
    }

    @ConditionalOnProperty(name = "apr", havingValue = "true")
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        AprLifecycleListener arpLifecycle = new AprLifecycleListener();
        tomcat.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
        tomcat.addContextLifecycleListeners(arpLifecycle);
        return tomcat;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/");
                container.addErrorPages(error404Page);
            }
        });
    }

}
