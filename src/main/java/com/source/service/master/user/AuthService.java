package com.source.service.master.user;

import com.source.dto.master.user.UserReqDto;

public interface AuthService {
	
	String verifyEmail(String accetoken);

	UserReqDto resendVerificationEmail(UserReqDto req);

	UserReqDto sendResetPasswordEmail(UserReqDto req);

	UserReqDto resetPassword(UserReqDto req);

}
