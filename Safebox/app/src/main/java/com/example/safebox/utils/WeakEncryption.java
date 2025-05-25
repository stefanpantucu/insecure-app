package com.example.safebox.utils;

import android.util.Base64;
import org.apache.commons.codec.binary.Hex;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class WeakEncryption {
    // Intentionally insecure: Hardcoded encryption key
    private static final String ENCRYPTION_KEY = "SuperSecretKey12";

    // Intentionally insecure: Using ECB mode
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static String encrypt(String data) {
        try {
            // Intentionally insecure: Using a weak key derivation
            SecretKeySpec key = new SecretKeySpec(
                ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8), "AES"
            );

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Intentionally insecure: Using simple Base64 encoding
            return Base64.encodeToString(
                cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)),
                Base64.DEFAULT
            );
        } catch (Exception e) {
            e.printStackTrace();
            return data; // Intentionally insecure: Returns plain text on error
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            // Intentionally insecure: Using the same weak key derivation
            SecretKeySpec key = new SecretKeySpec(
                ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8), "AES"
            );

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedBytes = cipher.doFinal(
                Base64.decode(encryptedData, Base64.DEFAULT)
            );
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return encryptedData; // Intentionally insecure: Returns encrypted text on error
        }
    }
} 