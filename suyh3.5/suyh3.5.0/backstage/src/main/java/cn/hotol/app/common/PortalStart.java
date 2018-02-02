package cn.hotol.app.common;

import cn.hotol.app.common.init.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PortalStart implements ServletContextListener {
	static Logger logger = Logger.getLogger(PortalStart.class);
	public void contextDestroyed(ServletContextEvent arg0) {}

	public void contextInitialized(ServletContextEvent arg0) {
		SpringInfo.init(arg0.getServletContext());
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
	}
}
