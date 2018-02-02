package cn.hotol.app.common.filter;

import cn.hotol.app.common.Constant;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 执行token过滤器
 */
public class SignFilter implements Filter {
	private static Logger logger = Logger.getLogger(SignFilter.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = req.getSession();
		String pathInfo = req.getPathInfo() == null ? "" : req.getPathInfo();
		String url = req.getServletPath() + pathInfo;
		logger.info(">>>>>URLFILTER>>>"+url);
		// 应用侧自己检测
		if(session.getAttribute(Constant.USERLOGINSESSION) == null){
			response.setStatus(302);
			response.setHeader("location",req.getContextPath()+"/skip");
			return;
		}

		arg2.doFilter(arg0, arg1);
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
		byte[] bytes = new byte[1024 * 1024];
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
		}
		return true;
	}

	private void errorResponse(HttpServletResponse response,String retStr) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println(retStr);
		out.flush();
		out.close();
	}
	public void init(FilterConfig arg0) throws ServletException {

	}

}
