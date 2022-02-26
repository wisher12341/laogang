package com.lejian.laogang.util;

import com.lejian.laogang.aop.CodeField;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Properties;

public class AESUtils {

    private static String password = null;

     static {
         try {
             Properties props = new Properties();
             props.load(AESUtils.class.getResourceAsStream("/application.properties"));
             password = props.getProperty("aes.password");
         }catch (Exception e){

         }
    }


    public static void decode(Object obj){
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields){
                if (field.getAnnotation(CodeField.class)!=null){
                    field.setAccessible(true);
                    String value = (String) field.get(obj);
                    if (StringUtils.isNotBlank(value) && !value.equals("0")) {
                        field.set(obj, decrypt(value));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void encode(Object obj){
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                if (field.getAnnotation(CodeField.class)!=null){
                    String value = (String) field.get(obj);
                    if (StringUtils.isNotBlank(value) && !value.equals("0")) {
                        field.set(obj, encrypt(value));
                    }
//                    else{
//                        field.set(obj,null);
//                    }
                }
//                else if (!field.getName().equals("id")){
//                    field.set(obj,null);
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @return
     */
    public static String encrypt(String content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
//            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**解密
     * @param content  待解密内容
     * @return
     */
    public static String decrypt(String content) {
        try {
            byte[] decryptFrom = parseHexStr2Byte(content);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
//            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(decryptFrom);
            return new String(result,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}