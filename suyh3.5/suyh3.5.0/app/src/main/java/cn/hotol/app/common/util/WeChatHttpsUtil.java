package cn.hotol.app.common.util;

import cn.hotol.app.bean.dto.thirdparty.TsHtThirdDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.three.certificate.CertificateServiceImpl;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-03.
 */
public class WeChatHttpsUtil {

    private static Logger logger = Logger.getLogger(WeChatHttpsUtil.class);

    /**
     * https请求微信api
     *
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     * @return
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        String result = "";
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new CertificateServiceImpl()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            result = buffer.toString();
        } catch (ConnectException ce) {
            logger.error("Weixin server connection timed out.");
        } catch (Exception e) {
            logger.error("https request error:{}");
        }
        return result;
    }

    /**
     * get请求微信api
     *
     * @param url
     * @return
     */
    public static String requestMessageApi(URL url) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
            BufferedReader bufReader = new BufferedReader(input);
            String line = "";
            StringBuilder contentBuf = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                contentBuf.append(line);
            }
            String resultBuf = contentBuf.toString();
            return resultBuf;
        } catch (Exception e) {
            logger.error("https request error:{}");
        }
        return null;
    }

    /**
     * 获取微信网页AccessToken
     *
     * @param tsHtThirdDto
     * @param code
     * @return
     */
    public static Map obtainAccessToken(TsHtThirdDto tsHtThirdDto, String code) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 第三方用户唯一凭证 订阅号的
        String appId = tsHtThirdDto.getAppid();
        // 第三方用户唯一凭证密钥
        String appSecret = tsHtThirdDto.getAppsecret();
        //通过code换取网页授权access_token,42001 access_token超时
        try {
            String appIdV = URLEncoder.encode(appId, "UTF-8");
            String appSecretV = URLEncoder.encode(appSecret, "UTF-8");
            String codeV = URLEncoder.encode(code, "UTF-8");
            String weChatTokenUrl = Constant.WEBPAGE_ACCESS_TOKEN_URL.replace("##APPID##", appIdV).replace("##SECRET##", appSecretV).replace("##CODE##", codeV);
            URL weChatToken = new URL(weChatTokenUrl);
            String jsonTaken = requestMessageApi(weChatToken);
            JsonNode jsonNodeTaken = objectMapper.readTree(jsonTaken);
            String access_token = jsonNodeTaken.path("access_token").asText();
            String expires_in = jsonNodeTaken.path("expires_in").asText();
            String refresh_token = jsonNodeTaken.path("refresh_token").asText();
            String openid = jsonNodeTaken.path("openid").asText();
            String scope = jsonNodeTaken.path("scope").asText();

            Map<String, Object> map = new HashedMap();
            map.put("access_token", access_token);
            map.put("expires_in", expires_in);
            map.put("refresh_token", refresh_token);
            map.put("openid", openid);
            map.put("scope", scope);

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 刷新微信网页AccessToken
     *
     * @param tsHtThirdDto
     * @param refreshToken
     * @return
     */
    public static Map refreshAccessToken(TsHtThirdDto tsHtThirdDto, String refreshToken) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 第三方用户唯一凭证 订阅号的
        String appId = tsHtThirdDto.getAppid();
        //刷新access_token
        try {
            String appIdV = URLEncoder.encode(appId, "UTF-8");
            String refreshTokenV = URLEncoder.encode(refreshToken, "UTF-8");
            String weChatTokenUrl = Constant.WEBPAGE_REFRESH_TOKEN_URL.replace("##APPID##", appIdV).replace("##REFRESH_TOKEN##", refreshTokenV);
            URL weChatToken = new URL(weChatTokenUrl);
            String jsonTaken = requestMessageApi(weChatToken);
            JsonNode jsonNodeTaken = objectMapper.readTree(jsonTaken);
            String access_token = jsonNodeTaken.path("access_token").asText();
            String expires_in = jsonNodeTaken.path("expires_in").asText();
            String refresh_token = jsonNodeTaken.path("refresh_token").asText();
            String openid = jsonNodeTaken.path("openid").asText();
            String scope = jsonNodeTaken.path("scope").asText();

            Map<String, Object> map = new HashedMap();
            map.put("access_token", access_token);
            map.put("expires_in", expires_in);
            map.put("refresh_token", refresh_token);
            map.put("openid", openid);
            map.put("scope", scope);

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取微信用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public static Map obtainWeChatUserInfo(String accessToken, String openId) {
        ObjectMapper objectMapper = new ObjectMapper();
        //获取微信用户信息
        try {
            String accessTokenV = URLEncoder.encode(accessToken, "UTF-8");
            String openIdV = URLEncoder.encode(openId, "UTF-8");
            String weChatTokenUrl = Constant.WE_CHAT_USER_INFO_URL.replace("##ACCESS_TOKEN##", accessTokenV).replace("##OPENID##", openIdV);
            URL weChatToken = new URL(weChatTokenUrl);
            String jsonTaken = requestMessageApi(weChatToken);
            JsonNode jsonNodeTaken = objectMapper.readTree(jsonTaken);
            String openid = jsonNodeTaken.path("openid").asText();
            String nickname = jsonNodeTaken.path("nickname").asText();
            String sex = jsonNodeTaken.path("sex").asText();
            String province = jsonNodeTaken.path("province").asText();
            String city = jsonNodeTaken.path("city").asText();
            String country = jsonNodeTaken.path("country").asText();
            String headimgurl = jsonNodeTaken.path("headimgurl").asText();

            Map<String, Object> map = new HashedMap();
            map.put("openid", openid);
            map.put("nickname", nickname);
            map.put("sex", sex);
            map.put("province", province);
            map.put("city", city);
            map.put("country", country);
            map.put("headimgurl", headimgurl);

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用拿到的access_token 采用http GET方式请求获得jsapi_ticket
     *
     * @param access_token
     * @return
     */
    public static String obtainJssdkTicket(String access_token){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String ACCESS_TOKEN=URLEncoder.encode(access_token,"UTF-8");
            String jsapiTicketUrl=Constant.JS_API_TICKET_URL.replace("##ACCESS_TOKEN##", ACCESS_TOKEN);
            URL jsapiTicket = new URL(jsapiTicketUrl);
            String jsonJsapiTicket=requestMessageApi(jsapiTicket);
            JsonNode jsonNodeRefToken = objectMapper.readTree(jsonJsapiTicket);
            String ticket=jsonNodeRefToken.path("ticket").asText();
            return ticket;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取access_token
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return
     */
    public static String obtainAccessToken(String appid, String appsecret) {
        String requestUrl = Constant.ACCESS_TOKEN_URL.replace("##APPID##", appid).replace("##APPSECRET##", appsecret);
        String request = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        try {
            JSONObject jsonObject=JSONObject.fromObject(request);
            String access_token=jsonObject.getString("access_token");
            return access_token;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
