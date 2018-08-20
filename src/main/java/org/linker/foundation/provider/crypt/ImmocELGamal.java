package org.linker.foundation.provider.crypt;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.DHParameterSpec;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author RWM
 * @date 2018/5/29
 */
public class ImmocELGamal {

    public static void main(String[] args) {
        bcElGamal("ruanweimin");
    }

    public static void bcElGamal(String val) {
        try {
            //
            Security.addProvider(new BouncyCastleProvider());

            // 初始化密钥
            AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator.getInstance("ElGamal");
            algorithmParameterGenerator.init(256);
            AlgorithmParameters algorithmParameters = algorithmParameterGenerator.generateParameters();
            DHParameterSpec dhParameterSpec = algorithmParameters.getParameterSpec(DHParameterSpec.class);
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ElGamal");
            keyPairGenerator.initialize(dhParameterSpec, new SecureRandom());

            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey elGamalPublicKey = keyPair.getPublic();
            PrivateKey elGamalPrivateKey = keyPair.getPrivate();
            System.out.println("Public Key: " + Base64.encodeBase64String(elGamalPublicKey.getEncoded()));
            System.out.println("Private Key: " + Base64.encodeBase64String(elGamalPrivateKey.getEncoded()));


            // 公钥加密、私钥解密
            // 公钥——加密
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(elGamalPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("ElGamal");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            Cipher cipher = Cipher.getInstance("ElGamal");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(val.getBytes());
            System.out.println("公钥——加密: " + Base64.encodeBase64String(result));

            // 公钥加密、私钥解密
            // 私钥——解密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(elGamalPrivateKey.getEncoded());
            keyFactory = KeyFactory.getInstance("ElGamal");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            cipher = Cipher.getInstance("ElGamal");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            result = cipher.doFinal(result);
            System.out.println("私钥——解密: " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
