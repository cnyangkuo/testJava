package com.practice.algorithm;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyExchangeExample {

    public static void main(String[] args) throws Exception {
        // 生成 RSA 密钥对
        KeyPair keyPair = generateRSAKeyPair();

        // 生成 AES 密钥
        SecretKey aesKey = generateAESKey();

        // 使用 RSA 公钥加密 AES 密钥
        byte[] encryptedAesKey = encryptWithPublicKey(aesKey.getEncoded(), keyPair.getPublic());

        // 输出加密后的 AES 密钥
        System.out.println("Encrypted AES Key: " + Base64.getEncoder().encodeToString(encryptedAesKey));

        // 使用 RSA 私钥解密 AES 密钥
        byte[] decryptedAesKeyBytes = decryptWithPrivateKey(encryptedAesKey, keyPair.getPrivate());
        SecretKey decryptedAesKey = new SecretKeySpec(decryptedAesKeyBytes, 0, decryptedAesKeyBytes.length, "AES");

        // 输出解密后的 AES 密钥
        System.out.println("Decrypted AES Key: " + Base64.getEncoder().encodeToString(decryptedAesKey.getEncoded()));

        // 使用 AES 密钥加密数据
        String originalData = "Hello, this is a secret message.";
        byte[] encryptedData = encryptData(originalData.getBytes(), decryptedAesKey);

        // 输出加密后的数据
        System.out.println("Encrypted Data: " + Base64.getEncoder().encodeToString(encryptedData));

        // 使用 AES 密钥解密数据
        byte[] decryptedData = decryptData(encryptedData, decryptedAesKey);

        // 输出解密后的数据
        System.out.println("Decrypted Data: " + new String(decryptedData));
    }

    private static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    private static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // 使用 256 位 AES 密钥
        return keyGen.generateKey();
    }

    private static byte[] encryptWithPublicKey(byte[] data, java.security.PublicKey publicKey) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    private static byte[] decryptWithPrivateKey(byte[] encryptedData, java.security.PrivateKey privateKey) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedData);
    }

    private static byte[] encryptData(byte[] data, SecretKey key) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private static byte[] decryptData(byte[] encryptedData, SecretKey key) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }
}

//import javax.crypto.K Zxdfghjhgsa aqSDFGHDSAZx\
//][poiu21]`cxcvb/.,mn bnm