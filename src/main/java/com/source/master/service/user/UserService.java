package com.source.master.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.source.config.jwt.UserPrincipal;
import com.source.master.dto.user.UserDetailsDto;
import com.source.master.dto.user.UserListDto;
import com.source.master.dto.user.UserReqDto;
import com.source.master.entity.user.UserMaster;

public interface UserService {

	UserReqDto registerUser(UserReqDto req);

	UserMaster findByUserId(Long userId);

	UserMaster getByUserName(String userName);

	UserDetailsDto getUserDetailsByUserId(UserPrincipal user);

	UserReqDto saveUser(UserReqDto req, UserPrincipal user);
	
	UserReqDto updateUser(UserReqDto req, UserPrincipal user);
	
	Page<UserListDto> getUserList(Pageable pageable, UserReqDto req, UserPrincipal user) ;


}
