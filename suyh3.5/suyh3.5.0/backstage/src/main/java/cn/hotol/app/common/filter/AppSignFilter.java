package cn.hotol.app.common.filter;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.util.Ip;
import cn.hotol.app.common.util.MD5;
import cn.hotol.app.common.util.MemcachedUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.*;


/**
 * 执行token过滤器
 */
public class AppSignFilter implements Filter {
	private static Logger logger = Logger.getLogger(AppSignFilter.class);

	public void destroy() {
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
			String logInfo = SignFilter.class + ":doFilter:";
			// 类路径+方法名
			logger.info("======" + logInfo + "begin======");
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
		    //判断参数是否合法
			if (!ahCheckInjectPass(request, response)) {
				errorResponse(response ,"{\"tip\":" + "\"输入项中不能包含非法字符。\""	+ ",\"mark\":\"202}" );
				return;
			}

			String pathInfo = request.getPathInfo() == null ? "" : request.getPathInfo();
			String url = request.getServletPath() + pathInfo;

		    if(url.indexOf("/app/3") > 0){
				//判断链接版本号
				String version = request.getHeader("version");
				try {
					int versionInt = Integer.valueOf(version);
					if (versionInt >= 3) {
						errorResponse(response ,"{\"tip\":" + "\"版本号有误\""	+ ",\"mark\":\"400\"}");
						return;
					}
				} catch (Exception e) {
					String tip = "{\"tip\":" + "\"版本号有误\""	+ ",\"mark\":\"400\"}";
					errorResponse(response ,tip);
					return;
				}
			}


		    //判断客户端类型是否有误
		    String client_type = request.getHeader("client_type");
		    if (!("3".equals(client_type) ||"2".equals(client_type) || "1".equals(client_type))) {
		    	String tip = "{\"tip\":" + "\"客户端类型有误\""	+ ",\"mark\":\"401\"}";
				errorResponse(response ,tip);
				return;
			}

		   //判断是否恶意刷屏
            Map<String, Integer> map = new HashMap<>();
		    if(Ip.getIpAddr(request)!=null&&!"".equals(Ip.getIpAddr(request))){
				if (MemcachedUtils.get(Ip.getIpAddr(request))!=null) {
					map = (Map<String, Integer>) MemcachedUtils.get(Ip.getIpAddr(request));
					int size = map.get("size")+1;
					if (size >= 1000000) {
						String tip = "{\"tip\":" + "\"请求过于频繁\""	+ ",\"mark\":\"600\"}";
						errorResponse(response ,tip);
						return;
					}else {
						map.put("size", size);
						MemcachedUtils.replace(Ip.getIpAddr(request), map, new Date(2 * 60 * 60 * 1000));
					}
				}else{
					map.put("size", 1);
					MemcachedUtils.add(Ip.getIpAddr(request), map, new Date(2 * 60 * 60 * 1000));
				}
			}else{
				String tip = "{\"tip\":" + "\"请求有误\""	+ ",\"mark\":\"600\"}";
				errorResponse(response ,tip);
				return;
			}


            //判断时间戳 签名是否合法
			if(url.indexOf(".") == -1){
				String Key = "hotol";
				String Timestamp = request.getHeader("Timestamp");
				String SignInfo = request.getHeader("SignInfo");
				String code = Timestamp + Key;
				//判断时间戳和加密信息是否为空
				if (Timestamp != null && SignInfo != null) {
					Long s = (System.currentTimeMillis() - Long.valueOf(Timestamp))/60000;
					//判断时间戳是否过期  时长5分钟
					if (-5 <= s && s <= 5) {
						//判断加密信息是否正确
						if (!SignInfo.equals(MD5.code(code))) {
							String tip = "{\"tip\":" + "\"签名已超时\""	+ ",\"mark\":\"500\"}";
							errorResponse(response ,tip);
							return;
						}else {
							//判断用户是否登陆
							if(url.indexOf("/token") > 0){
								String token = request.getHeader("token");
								//判断token是否为空
								if (token != null&&!"".equals(token)) {
									TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) MemcachedUtils.get(token);
									if (tdHtAdminDto == null) {
										String tip = "{\"tip\":" + "\"token无效\""	+ ",\"mark\":\"100\"}";
										errorResponse(response ,tip);
										return;
									} else {
										MemcachedUtils.replace(tdHtAdminDto.getToken() ,tdHtAdminDto ,new Date(20*24*60*60*1000));
									}
								}else{
									String tip = "{\"tip\":" + "\"未登陆\""	+ ",\"mark\":\"101\"}";
									errorResponse(response ,tip);
									return;
								}
							}
						}
					}else{
						String tip = "{\"tip\":" + "\"签名无效\""	+ ",\"mark\":\"501\",\"obj\":" + "null\"}";
						errorResponse(response ,tip);
						return;
					}	
				}else{
					String tip = "{\"tip\":" + "\"签名参数有误\"" + ",\"mark\":\"502\",\"obj\":" + "null\"}";
					errorResponse(response ,tip);
					return;
				}
				logger.info("token filter >>>> success  ");
			}

