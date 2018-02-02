package cn.hotol.app.common.util;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

public class MemcachedUtils {
	private static final Logger logger = Logger.getLogger(MemcachedUtils.class);
	private static MemCachedClient cachedClient;

	static {
		if (cachedClient == null) {
			ApplicationContext ctx = new FileSystemXmlApplicationContext(
					new String[] { "classpath:/spring/applicationContext.xml" });

			SockIOPool s = (SockIOPool) ctx.getBean("memcachedPool");

			cachedClient = (MemCachedClient) ctx.getBean("memcachedClient");

		}

	}

	private MemcachedUtils() {
	}

	/**
	 * @Purpose  仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对。
	 * @version  1.0
	 * @author   lizhun
	 * @param    key
	 * @param    value
	 * @param    expire  New Date(1000*10)：十秒后过期
	 * @return   boolean
	 */
	public static boolean add(String key, Object value, Date expire) {
		boolean flag = false;
		try {
			flag = cachedClient.add(key, value, expire);
		} catch (Exception e) {
			// 记录Memcached日志
			MemcachedLog.writeLog("Memcached add方法报错，key值：" + key + "\r\n" + exceptionWrite(e));
		}
		return flag;
	}




	/**
	 * @Purpose  仅当键已经存在时，replace 命令才会替换缓存中的键。
	 * @version  1.0
	 * @author   lizhun
	 * @param    key
	 * @param    value
	 * @param    expire  New Date(1000*10)：十秒后过期
	 * @return   boolean
	 */
	public static boolean replace(String key, Object value, Date expire) {
		boolean flag = false;
		try {
			flag = cachedClient.replace(key, value, expire);
		} catch (Exception e) {
			MemcachedLog.writeLog("Memcached replace方法报错，key值：" + key + "\r\n" + exceptionWrite(e));
		}
		return flag;
	}

	/**
	 * @Purpose  命令用于检索与之前添加的键值对相关的值。
	 * @version  1.0
	 * @author   lizhun
	 * @param    key
	 * @return   boolean
	 */
	public static Object get(String key) {
		Object obj = null;
		try {
			obj = cachedClient.get(key);
		} catch (Exception e) {
			MemcachedLog.writeLog("Memcached get方法报错，key值：" + key + "\r\n" + exceptionWrite(e));
			return null;
		}
		return obj;
	}


	/**
	 * @Purpose  删除 memcached 中的任何现有值。
	 * @version  1.0
	 * @author   lizhun
	 * @param    key
	 * @return   boolean
	 */
	public static boolean delete(String key) {
		boolean flag = false;
		try {
			flag = cachedClient.delete(key, null);
		} catch (Exception e) {
			MemcachedLog.writeLog("Memcached delete方法报错，key值：" + key + "\r\n" + exceptionWrite(e));
		}
		return flag;
	}


	/**
	 * @Purpose  清理缓存中的所有键/值对
	 * @version  1.0
	 * @author   lizhun
	 * @return   boolean
	 */
	public static boolean flashAll() {
		boolean flag = false;
		try {
			flag = cachedClient.flushAll();
		} catch (Exception e) {
			MemcachedLog.writeLog("Memcached flashAll方法报错\r\n" + exceptionWrite(e));
		}
		return flag;
	}

	/**
	 * 返回异常栈信息，String类型
	 *
	 * @param e
	 * @return
	 */
	private static String exceptionWrite(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}

	/**
	 *
	 * @ClassName: MemcachedLog
	 * @Description: Memcached日志记录
	 * @author yinjw
	 * @date 2014-6-18 下午5:01:37
	 *
	 */
	private static class MemcachedLog {
		private final static String MEMCACHED_LOG = "D:\\memcached.log";
		private final static String LINUX_MEMCACHED_LOG = "/usr/local/logs/memcached.log";
		private static FileWriter fileWriter;
		private static BufferedWriter logWrite;
		// 获取PID，可以找到对应的JVM进程
		private final static RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		private final static String PID = runtime.getName();

		/**
		 * 初始化写入流
		 */
		static {
			try {
				String osName = System.getProperty("os.name");
				if (osName.indexOf("Windows") == -1) {
					fileWriter = new FileWriter(MEMCACHED_LOG, true);
				} else {
					fileWriter = new FileWriter(LINUX_MEMCACHED_LOG, true);
				}
				logWrite = new BufferedWriter(fileWriter);
			} catch (IOException e) {
				logger.error("memcached 日志初始化失败", e);
				closeLogStream();
			}
		}

		/**
		 * 写入日志信息
		 *
		 * @param content
		 *            日志内容
		 */
		public static void writeLog(String content) {
			try {
				logWrite.write("[" + PID + "] " + "- ["
						+ DateFormatUtils.format(new Date().getTime(), "yyyy-MM-dd hh:mm") + "]\r\n" + content);
				logWrite.newLine();
				logWrite.flush();
			} catch (IOException e) {
				logger.error("memcached 写入日志信息失败", e);
			}
		}

		/**
		 * 关闭流
		 */
		private static void closeLogStream() {
			try {
				fileWriter.close();
				logWrite.close();
			} catch (IOException e) {
				logger.error("memcached 日志对象关闭失败", e);
			}
		}
	}
}