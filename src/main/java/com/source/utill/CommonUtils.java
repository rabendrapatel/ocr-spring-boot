package com.source.utill;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class CommonUtils {
	private static final String ALGORITHM = "AES/ECB/PKCS5PADDING";

	public static String encodeToBase64(String plainText) {
		byte[] encodedBytes = Base64.getUrlEncoder().encode(plainText.getBytes());
		return new String(encodedBytes);
	}

	public static String decodeFromBase64(String encodedText) {
		byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedText);
		return new String(decodedBytes);
	}

	public static String encodeAES(String plainText, String secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);

			byte[] encryptedText = cipher.doFinal(plainText.getBytes());
			return Base64.getEncoder().encodeToString(encryptedText);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String decodeAES(String encryptedText, String secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);

			byte[] decodedText = Base64.getDecoder().decode(encryptedText);
			byte[] plainText = cipher.doFinal(decodedText);
			return new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
