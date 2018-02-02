package cn.hotol.app.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LuBin
 * Date 2016-12-16.
 */
public class MobileOrPhoneUtil {

    //手机号验证
    private static final Pattern mobile_verification=Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
    //普通电话验证
    private static final Pattern telephone_verification=Pattern.compile("^[1-9]{1}[0-9]{5,8}$");
    //400电话验证
    private static final Pattern four_phone_verification=Pattern.compile("^[4][0][0][0-9]{5,10}$");
    //带区号电话验证
    private static final Pattern area_phone_verification=Pattern.compile("^[0][0-9]{2,3}[0-9]{5,15}$");

    /**
     * @Purpose  验证是否为手机号
     * @version  1.0
     * @author   lubin
     * @param    mobile
     * @return   boolean
     */
    public static boolean getIsMobile(String mobile){
        mobile=dislodgeBeeline(mobile);
        Matcher matcher = mobile_verification.matcher(mobile);
        return matcher.matches();
    }

    /**
     * @Purpose  验证是否为电话号码
     * @version  1.0
     * @author   lubin
     * @param    phone
     * @return   boolean
     */
    public static boolean getIsPhone(String phone){
        Matcher matcher=null;
        boolean bool=false;
        phone=dislodgeBeeline(phone);
        if (phone.indexOf("400") == 0) {
            matcher=four_phone_verification.matcher(phone);
            bool=matcher.matches();
        }else{
            if (phone.length() >= 9) {
                matcher=area_phone_verification.matcher(phone);
                bool=matcher.matches();
            }else{
                matcher=telephone_verification.matcher(phone);
                bool=matcher.matches();
            }
        }
        return bool;
    }

    /**
     * @Purpose  去除号码中的“-”符号
     * @version  1.0
     * @author   lubin
     * @param    str
     * @return   String
     */
    public static String dislodgeBeeline(String str){
        if (str.indexOf("-") != -1) {
            String newStr = "";
            String[] strs = str.split("-");
            for (int i = 0; i < strs.length; i++) {
                newStr = newStr + strs[i];
            }
            str = newStr;
        }
        return str;
    }

}
