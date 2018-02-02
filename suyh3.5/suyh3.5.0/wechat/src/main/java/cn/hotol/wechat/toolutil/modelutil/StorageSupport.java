package cn.hotol.wechat.toolutil.modelutil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lost on 14-6-5.
 */
public class StorageSupport {

    private String systemModel;

    private String storageRoot;

    public void setSystemModel(String systemModel) {
        this.systemModel = systemModel;
    }

    public void setStorageRoot(String storageRoot) {
        this.storageRoot = storageRoot;
    }

    public String getUploadRoot(ServletContext servletContext){

        if(systemModel.equals("test") || systemModel.equals("demo") || systemModel.equals("product") || systemModel.equals("ali")){
            return storageRoot;
        }else{
            return servletContext.getRealPath("/storage");
        }
    }

    public String getApiPath(){
        if(systemModel.equals("test") || systemModel.equals("demo")){
            return "/storage/w13_test/api/API.xml";
        }else{
            return this.getClass().getClassLoader().getResource("API.xml").getPath();
        }
    }

    public String getEffectiveServerUrl(HttpServletRequest request){
        if(systemModel.equals("ali")){
            return "hotol.cn";
        }else{
            return request.getServerName()+":"+request.getServerPort();
        }
    }
}
