package com.source.master.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.source.master.dto.user.UserListDto;
import com.source.master.dto.user.UserReqDto;
import com.source.master.entity.user.UserMaster;

public interface UserService {
	
	Page<UserListDto> getUserList(Pageable pageable, UserReqDto req) ;

	UserReqDto saveUser(UserReqDto req);

	UserReqDto registerUser(UserReqDto req);

	UserMaster findByUserId(Long userId);

	UserMaster getByUserName(String userName);


}
