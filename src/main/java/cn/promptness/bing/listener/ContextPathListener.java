package cn.promptness.bing.listener;

import cn.promptness.bing.config.BingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;


/**
 * Application Lifecycle Listener implementation class ContextPathListener
 *
 */
@Component
public class ContextPathListener implements ServletContextInitializer {

	@Autowired
	private BingProperties bingProperties;

	@Override
	public void onStartup(ServletContext servletContext) {
		servletContext.setAttribute("imageHost", bingProperties.getImageHost());
		servletContext.setAttribute("imageParam", bingProperties.getImageParam());
	}
}
