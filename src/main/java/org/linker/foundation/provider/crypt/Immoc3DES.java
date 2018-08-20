package org.linker.foundation.provider.crypt;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @author RWM
 * @date 2018/5/28
 */
public class Immoc3DES {

    public static void main(String[] args) {
        jdk3DES("ruanweimin");
        bc3DES("ruanweimin");
    }

    private static void jdk3DES(String val) {
        try {
            // 生产KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            // keyGenerator.init(168);
            keyGenerator.init(new SecureRandom());// 生成默认长度的KEY
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();

            // KEY转换
            DESedeKeySpec desKeySpec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(desKeySpec);

            // 加密,加密方式/工作模式/填充方式
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(val.getBytes());
            System.out.println("jdk 3des encrypt: " + Hex.toHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("jdk 3des decrypt: " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bc3DES(String val) {
        try {
            Security.addProvider(new BouncyCastleProvider());

            // 生产KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede", "BC");
            keyGenerator.getProvider();
            // keyGenerator.init(168);
            keyGenerator.init(new SecureRandom());// 生成默认长度的KEY
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();

            // KEY转换
            DESedeKeySpec desKeySpec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(desKeySpec);

            // 加密,加密方式/工作模式/填充方式
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(val.getBytes());
            System.out.println("bc 3des encrypt: " + Hex.toHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("bc 3des decrypt: " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