			chain.doFilter(req, res);

		logger.info("======" + logInfo + "end======");
	}
	protected boolean ahCheckInjectPass(HttpServletRequest req, HttpServletResponse response) throws IOException {
		//1、POST模式非法字符定义
		//双斜杠加上后，会影响被动登录
		String inj_str = "(function(,script,iframe,insert,delete,update,declare,expression,XSS,alert,scanner,onerror,prompt,atestu,object,and ,having";

		//2、GET模式非法字符定义
		//双斜杠编码后为 %0a,故需要加上
		//此时为GET提交，否则为POST提交(POST提交，营销业务中存在参数中含HTML标签)
		if (req.getQueryString() != null) {
			//inj_str = "(function(,script,iframe,insert,delete,update,declare,expression,XSS,alert,scanner,onerror,prompt,atestu,object,and ,having,eval,{,},../,./,<!--,-->";
			inj_str = "(function(,script,iframe,insert,delete,update,declare,expression,XSS,alert,scanner,onerror,prompt,atestu,object,and ,having,eval,../,./,<!--,-->";
		}

		//get方式传递的参数判断
		String loginFrom = req.getRequestURL().toString();
		//转码
		loginFrom = URLDecoder.decode(loginFrom, "UTF-8");
		
		//需要对	URL中的参数进行转码
		String urlQuery = req.getQueryString() == null ? "" : URLDecoder.decode(req.getQueryString(), "UTF-8");
//		if (urlQuery.contains("SAMLart")) {//SSO放过
//			return true;
//		}

		String inj_stra[] = inj_str.split(",");
		for (int i = 0; i < inj_stra.length; i++) {
			if (urlQuery.toLowerCase().indexOf(inj_stra[i]) >= 0) {
				logger.info("校验不通过,原因,包含非法字符:" + inj_stra[i]);
				return false;
			}
		}

		//post 表单 和 ajax提交 参数处理
		Map map = req.getParameterMap();
		Set set = map.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String temp = (String) it.next();
			String value = "";
			if (map.get(temp) != null) {
				String[] values = (String[]) map.get(temp);
				value = values[0];
			}

			for (int i = 0; i < inj_stra.length; i++) {
				if (value.toLowerCase().indexOf(inj_stra[i]) >= 0) {
					logger.info("校验不通过,原因,包含非法字符:" + inj_stra[i]);
					return false;
				}
			}
		}
        //json 参数处理
		/*byte[] bytes = new byte[1024 * 1024];
		InputStream is = req.getInputStream();

		int nRead = 1;
		int nTotalRead = 0;
		while (nRead > 0) {
			nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
			if (nRead > 0)
				nTotalRead = nTotalRead + nRead;
		}
		String str = new String(bytes, 0, nTotalRead, "utf-8");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				logger.info("校验不通过,原因,包含非法字符:" + inj_stra[i]);
				return false;
			}
		}*/
		return true;
	}

	private void errorResponse(HttpServletResponse response,String retStr) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println(retStr);
		out.flush();
		out.close();
	}
	public void init(FilterConfig arg0) throws ServletException {

	}

}
