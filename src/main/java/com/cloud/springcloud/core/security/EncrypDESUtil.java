package com.cloud.springcloud.core.security;

import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;

public   class EncrypDESUtil {


    /**
     * 使用DES对字符串加密
     *
     * @param str
     *            utf8编码的字符串
     * @param key
     *            密钥（56位，7字节）
     *
     */
    public static byte[] desEncrypt(String str, String key) throws Exception {
        if (str == null || key == null)
            return null;
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "DES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return bytes;
    }

    /**
     * 使用DES对数据解密
     *
     * @param bytes
     *            utf8编码的二进制数据
     * @param key
     *            密钥（16字节）
     * @return 解密结果
     * @throws Exception
     */
    public static String desDecrypt(byte[] bytes, String key) throws Exception {
        if (bytes == null || key == null)
            return null;
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "DES"));
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }

    /**
     * 使用base64解决乱码
     *
     * @param secretKey
     *            加密后的字节码
     */
    public static String jdkBase64String(byte[] secretKey) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(secretKey);
    }

    /**
     * 使用jdk的base64 解密字符串 返回为null表示解密失败
     *
     * @throws IOException
     */
    public static byte[] jdkBase64Decoder(String str) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(str);
    }

    public static void main(String[] args) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code" , 0);
        jsonObject.put("message", "dfgdfg梵蒂冈反对的范德萨非凡哥：");
        String openId = jdkBase64String(desEncrypt(jsonObject.toJSONString(), "ludbqhqh"));
        System.out.println(openId);

        String  dsa = "vlUZUbxXysFraT6UfYtaVfhcVXir92YMGcqyzYPjtwIg3NN7mn/49WHdG7MoFjwfcJlpC6CyQ5mA\n" +
                "GXUJsaB1xS6Kk/7Qkwl1" ;
        String desDecrypt = desDecrypt(jdkBase64Decoder(dsa), "ludbqhqh");
        System.out.println(desDecrypt);
    }

}
