package cn.hotol.app.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017-03-02.
 */
public class BeforeOrAfterDate {

    /**
     * 查询当前日期前(后)x天的日期
     *
     * @param date 当前日期
     * @param day  天数（如果day数为负数,说明是此日期前的天数）
     * @return yyyy-MM-dd
     */
    public String beforDayNumTimeNum(Date date, int day, String formatStr) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, day);
        return new SimpleDateFormat(formatStr).format(c.getTime());
    }

    /**
     * 查询当前日期前(后)x周的日期
     *
     * @param date 当前日期
     * @param day  天数（如果day数为负数,说明是此日期前的天数）
     * @return yyyy-MM-dd
     */
    public int beforWeekNumTimeNum(Date date, int day, String formatStr) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_YEAR, day);
        return Integer.parseInt(new SimpleDateFormat(formatStr).format(c.getTime()));
    }

    /**
     * 查询当前日期前(后)x月的日期
     *
     * @param date 当前日期
     * @param day  天数（如果day数为负数,说明是此日期前的天数）
     * @return yyyy-MM-dd
     */
    public String beforMonthNumTimeNum(Date date, int day, String formatStr) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, day);
        return new SimpleDateFormat(formatStr).format(c.getTime());
    }

    /**
     * 补全天日期数据
     *
     * @param dataMapList
     * @param date
     * @return
     */
    public List<Map<String, Object>> obtainDayDataMapList(List<Map<String, Object>> dataMapList, Date date,int size) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < dataMapList.size(); i++) {
            Map<String, Object> dataMap = dataMapList.get(i);
            map.put(dataMap.get("conditions").toString(), dataMap.get("num"));
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_YEAR, i - size + 1);
            String key = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            Object value = map.get(key);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("conditions", key);
            if (value == null || "".equals(value)) {
                dataMap.put("num", 0);
            } else {
                dataMap.put("num", value);
            }
            mapList.add(dataMap);
        }
        return mapList;
    }

    /**
     * 补全周日期数据
     *
     * @param dataMapList
     * @param date
     * @return
     */
    public List<Map<String, Object>> obtainWeekDataMapList(List<Map<String, Object>> dataMapList, Date date) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < dataMapList.size(); i++) {
            Map<String, Object> dataMap = dataMapList.get(i);
            map.put(dataMap.get("conditions").toString(), dataMap.get("num"));
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.WEEK_OF_YEAR, i - 3);
            String key = new SimpleDateFormat("yyyyww").format(c.getTime());
            Object value = map.get(key);
            Map<String, Object> dataMap = new HashMap<>();

            Calendar calStart = Calendar.getInstance();
            calStart.set(Calendar.YEAR, Integer.parseInt(key.substring(0,4))); // 2016年
            calStart.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(key.substring(4,6))); // 设置为2016年的第10周
            calStart.set(Calendar.DAY_OF_WEEK, 1); // 1表示周日，2表示周一，7表示周六

            Calendar calEnd = Calendar.getInstance();
            calEnd.set(Calendar.YEAR, Integer.parseInt(key.substring(0,4))); // 2016年
            calEnd.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(key.substring(4,6))); // 设置为2016年的第10周
            calEnd.set(Calendar.DAY_OF_WEEK, 7); // 1表示周日，2表示周一，7表示周六

            dataMap.put("conditions", new SimpleDateFormat("yyyy-MM-dd").format(calStart.getTime())+"~"+new SimpleDateFormat("MM-dd").format(calEnd.getTime()));

            if (value == null || "".equals(value)) {
                dataMap.put("num", 0);
            } else {
                dataMap.put("num", value);
            }
            mapList.add(dataMap);
        }
        return mapList;
    }

    /**
     * 补全月日期数据
     *
     * @param dataMapList
     * @param date
     * @return
     */
    public List<Map<String, Object>> obtainMonthDataMapList(List<Map<String, Object>> dataMapList, Date date) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < dataMapList.size(); i++) {
            Map<String, Object> dataMap = dataMapList.get(i);
            map.put(dataMap.get("conditions").toString(), dataMap.get("num"));
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, i - 5);
            String key = new SimpleDateFormat("yyyy-MM").format(c.getTime());
            Object value = map.get(key);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("conditions", key);
            if (value == null || "".equals(value)) {
                dataMap.put("num", 0);
            } else {
                dataMap.put("num", value);
            }
            mapList.add(dataMap);
        }
        return mapList;
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public List<Object> listReverse(List<Map<String, Object>> list){
        List<Object> newList=new ArrayList<Object>();
        for(int i=list.size();i>0;i--){
            newList.add(list.get(i-1));
        }
        return newList;
    }

}
