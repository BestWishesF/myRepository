package cn.hotol.app.common;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * spring 容器
 */
public class SpringInfo {
	public static WebApplicationContext SPRING_CONTEXT = null;

	/**
	 * 初始化springcontext
	 * @param servletContext
	 */
	public static void init(ServletContext servletContext) {
		SPRING_CONTEXT = (WebApplicationContext) servletContext
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}

	/**
	 * 获取spring bean
	 * @param beanName
	 */
	public static Object getBean(String beanName) {
		return SpringInfo.SPRING_CONTEXT.getBean(beanName);
	}

}
