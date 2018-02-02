package cn.hotol.app.common.controller;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.init.*;
import cn.hotol.app.common.util.UploadFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Controller
public class FileController {



    /**
     *上传banner图片
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/banner/fileImg" )
    @ResponseBody
    public Map<String, String> fileImg(HttpServletRequest request)throws Exception{
        Map<String, String>  map = new HashMap<>();
        Date currTime = new Date();
        boolean flag ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssS", Locale.CHINA);
        String fileName = new String((formatter.format(currTime)).getBytes("UTF-8"));
        File savePath = new File(Constant.FILE_IMG+"slash"+"banner");
        flag = UploadFileUtil.submitForm(request, savePath.toString(), fileName, ".jpg,.png,.JPG,.PNG", 4096000L,null);
        if(flag){
            String imgName = request.getAttribute("upload.filename").toString();
            map.put("mark", "0");
            map.put("tip", Constant.DOMAIN +  Constant.FILE_IMG+"/"+"banner"+"/"+imgName);
            map.put("obj", imgName);
        }else{
            map.put("mark", "1");
            map.put("mark", "上传失败");
        }
        return map;
    }
    /**
     *上传banner图片
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/dict/fileImg" )
    @ResponseBody
    public Map<String, String> dict(HttpServletRequest request)throws Exception{
        Map<String, String>  map = new HashMap<>();
        Date currTime = new Date();
        boolean flag ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssS", Locale.CHINA);
        String fileName = new String((formatter.format(currTime)).getBytes("UTF-8"));
        File savePath = new File(Constant.FILE_IMG+"slash"+"dict");
        flag = UploadFileUtil.submitForm(request, savePath.toString(), fileName, ".jpg,.png,.JPG,.PNG", 4096000L,null);
        if(flag){
            String imgName = request.getAttribute("upload.filename").toString();
            map.put("mark", "0");
            map.put("tip", Constant.DOMAIN +  Constant.FILE_IMG+"/"+"dict"+"/"+imgName);
            map.put("obj", imgName);
        }else{
            map.put("mark", "1");
            map.put("mark", "上传失败");
        }
        return map;
    }

    /**
     * @Purpose  清除缓存
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value = "/admin/clear/cache" )
    @ResponseBody
    public RetInfo cache(HttpServletRequest request)throws Exception{
        RetInfo retInfo = new RetInfo();
        retInfo.setMark("0");
        BannerInit.flush();
        LocationInit.flush();
        DictionaryInit.flush();
        ArticleInit.flush();
        ExpRegionPriceInit.flush();
        GoodsInit.flush();
        TaskInit.flush();
        ThirdPartyInit.flush();
        ExpressSdkInit.flush();
        DivideInit.flush();
        DataConfigInit.flush();
        AgentWorkTimeInit.flush();
        return retInfo;
    }

    /**
     * @Purpose   上传积分商品图标
     * @version   3.0
     * @author    lubin
     * @time      2017-03-25
     * @param     request 上传的图片
     * @return    RetInfo  返回的结果
     */
    @RequestMapping(value = "/admin/score/goods/fileGoodsImg" )
    @ResponseBody
    public Map<String, String> fileGoodsImg(HttpServletRequest request)throws Exception{
        Map<String, String>  map = new HashMap<>();
        Date currTime = new Date();
        boolean flag ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssS", Locale.CHINA);
        String fileName = new String((formatter.format(currTime)).getBytes("UTF-8"));
        File savePath = new File(Constant.FILE_IMG+"slash"+"score"+"slash"+"goodsIcon");
        flag = UploadFileUtil.submitForm(request, savePath.toString(), fileName, ".jpg,.png,.JPG,.PNG", 2048000L,null);
        if(flag){
            String imgName = request.getAttribute("upload.filename").toString();
            map.put("mark", "0");
            map.put("tip", Constant.DOMAIN +  Constant.FILE_IMG+"/"+"score"+"/"+"goodsIcon"+"/"+imgName);
            map.put("obj", imgName);
        }else{
            map.put("mark", "1");
            map.put("mark", "上传失败");
        }
        return map;
    }

    /**
     * @Purpose   上传积分商品图片
     * @version   3.0
     * @author    lubin
     * @time      2017-03-25
     * @param     request 上传的图片
     * @return    RetInfo  返回的结果
     */
    @RequestMapping(value = "/admin/score/goods/fileGoodsDetailImg" )
    @ResponseBody
    public Map<String, String> fileGoodsDetailImg(HttpServletRequest request)throws Exception{
        Map<String, String>  map = new HashMap<>();
        Date currTime = new Date();
        boolean flag ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssS", Locale.CHINA);
        String fileName = new String((formatter.format(currTime)).getBytes("UTF-8"));
        File savePath = new File(Constant.FILE_IMG+"slash"+"score"+"slash"+"goodsDetail");
        flag = UploadFileUtil.submitForm(request, savePath.toString(), fileName, ".jpg,.png,.JPG,.PNG", 4096000L,null);
        if(flag){
            String imgName = request.getAttribute("upload.filename").toString();
            map.put("mark", "0");
            map.put("tip", Constant.DOMAIN +  Constant.FILE_IMG+"/"+"score"+"/"+"goodsDetail"+"/"+imgName);
            map.put("obj", imgName);
        }else{
            map.put("mark", "1");
            map.put("mark", "上传失败");
        }
        return map;
    }
}
