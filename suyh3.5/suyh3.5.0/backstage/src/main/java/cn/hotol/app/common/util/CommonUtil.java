package cn.hotol.app.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lizhun on 2016/12/1.
 */
public class CommonUtil {
    private static SimpleDateFormat sdf_month = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat sdf_date_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf_date_times = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * @Purpose  生成token
     * @author   lizhun
     * @version  1.0
     * @return   String
     */
    public static String getToken(){
        UUID token = UUID.randomUUID();
        return ""+token;
    }
    /**
     * @Purpose  获取当前月份
     * @author   lizhun
     * @version  1.0
     * @return   String
     */
    public static   String getMonth(){
        Date date = new Date();
        return sdf_month.format(date);
    }
    /**
     * @Purpose  分页参数
     * @author   lizhun
     * @version  1.0
     * @return   String
     */
    public static Map<String, Object> page(int totalRecord ,int currentPage ,int pageSize){
        int totalPage;
        if (totalRecord % pageSize == 0) {
            totalPage = totalRecord/pageSize;
        } else {
            totalPage = (totalRecord/pageSize) + 1;
        }
        if (currentPage >= totalPage) {
            currentPage = totalPage;
        }
        if (currentPage <= 1) {
            currentPage = 1;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("currentPage", (currentPage - 1) * pageSize);
        map.put("pageSize",pageSize);
        map.put("totalRecord",totalRecord);
        map.put("totalPage",totalPage);
        return map;
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
     * @Purpose  获取当前日期
     * @author   lubin
     * @version  1.0
     * @return   String
     */
    public static String getDate(){
        Date date = new Date();
        return sdf_date.format(date);
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

    /**
     * @Purpose  获取编号
     * @author   lizhun
     * @version  1.0
     * @return   String
     */
    public static String getOrderNub(Date date) {
        String orderNub = sdf.format(date).substring(2, 12);

        String code = "";
        while(code.length()<5)
        {
            code+=(int)(Math.random()*10);
        }
        orderNub = orderNub + code;
        return orderNub;
    }
}
