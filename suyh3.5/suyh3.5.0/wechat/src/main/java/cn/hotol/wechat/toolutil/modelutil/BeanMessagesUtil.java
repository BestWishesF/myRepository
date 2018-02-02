package cn.hotol.wechat.toolutil.modelutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * login: Hill Pan
 * Date: 2/14/12
 * Time: 10:49 AM
 */
public class BeanMessagesUtil {

    private static Logger logger = LoggerFactory.getLogger(BeanMessagesUtil.class);

    public static final String I18N_PACKAGE_ROOT = "i18n.";

    private static final Map<Class, ResourceBundle> beanMessagesCache = new HashMap<Class, ResourceBundle>();

    public static ResourceBundle getResourceBundle(Object object) {

        return getResourceBundle(object, Locale.US);
    }

    public static ResourceBundle getResourceBundle(Class clazz) {

        return getResourceBundle(clazz, Locale.US);
    }

    public static ResourceBundle getResourceBundle(Object object, Locale locale) {

        Class clazz = object.getClass();

        return getResourceBundle(clazz, locale);
    }

    public static ResourceBundle getResourceBundle(Class clazz, Locale locale) {

        ResourceBundle clazzMessages = beanMessagesCache.get(clazz);

        if (clazzMessages == null) {

            clazzMessages = loadResourceBundle(locale, clazz);

            beanMessagesCache.put(clazz, clazzMessages);
        }

        return clazzMessages;
    }

    private static ResourceBundle loadResourceBundle(Locale locale, Class clazz) {

        ResourceBundle clazzMessages;

        String baseName = getClassI18NBaseName(clazz);

        try {
            clazzMessages = PropertyResourceBundle.getBundle(baseName, locale);
        } catch (MissingResourceException mre) {
            logger.error("Can't find the resource with baseName : {}", baseName);
            clazzMessages = new NullResourceBundle(baseName);
        }

        return clazzMessages;
    }

    private static String getClassI18NBaseName(Class clazz) {
        return I18N_PACKAGE_ROOT + clazz.getName();
    }

    private static class NullResourceBundle extends ResourceBundle {

        private String baseName;

        private NullResourceBundle(String baseName) {
            this.baseName = baseName;
        }

        @Override
        protected Object handleGetObject(String key) {
            logger.warn("Can't get value from a NullResourceBundle(baseName : {}). The key will be as return value.", baseName);
            return key;
        }

        @Override
        public Enumeration<String> getKeys() {
            return new Enumeration<String>() {
                @Override
                public boolean hasMoreElements() {
                    return false;
                }

                @Override
                public String nextElement() {
                    throw new NoSuchElementException("NullResourceBundle Enumerator");
                }
            };
        }
    }
}
