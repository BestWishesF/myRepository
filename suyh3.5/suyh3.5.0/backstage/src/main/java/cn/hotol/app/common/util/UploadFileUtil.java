package cn.hotol.app.common.util;

import cn.hotol.app.common.oss.OssUtil;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/21.
 */
public class UploadFileUtil {
    private static Logger logger = Logger.getLogger(UploadFileUtil.class);

    /**
     * 允许上传文件的单个大小限制
     */
    private long uploadSizeLimit;
    /**
     * 允许上传的文件类型列表
     */
    private String allowType;
    /**
     * 禁止上传的文件类型列表
     */
    private String banType;




    /**
     * 上传文件
     * 用于enctype="multipart/form-data"型的form表单提交
     * @param request	in	HttpServletRequest	请求
     * @param savePath	in	String	要保存到的文件路径
     * @param fileName	in	String	要保存为的文件名称
     * @param normalParas	out	Map<String,String>	表单中其他非上传类的传统参数Map。key：参数名（表单元素中的name属性值）；value：参数值
     * @param newFilename  out	StringBuffer	保存后的新文件名（当fileName不带后缀名的时候，根据上传文件的后缀名自动添加，并赋值此变量返回）
     * @return boolean	成功/失败
     */
    public  boolean upload(HttpServletRequest request, String savePath,
                           String fileName, Map<String,String> normalParas, StringBuffer newFilename) {
        String logInfo = this.getClass().getName() + ":upload:";
        logger.info("======" + logInfo + "begin======");
        boolean isSuccess = false;
        try {
            request.setCharacterEncoding("UTF-8");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    normalParas.put(item.getFieldName(), item.getString("UTF-8"));
                } else {
                    if (item.getName() != null && !item.getName().equals("")) {
                        if(allowType!=null){
                            if(allowType.indexOf(item.getContentType())==-1){
                                request.setAttribute("upload.message", "不允许的上传文件类型！");
                                logger.info("======" + logInfo + ":不允许的上传文件类型！======");
                                return false;
                            }
                        }
                        if(banType!=null){
                            if(banType.indexOf(item.getContentType())!=-1){
                                request.setAttribute("upload.message", "被禁止的上传文件类型！");
                                logger.info("======" + logInfo + ":被禁止的上传文件类型！======");
                                return false;
                            }
                        }
                        if(uploadSizeLimit>0){
                            if(item.getSize()>uploadSizeLimit){
                                request.setAttribute("upload.message", "文件大小超过限额！");
                                logger.info("======" + logInfo + ":文件大小超过限额！======");
                                return false;
                            }
                        }

                        if(fileName.indexOf(".")==-1){
                            String[] tmps=item.getName().split("\\.");
                            if(tmps.length>1){
                                String tail="."+tmps[tmps.length-1];
                                fileName=fileName+tail;
                                request.setAttribute("upload.filename", fileName);
                            }

                        }
                        newFilename.append(fileName);
                        // 上传文件的保存路径
//						File file = new File(savePath, fileName);
//						item.write(file);
                        savePath = savePath.replaceAll("slash","/");
                        ObjectMetadata objectMetadata = new ObjectMetadata();
                        objectMetadata.setContentLength(item.getSize());
                        OssUtil.uploadInputStream(item.getInputStream(), objectMetadata,savePath + "/"+ fileName);
                        request.setAttribute("upload.message", "上传文件成功！");
                        logger.info("======" + logInfo + ":上传文件成功！======");
                    } else {
                        request.setAttribute("upload.message", "没有选择上传文件！");
                        logger.info("======" + logInfo + ":没有选择上传文件！======");
                        normalParas.put("app_pic", "-1");//用于应用图片是否上传判断
//						return false;
                    }
                }
            }
            isSuccess = true;
        } catch (FileUploadException e) {
            request.setAttribute("upload.message", "上传文件失败！");
            isSuccess = false;
            logger.error("==========="+logInfo+":"+e.toString());
        } catch (Exception e) {
            request.setAttribute("upload.message", "上传文件失败！");
            isSuccess = false;
            logger.error("==========="+logInfo+":"+e.toString());
        }
        return isSuccess;
    }

    /**
     * 获取表单中的变量
     * @param request	in	请求
     * @param normalParaMap	out	Map<String,String> 普通类型参数容器，key：参数名（表单元素中的name属性值）；value：参数值
     * @param uploadParaMap	out	Map<String,FileItem>	上传类参数容器，key：参数名（表单元素中的name属性值）；value：上传文件对象
     */
    public void getParas(HttpServletRequest request, String charset, Map<String,String> normalParaMap, Map<String,FileItem> uploadParaMap){
        try {
            if(charset==null || "".equals(charset)){
                request.setCharacterEncoding("UTF-8");
            }else{
                request.setCharacterEncoding(charset);
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    normalParaMap.put(item.getFieldName(), item.getString("UTF-8"));
                } else {
                    uploadParaMap.put(item.getFieldName(), item);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 设置文件大小限制
     * 单位为byte
     * @param uploadSizeLimit
     */
    public void setUploadSizeLimit(long uploadSizeLimit) {
        this.uploadSizeLimit = uploadSizeLimit;
    }



    /**
     * 设置允许的文件类型，与setBanType互斥，使用此方法setBanType将被赋值为空
     * 示例：setAllowType(".gif,.txt,.doc,.css");
     * @param allowType 允许的文件类型
     */
    public void setAllowType(String allowType) {
        this.allowType = formateType(allowType);
        this.banType=null;
    }




    /**
     * 设置禁止的文件类型，与setAllowType互斥，使用此方法setAllowType将被赋值为空
     * 示例：setBanType(".gif,.txt,.doc,.css");
     * @param banType 禁止的文件类型
     */
    public void setBanType(String banType) {
        this.banType = formateType(banType);
        this.allowType=null;
    }

    /**
     * 格式化允许/禁止文件类型串
     * @param oType
     * @return
     */
    private String formateType(String oType){
        String nType="";
        if(oType.indexOf(".3gpp")!=-1){
            nType="audio/3gpp,video/3gpp"+","+nType;
        }
        if(oType.indexOf(".ac3")!=-1){
            nType="audio/ac3"+","+nType;
        }
        if(oType.indexOf(".asf")!=-1){
            nType="allpication/vnd.ms-asf"+","+nType;
        }
        if(oType.indexOf(".au")!=-1){
            nType="audio/basic"+","+nType;
        }
        if(oType.indexOf(".css")!=-1){
            nType="text/css"+","+nType;
        }
        if(oType.indexOf(".csv")!=-1){
            nType="text/csv"+","+nType;
        }
        if(oType.indexOf(".doc")!=-1){
            nType="application/msword"+","+nType;
        }
        if(oType.indexOf(".dot")!=-1){
            nType="application/msword"+","+nType;
        }
        if(oType.indexOf(".dtd")!=-1){
            nType="application/xml-dtd"+","+nType;
        }
        if(oType.indexOf(".dwg")!=-1){
            nType="image/vnd.dwg"+","+nType;
        }
        if(oType.indexOf(".dxf")!=-1){
            nType="image/vnd.dxf"+","+nType;
        }
        if(oType.indexOf(".gif")!=-1){
            nType="image/gif"+","+nType;
        }
        if(oType.indexOf(".htm")!=-1){
            nType="text/html"+","+nType;
        }
        if(oType.indexOf(".html")!=-1){
            nType="text/html"+","+nType;
        }
        if(oType.indexOf(".jp2")!=-1){
            nType="image/jp2"+","+nType;
        }
        if(oType.indexOf(".jpe")!=-1){
            nType="image/jpeg"+","+nType;
        }
        if(oType.indexOf(".jpeg")!=-1){
            nType="image/jpeg,image/pjpeg"+","+nType;
        }
        if(oType.indexOf(".jpg")!=-1){
            nType="image/jpeg,image/pjpg"+","+nType;
        }
        if(oType.indexOf(".js")!=-1){
            nType="text/javascript,application/javascript"+","+nType;
        }
        if(oType.indexOf(".json")!=-1){
            nType="application/json"+","+nType;
        }
        if(oType.indexOf(".mp2")!=-1){
            nType="audio/mpeg,video/mpeg"+","+nType;
        }
        if(oType.indexOf(".mp3")!=-1){
            nType="audio/mpeg"+","+nType;
        }
        if(oType.indexOf(".mp4")!=-1){
            nType="audio/mp4,video/mp4"+","+nType;
        }
        if(oType.indexOf(".mpeg")!=-1){
            nType="video/mpeg"+","+nType;
        }
        if(oType.indexOf(".mpg")!=-1){
            nType="video/mpeg"+","+nType;
        }
        if(oType.indexOf(".mpp")!=-1){
            nType="application/vnd.ms-project"+","+nType;
        }
        if(oType.indexOf(".ogg")!=-1){
            nType="application/ogg,audio/ogg"+","+nType;
        }
        if(oType.indexOf(".pdf")!=-1){
            nType="application/pdf"+","+nType;
        }
        if(oType.indexOf(".png")!=-1){
            nType="image/png,image/x-png"+","+nType;
        }
        if(oType.indexOf(".pot")!=-1){
            nType="application/vnd.ms-powerpoint"+","+nType;
        }
        if(oType.indexOf(".pps")!=-1){
            nType="application/vnd.ms-powerpoint"+","+nType;
        }
        if(oType.indexOf(".ppt")!=-1){
            nType="application/vnd.ms-powerpoint"+","+nType;
        }
        if(oType.indexOf(".rtf")!=-1){
            nType="application/rtf,text/rtf"+","+nType;
        }
        if(oType.indexOf(".svf")!=-1){
            nType="image/vnd.svf"+","+nType;
        }
        if(oType.indexOf(".tif")!=-1){
            nType="image/tiff"+","+nType;
        }
        if(oType.indexOf(".tiff")!=-1){
            nType="image/tiff"+","+nType;
        }
        if(oType.indexOf(".txt")!=-1){
            nType="text/plain"+","+nType;
        }
        if(oType.indexOf(".wdb")!=-1){
            nType="application/vnd.ms-works"+","+nType;
        }
        if(oType.indexOf(".wps")!=-1){
            nType="application/vnd.ms-works"+","+nType;
        }
        if(oType.indexOf(".xhtml")!=-1){
            nType="application/xhtml+xml"+","+nType;
        }
        if(oType.indexOf(".xlc")!=-1){
            nType="application/vnd.ms-excel"+","+nType;
        }
        if(oType.indexOf(".xlm")!=-1){
            nType="application/vnd.ms-excel"+","+nType;
        }
        if(oType.indexOf(".xls")!=-1){
            nType="application/vnd.ms-excel"+","+nType;
        }
        if(oType.indexOf(".xlt")!=-1){
            nType="application/vnd.ms-excel"+","+nType;
        }
        if(oType.indexOf(".xlw")!=-1){
            nType="application/vnd.ms-excel"+","+nType;
        }
        if(oType.indexOf(".xml")!=-1){
            nType="text/xml,application/xml"+","+nType;
        }
        if(oType.indexOf(".zip")!=-1){
            nType="aplication/zip"+","+nType;
        }
        return nType;
    }



    /**
     附支持的文件类型：

     *.3gpp	audio/3gpp， video/3gpp	3GPP Audio/Video
     *.ac3	audio/ac3	AC3 Audio
     *.asf	allpication/vnd.ms-asf	Advanced Streaming Format
     *.au	audio/basic	AU Audio
     *.css	text/css	Cascading Style Sheets
     *.csv	text/csv	Comma Separated Values
     *.doc	application/msword	MS Word Document
     *.dot	application/msword	MS Word Template
     *.dtd	application/xml-dtd	Document Type Definition
     *.dwg	image/vnd.dwg	AutoCAD Drawing Database
     *.dxf	image/vnd.dxf	AutoCAD Drawing Interchange Format
     *.gif	image/gif	Graphic Interchange Format
     *.htm	text/html	HyperText Markup Language
     *.html	text/html	HyperText Markup Language
     *.jp2	image/jp2	JPEG-2000
     *.jpe	image/jpeg	JPEG
     *.jpeg	image/jpeg	JPEG
     *.jpg	image/jpeg	JPEG
     *.js	text/javascript， application/javascript	JavaScript
     *.json	application/json	JavaScript Object Notation
     *.mp2	audio/mpeg， video/mpeg	MPEG Audio/Video Stream， Layer II
     *.mp3	audio/mpeg	MPEG Audio Stream， Layer III
     *.mp4	audio/mp4， video/mp4	MPEG-4 Audio/Video
     *.mpeg	video/mpeg	MPEG Video Stream， Layer II
     *.mpg	video/mpeg	MPEG Video Stream， Layer II
     *.mpp	application/vnd.ms-project	MS Project Project
     *.ogg	application/ogg， audio/ogg	Ogg Vorbis
     *.pdf	application/pdf	Portable Document Format
     *.png	image/png	Portable Network Graphics
     *.pot	application/vnd.ms-powerpoint	MS PowerPoint Template
     *.pps	application/vnd.ms-powerpoint	MS PowerPoint Slideshow
     *.ppt	application/vnd.ms-powerpoint	MS PowerPoint Presentation
     *.rtf	application/rtf， text/rtf	Rich Text Format
     *.svf	image/vnd.svf	Simple Vector Format
     *.tif	image/tiff	Tagged Image Format File
     *.tiff	image/tiff	Tagged Image Format File
     *.txt	text/plain	Plain Text
     *.wdb	application/vnd.ms-works	MS Works Database
     *.wps	application/vnd.ms-works	Works Text Document
     *.xhtml	application/xhtml+xml	Extensible HyperText Markup Language
     *.xlc	application/vnd.ms-excel	MS Excel Chart
     *.xlm	application/vnd.ms-excel	MS Excel Macro
     *.xls	application/vnd.ms-excel	MS Excel Spreadsheet
     *.xlt	application/vnd.ms-excel	MS Excel Template
     *.xlw	application/vnd.ms-excel	MS Excel Workspace
     *.xml	text/xml， application/xml	Extensible Markup Language
     *.zip	aplication/zip	Compressed Archive
     */


    public static boolean submitForm(HttpServletRequest request, String savePath, String fileName,
                                     String allowType, long limitSize, Map<String, String> normalPMap){
        //创建文件夹
        File file=new File(savePath);
        if(!file.exists() && !file.isDirectory()){
            file.mkdirs();
        }
        UploadFileUtil fu = new UploadFileUtil();
        fu.setAllowType(allowType);
        fu.setUploadSizeLimit(limitSize);
        StringBuffer newFileName = new StringBuffer();
        boolean r = fu.upload(request, savePath, fileName, normalPMap, newFileName);
        return r;
    }
}



