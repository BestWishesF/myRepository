package cn.hotol.wechat.domain.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;

import java.util.List;

/**
 * login: Hill Pan
 * Date: 7/4/12
 * Time: 3:35 PM
 */
public class W13JacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {

    private List<W13Serializer> w13SerializerList;

    public void setW13SerializerList(List<W13Serializer> w13SerializerList) {
        this.w13SerializerList = w13SerializerList;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setAnnotationIntrospector(this);
    }

    @Override
    public Object findSerializer(Annotated a) {

        for (W13Serializer w13Serializer : w13SerializerList) {
            if (a.hasAnnotation(w13Serializer.targetAnnotation())) {
                return w13Serializer;
            }
        }
        return super.findSerializer(a);
    }
}