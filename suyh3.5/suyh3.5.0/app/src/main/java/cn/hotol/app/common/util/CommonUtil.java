package cn.hotol.app.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by lizhun on 2016/12/1.
 */
public class CommonUtil {
    private static SimpleDateFormat sdf_month = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat sdf_hour = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat sdf_date_times = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf_date_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * @return String
     * @Purpose 生成token
     * @author lizhun
     * @version 1.0
     */
    public static String getToken() {
        UUID token = UUID.randomUUID();
        return "" + token;
    }

    /**
     * @return String
     * @Purpose 获取当前月份
     * @author lizhun
     * @version 1.0
     */
    public static String getMonth() {
        Date date = new Date();
        return sdf_month.format(date);
    }

    /**
     * @return int
     * @Purpose 获取当前小时
     * @author lubin
     * @version 1.0
     */
    public static int getCurrentHour() {
        Date date = new Date();
        return date.getHours();
    }

    /**
     * @return int
     * @Purpose 获取小时数
     * @author lubin
     * @version 1.0
     */
    public static int getHour(String hourStr) {
        int hour = 0;
        try {
            hour = sdf_hour.parse(hourStr).getHours();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hour;
    }
    /**
     * @Purpose  获取编号
     * @author   lizhun
     * @version  1.0
     * @return   String
     */
    public static String getOrderNub() {
        Date date = new Date();
        String orderNub = sdf.format(date).substring(2, 12);

        String code = "";
        while(code.length()<5)
        {
            code+=(int)(Math.random()*10);
        }
        orderNub = orderNub + code;
        return orderNub;
    }

    /**
     * @Purpose  获取距离
     * @author   lubin
     * @version  1.0
     * @param lngOne 第一个坐标点的经度
     * @param latOne 第一个坐标点的纬度
     * @param lngTwo 第二个坐标点的经度
     * @param latTwo 第二个坐标点的纬度
     * @return double
     */
    public static double getDistance(double lngOne, double latOne, double lngTwo, double latTwo) {
        double earthRadius = 6378137; // 地球半径
        latOne = latOne * Math.PI / 180.0;
        latTwo = latTwo * Math.PI / 180.0;
        double lat = latOne - latTwo;
        double lng = (lngOne - lngTwo) * Math.PI / 180.0;
        double latSa = Math.sin(lat / 2.0);
        double LngSa = Math.sin(lng / 2.0);
        double distance = 2 * earthRadius * Math.asin(Math.sqrt(latSa * latSa + Math.cos(latOne) * Math.cos(latTwo) * LngSa * LngSa));
        return Math.round(distance);
    }

    /**
     * @Purpose  获取当前日期与时间(24小时制)
     * @author   lubin
     * @version  1.0
     * @return   String
     */
    public static String getDateAndTimes(){
        Date date = new Date();
        return sdf_date_times.format(date);
    }

    /**
     * @Purpose  获取当前日期与时间
     * @author   lubin
     * @version  1.0
     * @return   String
     */
    public static String getDateAndTime(){
        Date date = new Date();
        return sdf_date_time.format(date);
    }

    /**
     * @Purpose  根据月份和id获取提交给快递公司的订单编号
     * @author   lubin
     * @version  1.0
     * @return   String
     */
    public static String getOrderNo(Date date, Integer id){
        String dateStr=sdf_month.format(date);
        String idStr=String.valueOf(id);
        if(idStr.length()<9){
            int num=9-idStr.length();
            for(int i=0;i<num;i++){
                idStr="0"+idStr;
            }
        }
        return dateStr+idStr;
    }
}
