package cn.hotol.wechat;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * login: Hill Pan
 * Date: 9/11/12
 * Time: 3:03 PM
 */

@Configuration
@EnableWebMvc
public class WebAppConfig {

    @Bean
    public MappingJacksonJsonView mappingJacksonJsonView() {
        return new MappingJacksonJsonView();
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {

        final ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
	    contentNegotiatingViewResolver.setDefaultContentType(MediaType.APPLICATION_JSON);

	    final ArrayList<View> defaultViews = new ArrayList<View>();
	    defaultViews.add(mappingJacksonJsonView());

	    contentNegotiatingViewResolver.setDefaultViews(defaultViews);

	    return contentNegotiatingViewResolver;
    }

    @Bean
    public UrlBasedViewResolver urlBasedViewResolver() {

	    UrlBasedViewResolver urlBasedViewResolver = new InternalResourceViewResolver();
	    urlBasedViewResolver.setPrefix("/WEB-INF/jsp/");
	    urlBasedViewResolver.setSuffix(".jsp");
	    return urlBasedViewResolver;
    }
}
