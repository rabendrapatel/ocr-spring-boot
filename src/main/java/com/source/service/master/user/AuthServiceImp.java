package com.source.service.master.user;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.source.dto.master.user.UserReqDto;
import com.source.entity.master.user.UserMaster;
import com.source.entity.master.user.UserPasswordResetLink;
import com.source.helper.tran.user.UserEmailHelper;
import com.source.helper.tran.user.UserTranHelper;
import com.source.repository.master.user.UserMasterRepository;
import com.source.repository.master.user.UserPasswordResetLinkRepository;

@Service
public class AuthServiceImp implements AuthService {

	@Value("${app.aesSecret}")
	String aesSecretKey;

	@Autowired
	UserTranHelper userHelper;

	@Autowired
	UserEmailHelper emailHelper;

	@Autowired
	UserMasterRepository userMasterRepo;

	@Autowired
	UserPasswordResetLinkRepository userPasswordResetLinkRepo;

	@Override
	public String verifyEmail(String accetoken) {
		Long userId = userHelper.getUserIdByToken(accetoken, aesSecretKey);
		UserMaster user = userMasterRepo.findByUserId(userId);
		if (Optional.ofNullable(user).isPresent() && user.getIsEmailVerify().equalsIgnoreCase("No")) {
			user.setIsEmailVerify("Yes");
			userMasterRepo.save(user);
			return userHelper.getEmailVerifiedMessage(user, "Success");
		}
		return userHelper.getEmailVerifiedMessage(user, "failed");
	}

	@Override
	public UserReqDto resendVerificationEmail(UserReqDto req) {
		Optional<UserMaster> userOp = userMasterRepo.findByEmail(req.getEmail());
		if (!userOp.isPresent()) {
			throw new RuntimeException("Email not found . Please try again.");
		} else if (userOp.isPresent() && userOp.get().getIsEmailVerify().equalsIgnoreCase("yes")) {
			throw new RuntimeException("Email already verified");
		}
		UserMaster user = userOp.get();
		String token = userHelper.generateTokenByUserId(user.getUserId(), aesSecretKey);
		emailHelper.sendEmailVerificationEmail(user, token);
		return req;
	}

	@Override
	public UserReqDto sendResetPasswordEmail(UserReqDto req) {
		Optional<UserMaster> userOp = userMasterRepo.findByEmail(req.getEmail());
		if (!userOp.isPresent()) {
			throw new RuntimeException("Email not found . Please try again.");
		}
		String token = userHelper.generateTokenByUserId(userOp.get().getUserId(), aesSecretKey);
		String resetLink = emailHelper.sendResetPasswordEmail(userOp.get(), token);

		UserPasswordResetLink reset = new UserPasswordResetLink();
		reset.setToken(token);
		reset.setLink(resetLink);
		reset.setIsPasswordReset("No");
		userPasswordResetLinkRepo.save(reset);

		return req;
	}

	@Override
	public UserReqDto resetPassword(UserReqDto req) {
		UserPasswordResetLink reset = userPasswordResetLinkRepo.findByToken(req.getToken());
		if (reset == null || !"No".equalsIgnoreCase(reset.getIsPasswordReset())) {
			throw new RuntimeException("Invalid request, Please try again.");
		}
		
		Long userId = userHelper.getUserIdByToken(req.getToken(), aesSecretKey);
		UserMaster user = userMasterRepo.findByUserId(userId);

		if (user == null) {
			throw new RuntimeException("User not found, Please try again.");
		}
		reset.setIsPasswordReset("Yes");
		userPasswordResetLinkRepo.save(reset);

		String password = userHelper.encodePassword(req.getPassword());
		user.setPassword(password);
		userMasterRepo.save(user);

		return req;
	}

}
