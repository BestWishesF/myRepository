package cn.hotol.wechat.domain.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * login: Hill Pan
 * Date: 7/4/12
 * Time: 3:36 PM
 */
public class ContextAwareJsonSerializer
        extends JsonSerializer<Serializable> implements W13Serializer, ServletContextAware {

    private String contextPath = "";

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void serialize(Serializable value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeString(contextPath + "" + value);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        contextPath = servletContext.getContextPath();
    }

    @Override
    public Class<? extends Annotation> targetAnnotation() {
        return ContextAware.class;
    }
}

