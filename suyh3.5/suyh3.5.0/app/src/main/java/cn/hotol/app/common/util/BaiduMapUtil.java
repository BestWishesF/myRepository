package cn.hotol.app.common.util;

import cn.hotol.app.bean.dto.location.AddressDto;
import cn.hotol.app.bean.dto.location.ChangeAddressDto;
import cn.hotol.app.bean.dto.location.ChangeLngLonDto;
import cn.hotol.app.bean.dto.location.LocationDto;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
            ChangeLngLonDto changeLngLonDto = mapper.readValue(responseBody, ChangeLngLonDto.class);
            return changeLngLonDto.getResult().getLocation();
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

    /**
     * 根据地址计算经纬度
     *
     * @param latitude 纬度
     * @param longitude 经度
     * @return
     */
    public AddressDto changeLngAndLat(BigDecimal latitude, BigDecimal longitude) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = buildAddressRequestUrl(latitude, longitude);
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
            ChangeAddressDto changeAddressDto = mapper.readValue(responseBody, ChangeAddressDto.class);
            return changeAddressDto.getResult().getAddressComponent();
        } catch (IOException e) {
            return null;
        }
    }

    private String buildAddressRequestUrl(BigDecimal latitude, BigDecimal longitude) {
        return MessageFormat.format(
                "{0}?location={1},{2}&output={3}&pois=0&ak={4}", endpoint,
                latitude, longitude, output, applicationKey);
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
        AddressDto addressDto =  mapUtil.changeLngAndLat(new BigDecimal(30.34492), new BigDecimal(120.20201));
    }

}
