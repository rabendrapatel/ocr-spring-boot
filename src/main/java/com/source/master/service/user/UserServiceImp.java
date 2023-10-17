package com.source.master.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.source.master.dto.user.UserListDto;
import com.source.master.dto.user.UserReqDto;
import com.source.master.entity.user.UserMaster;
import com.source.master.repository.user.UserMasterRepository;
import com.source.specification.user.UserSpecification;


@Service
public class UserServiceImp implements UserService {

	@Autowired
	ModelMapper m;
	
	@Autowired
	UserMasterRepository userMasterRepo;
	
	@Override
	public Page<UserListDto> getUserList(Pageable pageable, UserReqDto req) {
		Specification<UserMaster> spec = UserSpecification.createUserListFilter(req);
		Page<UserMaster> list = userMasterRepo.findAll(spec,pageable);
		
		List<UserListDto> resAL = list.stream().map(elm -> {
			UserListDto dto = m.map(elm, UserListDto.class);
			return dto;
		}).collect(Collectors.toList());
		return new PageImpl<>(resAL, pageable, list.getTotalElements());
	}

	@Override
	public UserReqDto saveUser(UserReqDto req) {
		return null;
	}

	@Override
	public UserReqDto registerUser(UserReqDto req) {
		return null;
	}

	@Override
	public UserMaster findByUserId(Long userId) {
		return userMasterRepo.findByUserId(userId);
	}

	@Override
	public UserMaster getByUserName(String userName) {
		return userMasterRepo.findByUserName(userName);
	}

	

}
