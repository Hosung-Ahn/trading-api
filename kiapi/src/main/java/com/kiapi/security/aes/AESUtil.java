package com.kiapi.security.aes;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Component
@Slf4j
public class AESUtil {
    @Value("${secret.secret-key}")
    private String secretKey;
    private SecretKey key;
    private static final String ALGORITHM = "AES";

    @PostConstruct
    protected void init() throws NoSuchAlgorithmException {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16); // Use only first 128 bits (16 bytes) for AES
        key = new SecretKeySpec(keyBytes, ALGORITHM);
    }

    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("암호화 에러", e);
            // custom 한 에러로 변경한 뒤 GlobalExceptionHandler 에서 처리하도록 할 수 있습니다.
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedData){
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] originalBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(originalBytes);
        } catch (Exception e) {
            log.error("복호화 에러", e);
            // custom 한 에러로 변경한 뒤 GlobalExceptionHandler 에서 처리하도록 할 수 있습니다.
            throw new RuntimeException(e);
        }
    }

}
