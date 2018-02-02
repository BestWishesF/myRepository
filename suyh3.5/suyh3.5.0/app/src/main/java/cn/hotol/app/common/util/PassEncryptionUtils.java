package cn.hotol.app.common.util;

import org.apache.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

/**
 * User: Lewisw
 * Date: 13-11-3
 * Time: 上午12:31
 */
public class PassEncryptionUtils {

    private static Logger logger = Logger.getLogger(PassEncryptionUtils.class);

    private static final byte[] DIGEST_RAW_KEY =
            {
                    -0x11, -0x22, 0x4F, -0x58, 0x28, 0x10, -0x40, 0x38,
                    0x21, 0x25, -0x71, 0x51, 0x7B, 0x2c, 0x15, 0x66,
                    0x77, -0x09, 0x72, -0x38, 0x3C, 0x44, 0x36, -0x12
            };

    private static final byte[] DEFAULT_AES_KEY_BYTES =
            new byte[]{
                    0x7f, -0x5e, -0x12, 0x18, -0x73, 0x2b, -0x75, 0x35,
                    0x2c, -0x0c, -0x4c, 0x53, 0x17, -0x2c, 0x2f, 0x5d,
                    0x1f, -0x08, 0x2c, -0x27, -0x31, -0x70, 0x3d, 0x27,
                    0x7a, -0x27, -0x6c, 0x4f, 0x44, 0x51, -0x67, 0x4c
            };


    public static String digest(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] passwordBytes = password.getBytes("UTF-8");

        byte[] dataBytes = new byte[passwordBytes.length + DIGEST_RAW_KEY.length];

        System.arraycopy(DIGEST_RAW_KEY, 0, dataBytes, 0, DIGEST_RAW_KEY.length);

        System.arraycopy(passwordBytes, 0, dataBytes, 0, passwordBytes.length);

        byte[] mdBytes = md.digest(dataBytes);

        return bytes2Hex(mdBytes);
    }

    public static byte[] sha256DigestLoop(byte[] inputBytes, int loopTimes) {

        byte[] buf = inputBytes;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            for (int i = 0; i < loopTimes; i++) {
                buf = md.digest(buf);
            }
            return buf;

        } catch (NoSuchAlgorithmException e) {
            logger.error("Failed to sha256DigestLoop", e);
            return null;
        }
    }

    private static String bytes2Hex(byte[] bts) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < bts.length; i++) {

            String tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }

        return sb.toString();
    }

    public static byte[] hex2Bytes(String hexStr) {

        if (hexStr == null) return null;

        int byteLen = hexStr.length() / 2;

        if (byteLen == 0) return null;

        byte[] b = new byte[byteLen];

        try {

            for (int i = 0; i < byteLen; i++) {
                b[i] = (byte) Integer.parseInt(hexStr.substring(2 * i, 2 * i + 2), 16);
//                b[i / 2] = (byte) Integer.decode("0x" + hexStr.substring(2 * i, 2 * i + 2)).intValue();
            }
            return b;

        } catch (Exception e) {

            return null;
        }
    }

    public static byte[] aes256CBCEncrypt(String plainText, byte[] keyBytes) {

        try {
            Cipher cipher = prepareCipher(Cipher.ENCRYPT_MODE, keyBytes);

            byte[] byteContent = plainText.getBytes("UTF-8");

            return cipher.doFinal(byteContent);

        } catch (GeneralSecurityException e) {
            logger.error("Failed to aes256CBCEncrypt", e);

        } catch (UnsupportedEncodingException e) {
            logger.error("Failed to aes256CBCEncrypt", e);
        }
        return null;
    }

    private static Cipher prepareCipher(int cipherMode, byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        IvParameterSpec iv = new IvParameterSpec(new byte[16]);

        cipher.init(cipherMode, key, iv);

        return cipher;
    }

    public static String aes256CBCDecrypt(byte[] cipherBytes, byte[] keyBytes) {

        try {

            Cipher cipher = prepareCipher(Cipher.DECRYPT_MODE, keyBytes);

            return new String(cipher.doFinal(cipherBytes), "UTF-8");

        } catch (GeneralSecurityException e) {
            logger.error("Failed to aes256CBCDecrypt. cipherBytes : " + bytes2Hex(cipherBytes), e);

        } catch (UnsupportedEncodingException e) {
            logger.error("Failed to aes256CBCDecrypt. cipherBytes : " + bytes2Hex(cipherBytes), e);
        }
        return null;
    }

    public static String aes256CBCEncrypt(String plainText) {

        return bytes2Hex(aes256CBCEncrypt(plainText, DEFAULT_AES_KEY_BYTES));
    }

    public static String aes256CBCDecrypt(String cipherText) {

        return aes256CBCDecrypt(hex2Bytes(cipherText), DEFAULT_AES_KEY_BYTES);
    }


    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException {
//        String cipherText = aes256CBCEncrypt("lewis.wang77@gmail.com,20131112111");
//        System.out.println(cipherText);
//        System.out.println(aes256CBCDecrypt(cipherText));

        Long date = new Date().getTime();
        SendMessage.sendMessage("18569034337", "测试");

    }
}
