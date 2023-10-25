package com.source.service.master.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.source.config.jwt.UserPrincipal;
import com.source.dto.master.user.UserDetailsDto;
import com.source.dto.master.user.UserListDto;
import com.source.dto.master.user.UserReqDto;
import com.source.entity.master.user.UserMaster;

public interface UserService {

	UserReqDto registerUser(UserReqDto req);

	UserMaster findByUserId(Long userId);

	UserMaster getByUserName(String userName);

	UserDetailsDto getUserDetailsByUserId(UserPrincipal user);

	UserReqDto saveUser(UserReqDto req, UserPrincipal user);
	
	UserReqDto updateUser(UserReqDto req, UserPrincipal user);
	
	Page<UserListDto> getUserList(Pageable pageable, UserReqDto req, UserPrincipal user) ;


}
