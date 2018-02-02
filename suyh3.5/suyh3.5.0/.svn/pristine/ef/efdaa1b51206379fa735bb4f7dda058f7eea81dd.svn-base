package cn.hotol.wechat.repository.testapi;

import cn.hotol.wechat.domain.model.testapi.TestAPI;
import cn.hotol.wechat.toolutil.modelutil.Result;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lost on ⊙﹏⊙2014/6/24 ⊙︿⊙13:51.
 */
@Repository
public class TestAPIRepository {

    /*<?xml version="1.0" encoding="utf-8"?>
    <root>
        <apicase>
            <apiname>000</apiname>
            <project>000</project>
            <postmode>000</postmode>
            <url>000</url>
            <urlplus>
                <item name="000">000</item>
            </urlplus>
            <dataformate>
                <item name="000">000</item>
                <item name="000">000</item>
                <item name="000">000</item>
                <item name="000">000</item>
            </dataformate>
        </apicase>
    </root>*/

    public String addAPI(TestAPI testAPI, String path) {
        File myAPI = new File(path);
        if (!myAPI.exists()){
            return null;
        }
        SAXReader sr = new SAXReader();
        String cid = new Date().getTime() + "";
        try {
            Document doc  =  sr.read(myAPI);

            Element root = doc.getRootElement();

            Element apiCase = root.addElement("apicase");

            apiCase = apiCase.addAttribute("cid", cid);

            Element apiName = apiCase.addElement("apiname");

            apiName.setText(testAPI.getApiName());

            Element progectName = apiCase.addElement("project");

            progectName.setText(testAPI.getProjectName());

            Element postHeader = apiCase.addElement("postheader");

            postHeader.setText(testAPI.getPostHeader());

            Element postMode = apiCase.addElement("postmode");

            postMode.setText(testAPI.getPostMode());

            Element url = apiCase.addElement("url");

            url.setText(testAPI.getUrl());

            Element postData = apiCase.addElement("postdata");

            postData.setText(testAPI.getPostData());

            Element remark = apiCase.addElement("remark");

            remark.setText(testAPI.getRemark());

            XMLWriter xw = new XMLWriter(new OutputStreamWriter(new FileOutputStream(myAPI),"UTF-8"));

            xw.write(doc);

            xw.flush();

            xw.close();

        } catch (DocumentException e) {

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            System.out.println("Over");

        }

        return cid;
    }

    public Result<List<TestAPI>> readAPI(String path){
/*
        File myAPI = new File(path);
        if(!myAPI.exists()){
            return null;
        }
*/
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("API.xml");
        SAXReader sr = new SAXReader();
        try {
            Document doc  =  sr.read(is);
            List<TestAPI> apiList = new ArrayList<TestAPI>();
            List apis = doc.getRootElement().selectNodes("//apicase");
            for(Object node : apis){
                Node n = (Node)node;
                String projectName = n.selectSingleNode("./project").getText();
                if(projectName != null && (projectName.equals("portal") || projectName.equals("both"))) {
                    TestAPI testAPI = new TestAPI();
                    testAPI.setCid(((Attribute) n.selectSingleNode("@cid")).getValue());
                    testAPI.setApiName(n.selectSingleNode("./apiname").getText());
                    testAPI.setProjectName(n.selectSingleNode("./project").getText());
                    testAPI.setPostMode(n.selectSingleNode("./postmode").getText());
                    testAPI.setUrl(n.selectSingleNode("./url").getText());
                    testAPI.setPostData(n.selectSingleNode("./postdata").getText());
                    testAPI.setPostHeader(n.selectSingleNode("./postheader").getText());
                    testAPI.setRemark(n.selectSingleNode("./remark").getText());

                    apiList.add(testAPI);
                }
            }

            return Result.success(apiList);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return Result.failure("失败！");
    }

    public void delAPI(String cid,String path){
        File myAPI = new File(path);
        if(!myAPI.exists()){
            return;
        }
        SAXReader sr = new SAXReader();
        try {
            Document doc = sr.read(myAPI);
            ArrayList<TestAPI> apiList = new ArrayList<TestAPI>();
            Element root = doc.getRootElement();
            root.remove(root.selectSingleNode("./apicase[@cid = '"+cid+"']"));

            XMLWriter xw = new XMLWriter(new OutputStreamWriter(new FileOutputStream(myAPI),"UTF-8"));

            xw.write(doc);

            xw.flush();

            xw.close();
        } catch (DocumentException e) {

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            System.out.println("Over");

        }

    }

    public String editAPI(TestAPI testAPI , String path){
        File myAPI = new File(path);
        if(!myAPI.exists()){
            return null;
        }
        SAXReader sr = new SAXReader();
        String cid = testAPI.getCid();
        try {
            Document doc = sr.read(myAPI);
            Element root = doc.getRootElement();
            Node apiCase = root.selectSingleNode("./apicase[@cid = '" + cid + "']");
            String apiName = testAPI.getApiName();
            if(apiName !=null && !apiName.equals("")){
                apiCase.selectSingleNode("./apiname").setText(apiName);
            }
            apiCase.selectSingleNode("./postmode").setText(testAPI.getPostMode());
            apiCase.selectSingleNode("./url").setText(testAPI.getUrl());
            apiCase.selectSingleNode("./postdata").setText(testAPI.getPostData());
            apiCase.selectSingleNode("./postheader").setText(testAPI.getPostHeader());
            apiCase.selectSingleNode("./remark").setText(testAPI.getRemark());

            XMLWriter xw = new XMLWriter(new OutputStreamWriter(new FileOutputStream(myAPI),"UTF-8"));

            xw.write(doc);

            xw.flush();

            xw.close();
        } catch (DocumentException e) {

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            System.out.println("Over");

        }

        return cid;

    }

}
