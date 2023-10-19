package com.source.master.service.user;

import com.source.master.dto.user.UserReqDto;

public interface AuthService {
	
	String verifyEmail(String accetoken);

	UserReqDto resendVerificationEmail(UserReqDto req);

}
