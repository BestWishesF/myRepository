package cn.hotol.app.common.util;

import cn.hotol.app.base.RetInfo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LuBin
 * Date 2017-01-23.
 */
public class ExcelUtil {

    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String []keys, String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    /**
     * 获取文件名称
     * @param path
     * @return
     */
    public static String getPostfix(String path) {
        if (path == null || "".equals(path.trim())) {
            return "";
        }
        if (path.contains(".")) {
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
        return "";
    }

    /**
     * 提取表格数据(2003版本)
     * @param hssfCell
     * @return
     */
    private static String getHCValue(HSSFCell hssfCell) {
        if(hssfCell==null){
            return "";
        }else {
            if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
                return String.valueOf(hssfCell.getBooleanCellValue());
            } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
//                             return String.valueOf(hssfCell.getNumericCellValue());
                DecimalFormat df = new DecimalFormat("0");
                return df.format(hssfCell.getNumericCellValue());
            } else {
                return String.valueOf(hssfCell.getStringCellValue());
            }
        }
    }

    /**
     * 提取表格数据(2010版本)
     * @param xssfRow
     * @return
     */
    private static String getXCValue(XSSFCell xssfRow) {
        if(xssfRow==null){
            return "";
        }else {
            if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
                return String.valueOf(xssfRow.getBooleanCellValue());
            } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
//                             return String.valueOf(xssfRow.getNumericCellValue());
                return String.valueOf(xssfRow.getRawValue());
            } else {
                return String.valueOf(xssfRow.getStringCellValue());
            }
        }
    }

    /**
     * 读取Excel 2003-2007数据
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static RetInfo readXls(InputStream inputStream, String exp_ord_time) throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        // Read the Sheet
        boolean is_ok=false;
        String message="";
        RetInfo retInfo=new RetInfo();
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    HSSFCell express_number = hssfRow.getCell(0);
                    HSSFCell site_name = hssfRow.getCell(1);
                    HSSFCell send_name = hssfRow.getCell(2);
                    HSSFCell send_mobile = hssfRow.getCell(3);
                    HSSFCell spare_mobile = hssfRow.getCell(4);
                    HSSFCell send_province = hssfRow.getCell(5);
                    HSSFCell send_city = hssfRow.getCell(6);
                    HSSFCell send_area = hssfRow.getCell(7);
                    HSSFCell send_address = hssfRow.getCell(8);
                    HSSFCell rev_province = hssfRow.getCell(9);
                    HSSFCell rev_city = hssfRow.getCell(10);
                    HSSFCell rev_name = hssfRow.getCell(11);
                    HSSFCell rev_mobile = hssfRow.getCell(12);
                    HSSFCell rev_area = hssfRow.getCell(13);
                    HSSFCell rev_address = hssfRow.getCell(14);

                    if(getHCValue(express_number).length()>32){
                        is_ok=true;
                        message="快递单号";
                    }
                    if(getHCValue(send_name).length()>16){
                        is_ok=true;
                        message="发件人姓名";
                    }
                    if(getHCValue(send_mobile).length()>16){
                        is_ok=true;
                        message="发件人手机号";
                    }
                    if(getHCValue(spare_mobile).length()>16){
                        is_ok=true;
                        message="发件人备选号";
                    }
                    if("".equals(getHCValue(send_province))){
                        is_ok=true;
                        message="发件人省";
                    }
                    if("".equals(getHCValue(send_city))){
                        is_ok=true;
                        message="发件人市";
                    }
                    if("".equals(getHCValue(send_area))){
                        is_ok=true;
                        message="发件人区";
                    }
                    if(getHCValue(send_address).length()>200){
                        is_ok=true;
                        message="发件人地址";
                    }
                    if(getHCValue(rev_mobile).length()>16){
                        is_ok=true;
                        message="收件人手机号";
                    }else {
                        if(!"".equals(getHCValue(rev_mobile))){
                            if("".equals(getHCValue(rev_province))){
                                is_ok=true;
                                message="收件人省";
                            }
                            if("".equals(getHCValue(rev_city))){
                                is_ok=true;
                                message="收件人市";
                            }
                            if("".equals(getHCValue(rev_area))){
                                is_ok=true;
                                message="收件人区";
                            }
                            if(getHCValue(rev_name).length()>16){
                                is_ok=true;
                                message="收件人姓名";
                            }
                            if(getHCValue(rev_address).length()>200){
                                is_ok=true;
                                message="收件人地址";
                            }
                        }
                    }

                    if(!is_ok){
                        int time=Integer.parseInt(exp_ord_time.replace("-",""));
                        if(time>=20170201){
                            if(getHCValue(express_number)!=null&&!"".equals(getHCValue(express_number).trim())&&!"0".equals(getHCValue(express_number).trim())){
                                if((getHCValue(send_mobile)!=null&&!"".equals(getHCValue(send_mobile).trim())&&!"0".equals(getHCValue(send_mobile).trim()))||(getHCValue(spare_mobile)!=null&&!"".equals(getHCValue(spare_mobile).trim())&&!"0".equals(getHCValue(spare_mobile).trim()))){
                                    Map<String, Object> map=new HashMap<String, Object>();
                                    map.put("express_number",getHCValue(express_number));
                                    map.put("site_name",getHCValue(site_name));
                                    map.put("send_name",getHCValue(send_name));
                                    if(getHCValue(send_mobile)!=null&&!"".equals(getHCValue(send_mobile).trim())&&!"0".equals(getHCValue(send_mobile).trim())){
                                        map.put("send_mobile",getHCValue(send_mobile));
                                    }else{
                                        map.put("send_mobile",getHCValue(spare_mobile));
                                    }
                                    map.put("send_province",getHCValue(send_province));
                                    map.put("send_city",getHCValue(send_city));
                                    map.put("send_area",getHCValue(send_area));
                                    map.put("send_address",getHCValue(send_address));
                                    map.put("rev_province",getHCValue(rev_province));
                                    map.put("rev_city",getHCValue(rev_city));
                                    map.put("rev_name",getHCValue(rev_name));
                                    map.put("rev_mobile",getHCValue(rev_mobile));
                                    map.put("rev_area",getHCValue(rev_area));
                                    map.put("rev_address",getHCValue(rev_address));
                                    mapList.add(map);
                                }
                            }
                        }else {
                            if((getHCValue(send_mobile)!=null&&!"".equals(getHCValue(send_mobile).trim())&&!"0".equals(getHCValue(send_mobile).trim()))||(getHCValue(spare_mobile)!=null&&!"".equals(getHCValue(spare_mobile).trim())&&!"0".equals(getHCValue(spare_mobile).trim()))){
                                Map<String, Object> map=new HashMap<String, Object>();
                                map.put("express_number",getHCValue(express_number));
                                map.put("site_name",getHCValue(site_name));
                                map.put("send_name",getHCValue(send_name));
                                if(getHCValue(send_mobile)!=null&&!"".equals(getHCValue(send_mobile).trim())&&!"0".equals(getHCValue(send_mobile).trim())){
                                    map.put("send_mobile",getHCValue(send_mobile));
                                }else{
                                    map.put("send_mobile",getHCValue(spare_mobile));
                                }
                                map.put("send_province",getHCValue(send_province));
                                map.put("send_city",getHCValue(send_city));
                                map.put("send_area",getHCValue(send_area));
                                map.put("send_address",getHCValue(send_address));
                                map.put("rev_province",getHCValue(rev_province));
                                map.put("rev_city",getHCValue(rev_city));
                                map.put("rev_name",getHCValue(rev_name));
                                map.put("rev_mobile",getHCValue(rev_mobile));
                                map.put("rev_area",getHCValue(rev_area));
                                map.put("rev_address",getHCValue(rev_address));
                                mapList.add(map);
                            }
                        }
                    }else {
                        message="第"+rowNum+"数据"+message+"有误";
                        break;
                    }
                }
            }
        }
        if(is_ok){
            retInfo.setMark("1");
            retInfo.setTip(message);
        }else {
            retInfo.setMark("0");
            retInfo.setObj(mapList);
        }
        return retInfo;
    }

    /**
     * 读取Excel 2010数据
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static RetInfo readXlsx(InputStream inputStream, String exp_ord_time) throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        // Read the Sheet
        boolean is_ok=false;
        String message="";
        RetInfo retInfo=new RetInfo();
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    XSSFCell express_number = xssfRow.getCell(0);
                    XSSFCell site_name = xssfRow.getCell(1);
                    XSSFCell send_name = xssfRow.getCell(2);
                    XSSFCell send_mobile = xssfRow.getCell(3);
                    XSSFCell spare_mobile = xssfRow.getCell(4);
                    XSSFCell send_province = xssfRow.getCell(5);
                    XSSFCell send_city = xssfRow.getCell(6);
                    XSSFCell send_area = xssfRow.getCell(7);
                    XSSFCell send_address = xssfRow.getCell(8);
                    XSSFCell rev_province = xssfRow.getCell(9);
                    XSSFCell rev_city = xssfRow.getCell(10);
                    XSSFCell rev_name = xssfRow.getCell(11);
                    XSSFCell rev_mobile = xssfRow.getCell(12);
                    XSSFCell rev_area = xssfRow.getCell(13);
                    XSSFCell rev_address = xssfRow.getCell(14);

                    if(getXCValue(express_number).length()>32){
                        is_ok=true;
                        message="快递单号";
                    }
                    if(getXCValue(send_name).length()>16){
                        is_ok=true;
                        message="发件人姓名";
                    }
                    if(getXCValue(send_mobile).length()>16){
                        is_ok=true;
                        message="发件人手机号";
                    }
                    if(getXCValue(spare_mobile).length()>16){
                        is_ok=true;
                        message="发件人备选号";
                    }
                    if("".equals(getXCValue(send_province))){
                        is_ok=true;
                        message="发件人省";
                    }
                    if("".equals(getXCValue(send_city))){
                        is_ok=true;
                        message="发件人市";
                    }
                    if("".equals(getXCValue(send_area))){
                        is_ok=true;
                        message="发件人区";
                    }
                    if(getXCValue(send_address).length()>200){
                        is_ok=true;
                        message="发件人地址";
                    }
                    if(getXCValue(rev_mobile).length()>16){
                        is_ok=true;
                        message="收件人手机号";
                    }else {
                        if(!"".equals(getXCValue(rev_mobile))){
                            if("".equals(getXCValue(rev_province))){
                                is_ok=true;
                                message="收件人省";
                            }
                            if("".equals(getXCValue(rev_city))){
                                is_ok=true;
                                message="收件人市";
                            }
                            if("".equals(getXCValue(rev_area))){
                                is_ok=true;
                                message="收件人区";
                            }
                            if(getXCValue(rev_name).length()>16){
                                is_ok=true;
                                message="收件人姓名";
                            }
                            if(getXCValue(rev_address).length()>200){
                                is_ok=true;
                                message="收件人地址";
                            }
                        }
                    }

                    if(!is_ok){
                        int time=Integer.parseInt(exp_ord_time.replace("-",""));
                        if(time>=20170201){
                            if(getXCValue(express_number)!=null&&!"".equals(getXCValue(express_number).trim())&&!"0".equals(getXCValue(express_number).trim())) {
                                if ((getXCValue(send_mobile) != null && !"".equals(getXCValue(send_mobile).trim()) && !"0".equals(getXCValue(send_mobile).trim())) || (getXCValue(spare_mobile) != null && !"".equals(getXCValue(spare_mobile).trim()) && !"0".equals(getXCValue(spare_mobile).trim()))) {
                                    Map<String, Object> map=new HashMap<String, Object>();
                                    map.put("express_number",getXCValue(express_number));
                                    map.put("site_name",getXCValue(site_name));
                                    map.put("send_name",getXCValue(send_name));
                                    if(getXCValue(send_mobile) != null && !"".equals(getXCValue(send_mobile).trim()) && !"0".equals(getXCValue(send_mobile).trim())){
                                        map.put("send_mobile",getXCValue(send_mobile));
                                    }else{
                                        map.put("send_mobile",getXCValue(spare_mobile));
                                    }
                                    map.put("send_province",getXCValue(send_province));
                                    map.put("send_city",getXCValue(send_city));
                                    map.put("send_area",getXCValue(send_area));
                                    map.put("send_address",getXCValue(send_address));
                                    map.put("rev_province",getXCValue(rev_province));
                                    map.put("rev_city",getXCValue(rev_city));
                                    map.put("rev_name",getXCValue(rev_name));
                                    map.put("rev_mobile",getXCValue(rev_mobile));
                                    map.put("rev_area",getXCValue(rev_area));
                                    map.put("rev_address",getXCValue(rev_address));
                                    mapList.add(map);
                                }
                            }
                        }else {
                            if ((getXCValue(send_mobile) != null && !"".equals(getXCValue(send_mobile).trim()) && !"0".equals(getXCValue(send_mobile).trim())) || (getXCValue(spare_mobile) != null && !"".equals(getXCValue(spare_mobile).trim()) && !"0".equals(getXCValue(spare_mobile).trim()))) {
                                Map<String, Object> map=new HashMap<String, Object>();
                                map.put("express_number",getXCValue(express_number));
                                map.put("site_name",getXCValue(site_name));
                                map.put("send_name",getXCValue(send_name));
                                if(getXCValue(send_mobile) != null && !"".equals(getXCValue(send_mobile).trim()) && !"0".equals(getXCValue(send_mobile).trim())){
                                    map.put("send_mobile",getXCValue(send_mobile));
                                }else{
                                    map.put("send_mobile",getXCValue(spare_mobile));
                                }
                                map.put("send_province",getXCValue(send_province));
                                map.put("send_city",getXCValue(send_city));
                                map.put("send_area",getXCValue(send_area));
                                map.put("send_address",getXCValue(send_address));
                                map.put("rev_province",getXCValue(rev_province));
                                map.put("rev_city",getXCValue(rev_city));
                                map.put("rev_name",getXCValue(rev_name));
                                map.put("rev_mobile",getXCValue(rev_mobile));
                                map.put("rev_area",getXCValue(rev_area));
                                map.put("rev_address",getXCValue(rev_address));
                                mapList.add(map);
                            }
                        }
                    }else {
                        message="第"+rowNum+"数据"+message+"有误";
                        break;
                    }
                }
            }
        }
        if(is_ok){
            retInfo.setMark("1");
            retInfo.setTip(message);
        }else {
            retInfo.setMark("0");
            retInfo.setObj(mapList);
        }
        return retInfo;
    }

    /**
     * 生成随机时间
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date randomDate(String beginDate, String endDate){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(beginDate);//构造开始日期
            Date end = format.parse(endDate);//构造结束日期
            //getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if(start.getTime() >= end.getTime()){
                return null;
            }
            long date = random(start.getTime(),end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long random(long begin,long end){
        long rtn = begin + (long)(Math.random() * (end - begin));
        //如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if(rtn == begin || rtn == end){
            return random(begin,end);
        }
        return rtn;
    }

}
