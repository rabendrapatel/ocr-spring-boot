package com.source.master.service.user;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.source.master.dto.user.UserReqDto;
import com.source.master.entity.user.UserMaster;
import com.source.master.repository.user.UserMasterRepository;
import com.source.tran.helper.user.UserEmailHelper;
import com.source.tran.helper.user.UserTranHelper;
import com.source.utill.CommonUtils;

@Service
public class AuthServiceImp implements AuthService {

	@Autowired
	UserTranHelper tranHelper;

	@Autowired
	UserEmailHelper emailHelper;

	@Autowired
	UserMasterRepository userMasterRepo;

	@Override
	public String verifyEmail(String accetoken) {
		Long userId = Long.valueOf(CommonUtils.decodeFromBase64(accetoken));
		UserMaster user = userMasterRepo.findByUserId(userId);
		if (Optional.ofNullable(user).isPresent() && user.getIsEmailVerify().equalsIgnoreCase("No")) {
			user.setIsEmailVerify("Yes");
			userMasterRepo.save(user);
			return tranHelper.getEmailVerifiedMessage(user, "Success");
		}
		return tranHelper.getEmailVerifiedMessage(user, "failed");
	}

	@Override
	public UserReqDto resendVerificationEmail(UserReqDto req) {
		Optional<UserMaster> userOp = userMasterRepo.findByEmail(req.getEmail());
		if(!userOp.isPresent()) {
			throw new RuntimeException("Email not found . Please try again.");
		}else if(userOp.isPresent() && userOp.get().getIsEmailVerify().equalsIgnoreCase("yes")) {
			throw new RuntimeException("Email already verified");
		}
		emailHelper.sendEmailVerificationEmail(userOp.get());
		return req;
	}

}
