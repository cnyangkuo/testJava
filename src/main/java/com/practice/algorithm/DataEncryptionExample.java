package com.practice.algorithm;

/**
 * @author yangkuo
 * @date 2024/9/16
 * @description
 */
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DataEncryptionExample {

    public static void main(String[] args) throws Exception {
        DataEncryptionExample dataEncryptionExample = new DataEncryptionExample();
        dataEncryptionExample.test("abc");
    }

    public void test(String aesKey) throws Exception {
        // 假设这里已经有了解密后的 AES 密钥
        byte[] decryptedAesKeyBytes = Base64.getDecoder().decode(aesKey);
        SecretKey decryptedAesKey = new SecretKeySpec(decryptedAesKeyBytes, 0, decryptedAesKeyBytes.length, "AES");

        // 原始数据
        String originalData = "Hello, this is a secret message. 不要告诉别人！";

        // 使用 AES 加密数据
        byte[] encryptedData = encryptData(originalData.getBytes(), decryptedAesKey);

        // 输出加密后的数据
        System.out.println("Encrypted Data: " + Base64.getEncoder().encodeToString(encryptedData));

        // 使用 AES 解密数据
        byte[] decryptedData = decryptData(encryptedData, decryptedAesKey);

        // 输出解密后的数据
        System.out.println("Decrypted Data: " + new String(decryptedData));
    }

    private static byte[] encryptData(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private static byte[] decryptData(byte[] encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }
}