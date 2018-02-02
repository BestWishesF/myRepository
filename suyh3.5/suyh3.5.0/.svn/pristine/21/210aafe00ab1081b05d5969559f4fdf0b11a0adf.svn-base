package cn.hotol.app.common.json;

import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.AESUtils;
import com.fasterxml.jackson.databind.JavaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Type;

public class CustMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        JavaType javaType = getJavaType(type, contextClass);
        return readJavaType(javaType, inputMessage);
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return super.readInternal(clazz, inputMessage);
    }

    @SuppressWarnings("deprecation")
    private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
        InputStream inputStream = null;
        ByteArrayOutputStream outSteam = null;
        try {
            // 获取输入流
            inputStream = inputMessage.getBody();
            // 如果输入流不能重复读取，新建一个流
            if (inputStream.markSupported()) {
                inputStream.mark(1);
                if (inputStream.read() == -1) {
                    return null;
                }
                inputStream.reset();
            } else {
                final PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
                int b = pushbackInputStream.read();
                if (b == -1) {
                    return null;
                } else {
                    pushbackInputStream.unread(b);
                }
                inputStream = pushbackInputStream;
            }
            // 读取流的内容
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            String msg = new String(outSteam.toByteArray());

            MediaType contentType = inputMessage.getHeaders().getContentType();
            if ("application/json;charset=UTF-8".equals(contentType.toString()) && msg != null) {
                msg = AESUtils.decrypt(msg, Constant.AES_KEY);
            }

            // 将流里的内容用objectMapper转换
            if (inputMessage instanceof MappingJacksonInputMessage) {
                Class<?> deserializationView = ((MappingJacksonInputMessage) inputMessage).getDeserializationView();
                if (deserializationView != null) {
                    return this.objectMapper.readerWithView(deserializationView).withType(javaType).
                            readValue(msg);
                }
            }
            return this.objectMapper.readValue(msg, javaType);
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("Could not read document: " + ex.getMessage(), ex);
        } finally {
            try {
                if (outSteam != null) {
                    outSteam.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new HttpMessageNotReadableException("Could not read document: " + e.getMessage(), e);
            }

        }
    }

    /**
     * 重写writeInternal方法，在返回内容前首先进行加密
     * @param object
     * @param type
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //使用Jackson的ObjectMapper将Java对象转换成Json String
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);
        //解密
        String encrypt = AESUtils.encrypt(json, Constant.AES_KEY);
        //输出
        outputMessage.getBody().write(encrypt.getBytes());
    }
}
