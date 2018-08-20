package org.linker.foundation.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

/**
 * @author RWM
 * @date 2018/4/10
 * @description:
 */
public class CryptUtils {
    public static final String val = "ruanweimin";

    private static MessageDigest SHA1;
    private static MessageDigest MD5;
    private static MessageDigest MD4;
    private static MessageDigest MD2;
    static {
        try {
            SHA1 = MessageDigest.getInstance("SHA-1");//SHA=SHA-1
            MD5 = MessageDigest.getInstance("MD5");
            MD2 = MessageDigest.getInstance("MD2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("get message digest instance error...", e);
        }
    }

    /**
     * Base64编码
     * **/
    public static String encodeBase64(String val) {
        return ByteUtils.string(Base64.getEncoder().encode(val.getBytes()));
    }

    /**
     * Base64解码
     * **/
    public static String decodeBase64(String val) {
        return ByteUtils.string(Base64.getDecoder().decode(val));
    }

    /**
     * SHA1签名
     */
    public static String sha1(String val) {
        byte[] sha1Bytes = SHA1.digest(val.getBytes());
        return Hex.encodeHexString(sha1Bytes);
        //return toHex(sha1Bytes);
    }

    public static String bcSHA1(String val) {
        Digest digest = new SHA1Digest();
        digest.update(val.getBytes(), 0, val.getBytes().length);
        byte[] sha1Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha1Bytes, 0);
        return org.bouncycastle.util.encoders.Hex.toHexString(sha1Bytes);
    }

    public static String bcSHA224(String val) {
        Digest digest = new SHA224Digest();
        digest.update(val.getBytes(), 0, val.getBytes().length);
        byte[] sha224Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha224Bytes, 0);
        return org.bouncycastle.util.encoders.Hex.toHexString(sha224Bytes);
    }

    public static String bcSHA224_2() {
        Security.addProvider(new BouncyCastleProvider());
        //TODO: 参考bcMD4() ^_^
        return null;
    }

    public static String ccSHA1(String val) {
        return DigestUtils.sha1Hex(val);
    }

    /**
     * MD5签名
     */
    public static String md5(String val) {
        byte[] MD5Bytes = MD5.digest(val.getBytes());
        return Hex.encodeHexString(MD5Bytes);
        //return toHex(MD5Bytes);
    }

    public static String md2(String val) {
        byte[] md2Bytes = MD2.digest(val.getBytes());
        return Hex.encodeHexString(md2Bytes);
    }

    public static String bcMD4(String val) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            MD4 = MessageDigest.getInstance("MD4");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("get message digest instance error...", e);
        }
        byte[] md4Bytes = MD4.digest(val.getBytes());
        return Hex.encodeHexString(md4Bytes);
        /*Digest digest = new MD4Digest();
        digest.update(val.getBytes(), 0, val.getBytes().length);
        byte[] md4Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md4Bytes, 0);
        return org.bouncycastle.util.encoders.Hex.toHexString(md4Bytes);*/
    }

    public static String bcMD5(String val) {
        Digest digest = new MD5Digest();
        digest.update(val.getBytes(), 0, val.getBytes().length);
        byte[] md5Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md5Bytes, 0);
        return org.bouncycastle.util.encoders.Hex.toHexString(md5Bytes);
    }

    public static String ccMD5(String val) {
        return DigestUtils.md5Hex(val.getBytes());
    }

    public static String ccMD2(String val) {
        return DigestUtils.md2Hex(val.getBytes());
    }

    /** 字节数组转十六进制字符串 **/
    public static String toHex(byte[] digest) {
        StringBuffer hexstr = new StringBuffer();
        String shaHex;
        for (int i = 0; i < digest.length; i++) {
            shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();
    }

    /** 16 进制字符串字节数组 **/
    public static byte[] hex2Bytes(String src){
        byte[] res = new byte[ src.length() / 2];
        char[] chs = src.toCharArray();
        for(int i = 0, c = 0; i < chs.length; i += 2, c++){
            res[c] = (byte) (Integer.parseInt(new String(chs,i,2), 16));
        }
        return res;
    }

    public static String jdkHmacMD5(String val){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");// 初始化KeyGenerator
            SecretKey secretKey = keyGenerator.generateKey();// 产生密钥
            byte[] key = secretKey.getEncoded();// 获得密钥

            key = Hex.decodeHex(new char[] {'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a',});

            SecretKey restoreSecretKey = new SecretKeySpec(key, "HmacMD5");// 还原密钥

            Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());// 实例化MAC
            mac.init(restoreSecretKey);// 初始化mac
            byte[] hmacMD5Bytes = mac.doFinal(val.getBytes());// 执行摘要
            return Hex.encodeHexString(hmacMD5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String bcHmacMD5(String val) {
        HMac hmac = new HMac(new MD5Digest());
        hmac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("aaaaaaaaaa")));
        hmac.update(val.getBytes(), 0, val.getBytes().length);

        byte[] hmacMD5Bytes = new byte[hmac.getMacSize()];
        hmac.doFinal(hmacMD5Bytes, 0);
        return org.bouncycastle.util.encoders.Hex.toHexString(hmacMD5Bytes);

    }

    public static void main(String[] args) {
        System.out.println("JDK  MD5 : " + md5(val));
        System.out.println("JDK  MD2 : " + md2(val));
        System.out.println("BC   MD4 : " + bcMD4(val));
        System.out.println("BC   MD5 : " + bcMD5(val));
        System.out.println("CC   MD5 : " + ccMD5(val));
        System.out.println("CC   MD2 : " + ccMD2(val));

        System.out.println("JDK SHA1 : " + sha1(val));
        System.out.println("BC  SHA1 : " + bcSHA1(val));
        System.out.println("CC  SHA1 : " + ccSHA1(val));
        System.out.println("BC  SHA224 : " + bcSHA224(val));

        System.out.println("JDK HMAC MD5 : " + jdkHmacMD5(val));
        System.out.println("BC  HMAC MD5 : " + bcHmacMD5(val));
    }

}
