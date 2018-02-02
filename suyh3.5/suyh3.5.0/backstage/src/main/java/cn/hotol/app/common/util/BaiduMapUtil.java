package cn.hotol.app.common.util;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

/**
 * Created by LuBin
 * Date 2016-12-29.
 */
public class BaiduMapUtil {

    private String endpoint = "http://api.map.baidu.com/geocoder/v2/";
    private String applicationKey = "IstZcZGxIWMgybcvDntmTbvM";
    private static final String output = "json";

    /**
     * 根据地址计算经纬度
     *
     * @param address 城市名称 街道地址
     * @return
     */
    public LocationDto changeAddress(String address) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = buildRequestUrl(address);
        HttpGet httpGet = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody;
        try {
            responseBody = httpClient.execute(httpGet, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            GeocoderSearchResponseUtil geocoderSearchResponse = mapper.readValue(responseBody, GeocoderSearchResponseUtil.class);
            return geocoderSearchResponse.getResult().getLocation();
        } catch (IOException e) {
            return null;
        }
    }

    private String buildRequestUrl(String address) {
        return MessageFormat.format(
                "{0}?address={1}&ak={2}&output={3}",
                endpoint,
                utf8Encode(address),
                applicationKey, output);
    }

    private String utf8Encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static void main(String[] strings) {
        BaiduMapUtil mapUtil = new BaiduMapUtil();
        LocationDto locationDto = mapUtil.changeAddress("杭州市华丰路333号");
    }

}
