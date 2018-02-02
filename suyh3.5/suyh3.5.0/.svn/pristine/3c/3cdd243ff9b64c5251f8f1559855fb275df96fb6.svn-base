package cn.hotol.wechat.domain.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * login: Hill Pan
 * Date: 1/30/12
 * Time: 6:10 PM
 */
public class JSONResolver implements WebArgumentResolver {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {

        JSONRequestBody jsonRequestBody =
                methodParameter.getParameterAnnotation(JSONRequestBody.class);

        if (jsonRequestBody == null) return UNRESOLVED;

        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);

        final Type genericParameterType = methodParameter.getGenericParameterType();

        return mapper.readValue(
                servletServerHttpRequest.getBody(),
                new TypeReference<Object>() {
                    @Override
                    public Type getType() {
                        return genericParameterType;
                    }
                });
    }
}
