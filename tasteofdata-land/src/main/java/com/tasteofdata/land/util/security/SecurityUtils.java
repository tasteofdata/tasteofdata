package com.tasteofdata.land.util.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/**
 * 加密工具类
 * @ClassName: SecurityUtils 
 * @date 2014年2月10日 下午2:59:26 
 *
 */
public class SecurityUtils {
//    private static Logger logger = Logger.getLogger(SecurityUtils.class);
//    private static final String DEFAULT_CHARSET = "UTF-8";
//    private static final String RC4_DEFAULT_PASSWORD = "3984aF333#@213";
//
//
//    /**
//     * 取MD5加密的secretKey的后8位当做密钥
//     * @param sourceSecretKey
//     * @return
//     */
//    private static String getSecretKey(String sourceSecretKey){
//        String md5secretKey = MD5Encode(sourceSecretKey);
//        return md5secretKey.substring(md5secretKey.length() - 8);
//    }

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * MD5加密
     * @param str
     * @return
     */
    public static String MD5Encode(String str) {
        if (str == null) return null;
        
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            return byteArrayToHexString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    
    private static final String DEFAULT_SECRETKEY = "tasteofdata_&12~48@)^*A";
    
    /**
     * RC4加密
     * @param input
     * @return
     */
    public static String RC4Encrypt(String str){
    	return RC4Encrypt(str,DEFAULT_SECRETKEY);
    }
    
    /**
     * RC4解密
     * @param encryptStr
     * @param secretKey
     * @return
     */
    public static String RC4Decrypt(String encryptStr){
    	return RC4Encrypt(encryptStr,DEFAULT_SECRETKEY);
    }
    
    /**
     * RC4加密
     * @param input
     * @return
     */
    public static String RC4Encrypt(String str,String secretKey){
    	if(null==str) return null;
    	String security = Base64.encode(RC4.encrypt(str.getBytes(), secretKey.getBytes()));
    	return security.replaceAll("/", "_");
    }
    
    /**
     * RC4解密
     * @param encryptStr
     * @param secretKey
     * @return
     */
    public static String RC4Decrypt(String encryptStr,String secretKey){
    	if(null==encryptStr) return null;
    	return new String(RC4.encrypt(Base64.decode(encryptStr.replaceAll("_", "/")), secretKey.getBytes()));
    }
    
    /**
     * RC4解密
     * @param input
     * @return
     */
    public static String MD5Encode(InputStream input) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            byte[] b = new byte[1024 * 10];
            int length = -1;

            while ((length = input.read(b)) > -1) {
                messagedigest.update(b, 0, length);
            }
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("InputStream md5 name error");
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("InputStream md5 name close error");
                }
            }
        }
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        String c0 = hexDigits[(bt & 0xf0) >> 4];
        String c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static final char[] c = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz".toCharArray();

    /**
     * 获得指定长度的随机字符串
     * @param len
     * @return
     */
    public static String getRandomString(int len) {
        if(len < 1){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(c[random.nextInt(c.length)]);
        }
        return sb.toString();
    }

    public static String UUID() {
        return UUID.randomUUID().toString();
    }


    
    /**
    public static void main(String[] args) {
        String plainText = "54";
        String s = encryptRC4(plainText);
        System.out.println("Bing Go: " + encryptRC4(plainText));
        System.out.println("---Bing Go: " + decryptRC4(s));
    }*/
}
