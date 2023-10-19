package com.source.tran.helper.user;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.source.constant.Constant;
import com.source.master.entity.user.UserMaster;

@Component
public class UserTranHelper {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public String generateUserName(String email) {
		String[] parts = email.split("@");
		if (parts.length == 2) {
			String username = parts[0];
			return username ;
		} else {
			return "User" + getRandomNo();
		}
	}

	private int getRandomNo() {
		long currentTimeMillis = System.currentTimeMillis();
		Random random = new Random(currentTimeMillis);
		return random.nextInt(100);
	}

	public String encodePassword(String password) {
		return bCryptPasswordEncoder.encode(password);
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
