package cn.hotol.wechat.servlet;

import cn.hotol.wechat.domain.json.ContextAwareJsonSerializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * login: Hill Pan
 * Date: 7/4/12
 * Time: 3:50 PM
 */
public class InitServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();

        WebApplicationContext webApplicationContext =
                WebApplicationContextUtils.getWebApplicationContext(servletContext);

        ContextAwareJsonSerializer contextAwareJsonSerializer =
                webApplicationContext.getBean(ContextAwareJsonSerializer.class);

        contextAwareJsonSerializer.setContextPath(servletContext.getContextPath());
    }
}
