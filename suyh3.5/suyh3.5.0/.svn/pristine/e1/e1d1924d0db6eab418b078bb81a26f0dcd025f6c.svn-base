package cn.hotol.app.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by LuBin
 * Date 2017-01-07.
 */
public class HttpUtil {

    /**
     * 发送POST 请求
     * @param url 请求地址
     * @param charset 编码格式
     * @param params 请求参数
     * @return 响应
     * @throws IOException
     */
    public static String post(String url, String charset, Map params) throws IOException {
        HttpURLConnection conn = null;
        OutputStreamWriter out = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer();
        try {
            conn = (HttpURLConnection)new URL(url).openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            out = new OutputStreamWriter(conn.getOutputStream(), charset);
            out.write(buildQuery(params, charset));
            out.flush();
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String tempLine = null;
            while ((tempLine = reader.readLine()) != null) {
                result.append(tempLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return result.toString();
    }

    /**
     * 将map转换为请求字符串
     * <p>data=xxx&msg_type=xxx</p>
     * @param params
     * @param charset
     * @return
     * @throws IOException
     */
    public static String buildQuery(Map<String, Object> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuffer data = new StringBuffer();
        boolean flag = false;

        for (Entry<String, Object> entry : params.entrySet()) {
            if (flag) {
                data.append("&");
            } else {
                flag = true;
            }
            data.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), charset));
        }

        return data.toString();

    }

    public static void main(String[] args) throws Exception {
        Map map = new HashMap();
        //{"billCodes":["","888888888888"]}
        String expressNo="419283992239";
        List list=new ArrayList();
        list.add(expressNo);
        String data = JSONArray.fromObject(list).toString();
        map.put("data", data);
        map.put("msg_type", "NEW_TRACES");
        map.put("data_digest", DigestUtil.digest(data, "C941941B726FB0F7C2293CD4E6B75AD1", DigestUtil.UTF8));
        map.put("company_id", "0a99f10855cb4070bcdf0e00d21e9082");

        try {
            String result=post("http://japi.zto.cn/zto/api_utf8/traceInterface", "UTF-8", map);
            JSONObject resultJson=JSONObject.fromObject(result);
            JSONArray dataArray=JSONArray.fromObject(resultJson.get("data"));
            JSONObject jsonObject=JSONObject.fromObject(dataArray.get(0));
            JSONArray tracesArray=JSONArray.fromObject(jsonObject.get("traces"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
