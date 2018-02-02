package cn.hotol.app.service.three.channel;

import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.MD5;
import cn.hotol.app.repository.TdHtMemberRepository;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017-03-16.
 */

@Service
public class ChannelServiceImpl implements ChannelService {

    private static Logger logger = Logger.getLogger(ChannelServiceImpl.class);

    @Resource
    private TdHtMemberRepository tdHtMemberRepository;

    /**
     * @param params
     * @return Map<String, String>
     * @Purpose 花喵渠道接口
     * @version 3.0
     * @author lubin
     */
    @Override
    public Map<String, String> channelHuamiao(Map<String, String> params) {
        String logInfo = this.getClass().getName() + ":channelHuamiao:";
        logger.info("======" + logInfo + "begin======");
        Map<String, String> map=new HashMap<>();
        try {
            String body=params.get("body");
            String sign=params.get("sign");
            String bodyStr=DecodeHex3Des(body,"fa9783475f9c4aa9a3538fe5");
            JSONObject jsonObject=JSONObject.fromObject(bodyStr);
            String merchantId=jsonObject.get("merchantId").toString();
            String randomStr=jsonObject.get("randomStr").toString();
            String username=jsonObject.get("username").toString();
            String timestamp=jsonObject.get("timestamp").toString();
            Long s = (System.currentTimeMillis() - Long.valueOf(timestamp)) / 60000;
            if(s < -3 && s > 3){
                map.put("code", "12");
                map.put("msg", "超时请求.");
            }else {
                if(!Constant.HUA_MIAO_CHANNEL_ID.equals(merchantId)){
                    map.put("code", "13");
                    map.put("msg", "merchantId不正确.");
                }else {
                    String md5Str=merchantId+randomStr+username+timestamp;
                    if(!MD5.code(md5Str).equals(sign)){
                        map.put("code", "15");
                        map.put("msg", "签名不正确.");
                    }else {
                        TdHtMemberDto tdHtMemberDto=tdHtMemberRepository.findMemberByMembPhone(username);
                        if(tdHtMemberDto==null){
                            map.put("code", "16");
                            map.put("msg", "该用户还没有注册.");
                        }else {
                            if(Long.valueOf(timestamp)-tdHtMemberDto.getMemb_register_time().getTime()>24*60*60*1000){
                                map.put("code", "17");
                                map.put("msg", "该用户不是1天内注册的用户.");
                            }else {
                                if(tdHtMemberDto.getMemb_register_client()==1){//安卓端注册
                                    if(!Constant.HUA_MIAO_CHANNEL_ID.equals(String.valueOf(tdHtMemberDto.getChannel_id()))){
                                        map.put("code", "14");
                                        map.put("msg", "该用户不是花喵下载注册.");
                                    }else {
                                        Map<String, String> successMap=new HashMap<String, String>();
                                        successMap.put("code","1");
                                        String nonceStr= UUID.randomUUID().toString().replace("-","").substring(0,8);
                                        successMap.put("randomStr",nonceStr);
                                        String timestampStr=String.valueOf(System.currentTimeMillis());
                                        successMap.put("timestamp",timestampStr);
                                        String jsonStr=JSONObject.fromObject(successMap).toString();
                                        String successBody=EncryptHex3Des(jsonStr,"fa9783475f9c4aa9a3538fe5");
                                        String successSign=MD5.code("1"+nonceStr+timestampStr);
                                        map.put("body",successBody);
                                        map.put("sign",successSign);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            map.put("code", "11");
            map.put("msg", "数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return map;
    }

    /**
     * @param text    待解码字符串
     * @param desKey  解码密钥
     * @return String
     * @Purpose Hex3Des解码
     * @version 3.0
     * @author lubin
     */
    public String DecodeHex3Des(String text,String desKey){
        String logInfo = this.getClass().getName() + ":DecodeHex3Des:";
        logger.info("======" + logInfo + "begin======");
        String str="";
        try {
            SecretKey secretKey=new SecretKeySpec(desKey.getBytes(),"DESede");
            Cipher cipher=Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            byte[] encryptedData=cipher.doFinal(Hex.decodeHex(text.toCharArray()));
            str=new String(encryptedData);
        }catch (Exception e){
            str="";
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return str;
    }

    /**
     * @param text    待加密字符串
     * @param desKey  加密密钥
     * @return String
     * @Purpose Hex3Des加密
     * @version 3.0
     * @author lubin
     */
    public String EncryptHex3Des(String text,String desKey){
        String logInfo = this.getClass().getName() + ":DecodeHex3Des:";
        logger.info("======" + logInfo + "begin======");
        String str="";
        try {
            SecretKey secretKey=new SecretKeySpec(desKey.getBytes(),"DESede");
            Cipher cipher=Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] encryptedData=cipher.doFinal(text.getBytes());
           str=Hex.encodeHexString(encryptedData);
        }catch (Exception e){
            str="";
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return str;
    }

}
