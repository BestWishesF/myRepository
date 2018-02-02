package cn.hotol.app.common.util;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.location.AreaDto;
import cn.hotol.app.bean.dto.location.CityDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 提取表格数据(2010版本)
     * @param xssfRow
     * @return
     */
    private static String getXCValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
//                             return String.valueOf(xssfRow.getNumericCellValue());
            return String.valueOf(xssfRow.getRawValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    /**
     * 读取Excel 2003-2007数据
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static RetInfo readXls(InputStream inputStream) throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
        RetInfo retInfo=new RetInfo();
        retInfo.setMark("0");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        // Read the Sheet
        //从字典中获取省市区
        List<ProvinceDto> provinceDtoList = (List<ProvinceDto>) MemcachedUtils.get(MemcachedKey.PROVINCIAL_CITY);
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    HSSFCell ht_number = hssfRow.getCell(0);
                    HSSFCell add_name = hssfRow.getCell(1);
                    HSSFCell add_mobile = hssfRow.getCell(2);
                    HSSFCell add_address = hssfRow.getCell(3);
                    HSSFCell weight = hssfRow.getCell(4);
                    HSSFCell exp_ord_category = hssfRow.getCell(5);

                    if(getHCValue(ht_number)==null||"".equals(getHCValue(ht_number).trim())||"0".equals(getHCValue(ht_number).trim())){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单厚通单号不能为空.");
                        break;
                    }
                    if(getHCValue(add_name)==null||"".equals(getHCValue(add_name).trim())||"0".equals(getHCValue(add_name).trim())){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单收件人姓名不能为空.");
                        break;
                    }
                    if(getHCValue(add_mobile)==null||"".equals(getHCValue(add_mobile).trim())||"0".equals(getHCValue(add_mobile).trim())){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单收件人手机号不能为空.");
                        break;
                    }
                    if(getHCValue(add_address)==null||"".equals(getHCValue(add_address).trim())||"0".equals(getHCValue(add_address).trim())){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单收件人地址不能为空.");
                        break;
                    }
                    if(getHCValue(exp_ord_category)==null||"".equals(getHCValue(exp_ord_category).trim())||"0".equals(getHCValue(exp_ord_category).trim())){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单货物名称不能为空.");
                        break;
                    }

                    Map<String, Object> map=new HashMap<String, Object>();
                    map.put("ht_number",getHCValue(ht_number));
                    map.put("add_name",getHCValue(add_name));
                    map.put("add_mobile_phone",getHCValue(add_mobile));
                    String address=getHCValue(add_address);
                    boolean is_address=false;
                    for(int i=0;i<provinceDtoList.size();i++){
                        ProvinceDto provinceDto=provinceDtoList.get(i);
                        if(address.indexOf(provinceDto.getProvince_name())>=0){
                            map.put("add_province",provinceDto.getDict_id());
                            map.put("add_province_name",provinceDto.getProvince_name());
                            List<CityDto> cityDtoList=provinceDto.getCity_list();
                            for(int j=0;j<cityDtoList.size();j++){
                                CityDto cityDto=cityDtoList.get(j);
                                if(address.indexOf(cityDto.getCity_name())>=0){
                                    map.put("add_city",cityDto.getDict_id());
                                    map.put("add_city_name",cityDto.getCity_name());
                                    List<AreaDto> areaDtoList=cityDto.getArea_list();
                                    for(int k=0;k<areaDtoList.size();k++){
                                        AreaDto areaDto=areaDtoList.get(k);
                                        if(address.indexOf(areaDto.getArea_name())>0){
                                            map.put("add_region",areaDto.getDict_id());
                                            map.put("add_region_name",areaDto.getArea_name());
                                            String add_detail_address = getHCValue(add_address);
                                            String detail_address = provinceDto.getProvince_name() + cityDto.getCity_name() + areaDto.getArea_name();
                                            add_detail_address = add_detail_address.substring(add_detail_address.indexOf(detail_address) + detail_address.length(), add_detail_address.length());
                                            map.put("add_detail_address",add_detail_address);
                                            is_address=true;
                                            break;
                                        }else {
                                            is_address=false;
                                        }
                                    }
                                    break;
                                }else {
                                    is_address=false;
                                }
                            }
                            break;
                        }else {
                            is_address=false;
                        }
                    }
                    if(!is_address){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单收件人地址不正确.");
                        break;
                    }
                    if(getHCValue(weight)==null||"".equals(getHCValue(weight).trim())){
                        map.put("exp_ord_clt_height",1);
                    }else {
                        map.put("exp_ord_clt_height",getHCValue(weight));
                    }
                    map.put("exp_ord_category", getHCValue(exp_ord_category));
                    mapList.add(map);
                }
            }
        }
        retInfo.setObj(mapList);
        return retInfo;
    }

    /**
     * 读取Excel 2010数据
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static RetInfo readXlsx(InputStream inputStream) throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        RetInfo retInfo=new RetInfo();
        retInfo.setMark("0");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            //从字典中获取省市区
            List<ProvinceDto> provinceDtoList = (List<ProvinceDto>) MemcachedUtils.get(MemcachedKey.PROVINCIAL_CITY);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    XSSFCell ht_number = xssfRow.getCell(0);
                    XSSFCell add_name = xssfRow.getCell(1);
                    XSSFCell add_mobile = xssfRow.getCell(2);
                    XSSFCell add_address = xssfRow.getCell(3);
                    XSSFCell weight = xssfRow.getCell(4);
                    XSSFCell exp_ord_category = xssfRow.getCell(5);

                    if(getXCValue(ht_number)==null||"".equals(getXCValue(ht_number).trim())||"0".equals(getXCValue(ht_number).trim())){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单厚通单号不能为空.");
                        break;
                    }
                    if(getXCValue(add_name)==null||"".equals(getXCValue(add_name).trim())||"0".equals(getXCValue(add_name).trim())){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单收件人姓名不能为空.");
                        break;
                    }
                    if(getXCValue(add_mobile)==null||"".equals(getXCValue(add_mobile).trim())||"0".equals(getXCValue(add_mobile).trim())){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单收件人手机号不能为空.");
                        break;
                    }
                    if(getXCValue(add_address)==null||"".equals(getXCValue(add_address).trim())||"0".equals(getXCValue(add_address).trim())){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单收件人地址不能为空.");
                        break;
                    }
                    if(getXCValue(exp_ord_category)==null||"".equals(getXCValue(exp_ord_category).trim())||"0".equals(getXCValue(exp_ord_category).trim())){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单货物名称不能为空.");
                        break;
                    }

                    Map<String, Object> map=new HashMap<String, Object>();
                    map.put("ht_number",getXCValue(ht_number));
                    map.put("add_name",getXCValue(add_name));
                    map.put("add_mobile_phone",getXCValue(add_mobile));
                    String address=getXCValue(add_address);
                    boolean is_address=false;
                    for(int i=0;i<provinceDtoList.size();i++){
                        ProvinceDto provinceDto=provinceDtoList.get(i);
                        if(address.indexOf(provinceDto.getProvince_name())>=0){
                            map.put("add_province",provinceDto.getDict_id());
                            map.put("add_province_name",provinceDto.getProvince_name());
                            List<CityDto> cityDtoList=provinceDto.getCity_list();
                            for(int j=0;j<cityDtoList.size();j++){
                                CityDto cityDto=cityDtoList.get(j);
                                if(address.indexOf(cityDto.getCity_name())>=0){
                                    map.put("add_city",cityDto.getDict_id());
                                    map.put("add_city_name",cityDto.getCity_name());
                                    List<AreaDto> areaDtoList=cityDto.getArea_list();
                                    for(int k=0;k<areaDtoList.size();k++){
                                        AreaDto areaDto=areaDtoList.get(k);
                                        if(address.indexOf(areaDto.getArea_name())>0){
                                            map.put("add_region",areaDto.getDict_id());
                                            map.put("add_region_name",areaDto.getArea_name());
                                            String add_detail_address = getXCValue(add_address);
                                            String detail_address = provinceDto.getProvince_name() + cityDto.getCity_name() + areaDto.getArea_name();
                                            add_detail_address = add_detail_address.substring(add_detail_address.indexOf(detail_address) + detail_address.length(), add_detail_address.length());
                                            map.put("add_detail_address", add_detail_address);
                                            is_address=true;
                                            break;
                                        }else {
                                            is_address=false;
                                        }
                                    }
                                    break;
                                }else {
                                    is_address=false;
                                }
                            }
                            break;
                        }else {
                            is_address=false;
                        }
                    }
                    if(!is_address){
                        retInfo.setMark("1");
                        retInfo.setTip("第"+rowNum+"单收件人地址不正确.");
                        break;
                    }
                    if(getXCValue(weight)==null||"".equals(getXCValue(weight).trim())){
                        map.put("exp_ord_clt_height",1);
                    }else {
                        map.put("exp_ord_clt_height",getXCValue(weight));
                    }
                    map.put("exp_ord_category", getXCValue(exp_ord_category));
                    mapList.add(map);
                }
            }
        }
        retInfo.setObj(mapList);
        return retInfo;
    }

}
