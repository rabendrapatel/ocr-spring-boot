package com.source.utill;

import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class CommonUtils {

	public static String encodeToBase64(String text) {
		byte[] encodedBytes = Base64.getEncoder().encode(text.getBytes());
		return new String(encodedBytes);
	}

	public static String decodeFromBase64(String encodedText) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
		return new String(decodedBytes);
	}

}
