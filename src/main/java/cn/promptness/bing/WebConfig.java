package cn.promptness.bing;

import cn.promptness.core.HttpResult;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Web Config
 * 编写一个配置类（@Configuration），是WebMvcConfigurationSupport类型；不能标注@EnableWebMvc
 *
 * @author Lynn
 */
@Configuration
@EnableWebMvc
@RestControllerAdvice
public class WebConfig implements WebMvcConfigurer, ErrorPageRegistrar {


    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpResult> error(Throwable e) {
        e.printStackTrace();
        return ResponseEntity.ok(HttpResult.getErrorHttpResult(e.getMessage()));
    }

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/");
        registry.addErrorPages(error404Page);
    }


}
