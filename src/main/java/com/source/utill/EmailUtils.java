package com.source.utill;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.source.constant.Credential;

@Service
public class EmailUtils {

	@Autowired
	FileHandlerUtils fs;
	
	@Value("${sc.system.doc.path}")
	String serverFolderPath;
	
	Properties props = new Properties();
	

	/* Common method no need to change start **/
	public EmailUtils() {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.quitwait", "false");
	}

	private class SmtpAuthenticator extends javax.mail.Authenticator {
		String username = "";
		String password = "";

		public SmtpAuthenticator(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	private EmailsData sentSmtpMail(String userName, String password, EmailsData emails) {
		try {
			Authenticator auth = new SmtpAuthenticator(userName, password);
			Session mailSession = Session.getInstance(props, auth);
			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(emails.getEmailSubject());

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(emails.getEmailBody(), "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			/* Add multiple attachedment */
			for (String fileName : emails.getAttachedment()) {
				messageBodyPart = new MimeBodyPart();
				String path = serverFolderPath + "/" + fileName;
				DataSource source = new FileDataSource(path);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fs.getFilename(fileName));
				multipart.addBodyPart(messageBodyPart);
			}

			message.setContent(multipart);
			message.setFrom(new InternetAddress(userName));
			message.setRecipients(javax.mail.Message.RecipientType.TO,
					InternetAddress.parse(String.join(",", emails.getSentToEmail())));
			message.setRecipients(javax.mail.Message.RecipientType.CC,
					InternetAddress.parse(String.join(",", emails.getCcToEmail())));

			transport.connect();
			Transport.send(message);
			transport.close();
			System.err.println("Email sent to" + emails.getSentToEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emails;
	}
	/* Common method no need to change end **/

	/* @Async General method to send email */
	@Async
	public EmailsData sendGeneralEmail(EmailsData emails) {
		return sentSmtpMail(Credential.GENERAL_USER, Credential.GENERAL_PASSWORD, emails);
	}

}
