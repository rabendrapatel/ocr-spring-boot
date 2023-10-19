package com.source.tran.helper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.source.constant.Constant;
import com.source.master.entity.user.UserMaster;
import com.source.utill.CommonUtils;
import com.source.utill.EmailUtils;
import com.source.utill.EmailsData;

@Component
public class UserEmailHelper {
	
	@Autowired
	EmailUtils emailUtils;
	
	@Value("${sc.server.path}")
	String serverPath;
	
	public void sendUserDetailsOnEmail(UserMaster user) {
		
		String subject = "Registration Successful";
		String emailContent = "Dear " + user.getFirstName() + ",\n\n" +
                "Your registration was successful!\n\n" +
                "Your username is: " + user.getUserName();

		EmailsData emails = EmailsData.getInstance();
		emails.setEmailSubject(subject);
		emails.setEmailBody(emailContent); 

		/* Get EmailId of Pending Level Approver */
		emails.getSentToEmail().add(user.getEmail());

		/* Send Async SMTP Email */
		emailUtils.sendGeneralEmail(emails);
	}
	
	public void sendEmailVerificationEmail(UserMaster user) {
		String token = CommonUtils.encodeToBase64(String.valueOf(user.getUserId()));
		String verificationLink = serverPath+"/api/tran/auth/verify/email?token="+token;

		String subject = "Verify Your Email Address";
		String emailContent = "<!DOCTYPE html>\n" +
		    "<html>\n" +
		    "<head>\n" +
		    "    <title>Account Verification</title>\n" +
		    "</head>\n" +
		    "<body>\n" +
		    "    <p>Dear " + user.getFirstName() + ",</p>\n" +
		    "    <p>Thank you for registering with us. To activate your account, please click the following link:</p>\n" +
		    "    <a href=\"" + verificationLink + "\">Verify Your Email</a>\n" +
		    "    <p>If the link above doesn't work, you can copy and paste the following URL into your browser's address bar:</p>\n" +
		    "    <p>" + verificationLink + "</p>\n" +
		    "    <p>If you did not request this verification, please disregard this email.</p>\n" +
		    "    <p>Best regards,</p>\n" +
		    "    <p>Your " + Constant.APP_NAME + " Team</p>\n" +
		    "</body>\n" +
		    "</html>";


		EmailsData emails = EmailsData.getInstance();
		emails.setEmailSubject(subject);
		emails.setEmailBody(emailContent); 

		/* Get EmailId of Pending Level Approver */
		emails.getSentToEmail().add(user.getEmail());

		/* Send Async SMTP Email */
		emailUtils.sendGeneralEmail(emails);
	}
}
