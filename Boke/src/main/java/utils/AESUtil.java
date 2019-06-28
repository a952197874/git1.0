package utils;

import org.apache.shiro.codec.Base64;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

public class AESUtil {
    private static final String key = "com.jlu.aes.keys";
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";//

    public static String binary(byte[] bytes, int radix) {

        return new BigInteger(1, bytes).toString(radix);
    }

    public static String base64Encode(byte[] bytes){
        return Base64.encodeToString(bytes);
    }

    public static byte[] base64Decode(String base64Code) throws Exception {
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);

    }

    /**
     * AES加密
     *
     * @param content    钥加密的内容
     * @param encrtptKey 加密的密钥
     * @return
     * @throws Exception
     */
    public static byte[] aesEncryptTobytes(String content, String encrtptKeys) throws Exception {
       //密钥生成器，会影响Ciper的处理
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);

        //AES/CBC/PKCS5paddding增加偏移量，增加算法难度。
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encrtptKeys.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));


    }

    /**
     * AES加密为base64
     * @param content
     * @param encrtptKeys
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encrtptKeys)throws Exception{

        return base64Encode(aesEncryptTobytes(content,encrtptKeys));

    }

    /**
     * AESj解密
     * @param bytes 要解密的内容
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String aesDecryptBybytes(byte[]bytes,String key) throws Exception{
        KeyGenerator kgen=KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher=Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key.getBytes(),"AES"));

        byte[] decrypt=cipher.doFinal(bytes);
        return new String(decrypt);
    }

    /**
     * base64 AES解密
     * @param encrypt base64加密过的
     * @param Key 密钥
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String encrypt,String Key)throws Exception{
        return StringUtils.isEmpty(encrypt)?null :aesDecryptBybytes(base64Decode(encrypt),key);

    }

    public static void main(String[] args) throws Exception{
        String i=AESUtil.binary(AESUtil.aesEncryptTobytes("nihao","1234567891234567"),16);
        System.out.println(i);
        String y=AESUtil.aesDecryptBybytes(AESUtil.aesEncryptTobytes("nihao","1234567891234567"),"1234567891234567");
        System.out.println(y);
    }

}


