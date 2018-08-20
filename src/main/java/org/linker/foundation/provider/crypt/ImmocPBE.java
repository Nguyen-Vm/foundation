package org.linker.foundation.provider.crypt;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @author RWM
 * @date 2018/5/28
 */
public class ImmocPBE {

    public static void main(String[] args) {
        jdkPBE("ruanweimin");
        bcPBE("ruanweimin");
    }

    public static void jdkPBE(String val) {
        // 初始化盐
        SecureRandom random = new SecureRandom();
        byte[] salt = random.generateSeed(8);

        // 口令与密钥
        String password = "immoc";
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            Key key = factory.generateSecret(pbeKeySpec);

            // 加密
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
            byte[] result = cipher.doFinal(val.getBytes());
            System.out.println("jck pbe encrypt: " + Base64.encodeBase64String(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
            result = cipher.doFinal(result);
            System.out.println("jdk pbe decrypt: " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bcPBE(String val) {
        // 初始化盐
        SecureRandom random = new SecureRandom();
        byte[] salt = random.generateSeed(8);

        // 口令与密钥
        String password = "immoc";
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());

        try {
            Security.addProvider(new BouncyCastleProvider());

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES", "BC");
            factory.getProvider();
            Key key = factory.generateSecret(pbeKeySpec);

            // 加密
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
            byte[] result = cipher.doFinal(val.getBytes());
            System.out.println("jck pbe encrypt: " + Base64.encodeBase64String(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
            result = cipher.doFinal(result);
            System.out.println("jdk pbe decrypt: " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
