package com.source.tran.helper.user;

import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.source.constant.Constant;
import com.source.master.entity.user.UserMaster;
import com.source.utill.CommonUtils;

@Component
public class UserTranHelper {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public String generateUserName(String email, String mobileNo) {
		 String generateBy = "email";
		 if ("email".equalsIgnoreCase(generateBy)) {
            String[] parts = email.split("@");
            if (parts.length == 2) {
                String username = parts[0];
                return username;
            }
        } else if ("mobile".equalsIgnoreCase(generateBy)) {
            return mobileNo;
        }
        return "U" + getRandomNo();
	}

	private int getRandomNo() {
		long currentTimeMillis = System.currentTimeMillis();
		Random random = new Random(currentTimeMillis);
		return random.nextInt(100);
	}

	public String encodePassword(String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	public String generateTokenByUserId(Long userId, String aesSecretKey) {
		String aestoken = CommonUtils.encodeAES(String.valueOf(userId),aesSecretKey);
		JSONObject json = new JSONObject();
		json.put("token", aestoken);
		json.put("time", System.currentTimeMillis());
		String base64Token = CommonUtils.encodeToBase64(json.toString());
		return base64Token;
	}
	
	public Long getUserIdByToken(String token, String aesSecretKey) {
		String base64Token = CommonUtils.decodeFromBase64(token);
		JSONObject json = new JSONObject(base64Token);
		String encodedAes = json.getString("token");
		String aestoken = CommonUtils.decodeAES(encodedAes,aesSecretKey);
		return Long.valueOf(aestoken);
	}

	public String getEmailVerifiedMessage(UserMaster user, String type) {
		String appName = Constant.APP_NAME;
		
		if(type.equalsIgnoreCase("success")) {
			String emailContent = "<!DOCTYPE html>\n" +
			    "<html>\n" +
			    "<head>\n" +
			    "    <title>Account Verified</title>\n" +
			    "</head>\n" +
			    "<body>\n" +
			    "    <p style=\"text-align: center;\" >Dear <b>" + user.getFirstName() + "</b>,Your account with " + appName + " has been verified successfully! .You can now log in and start using our services.</p>\n" +
			    "</body>\n" +
			    "</html>";
			return emailContent;
		}else if(type.equalsIgnoreCase("failed")) {
			String emailContent = "<!DOCTYPE html>\n" +
			    "<html>\n" +
			    "<head>\n" +
			    "    <title>Account Verification Failed</title>\n" +
			    "</head>\n" +
			    "<body>\n" +
			    "    <p style=\"text-align: center;\">Dear <b>" + user.getFirstName() + "</b>,We regret to inform you that we were unable to verify your account with " + appName + ".If you believe this is in error, please contact our support team .</p>\n" +
			    "</body>\n" +
			    "</html>";
			return emailContent;
		}
		
		return "Invalid link , please contact our support team";
		
	}

	
}
