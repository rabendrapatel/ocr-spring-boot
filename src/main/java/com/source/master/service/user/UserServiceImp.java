package com.source.master.service.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.source.config.jwt.UserPrincipal;
import com.source.constant.FolderPaths;
import com.source.master.dto.user.UserDetailsDto;
import com.source.master.dto.user.UserListDto;
import com.source.master.dto.user.UserReqDto;
import com.source.master.entity.user.UserMaster;
import com.source.master.repository.user.UserMasterRepository;
import com.source.specification.user.UserSpecification;
import com.source.tran.helper.user.UserEmailHelper;
import com.source.tran.helper.user.UserTranHelper;
import com.source.utill.FileHandlerUtils;
import com.source.utill.MapperUtils;

@Service
public class UserServiceImp implements UserService {

	@Value("${app.aesSecret}")
	String aesSecretKey;

	@Autowired
	FileHandlerUtils fs;

	@Autowired
	UserTranHelper tranHelper;

	@Autowired
	UserEmailHelper emailHelper;

	@Autowired
	UserMasterRepository userMasterRepo;

	@Override
	public UserMaster findByUserId(Long userId) {
		return userMasterRepo.findByUserId(userId);
	}

	@Override
	public UserMaster getByUserName(String userName) {
		return userMasterRepo.findByUserName(userName);
	}

	@Override
	public Page<UserListDto> getUserList(Pageable pageable, UserReqDto req, UserPrincipal user) {
		ModelMapper m = MapperUtils.getInstance();
		Specification<UserMaster> spec = UserSpecification.createUserListFilter(req);
		Page<UserMaster> list = userMasterRepo.findAll(spec, pageable);

		List<UserListDto> resAL = list.stream().map(elm -> {
			UserListDto dto = m.map(elm, UserListDto.class);
			return dto;
		}).collect(Collectors.toList());
		return new PageImpl<>(resAL, pageable, list.getTotalElements());
	}

	@Override
	public UserReqDto saveUser(UserReqDto req, UserPrincipal user) {
		if (validateEmail(req.getEmail(), 0l)) {
			throw new RuntimeException("Email already exist");
		}
		ModelMapper m = MapperUtils.getInstance();
		userMasterRepo.findByEmail(req.getEmail()).ifPresent(u -> {
			throw new RuntimeException("Email already exist");
		});
		userMasterRepo.findByMobileNo(req.getMobileNo()).ifPresent(u -> {
			throw new RuntimeException("Mobile already exist");
		});
		String userName = tranHelper.generateUserName(req.getEmail());
		String password = tranHelper.encodePassword(req.getPassword());
		Optional.ofNullable(req.getPhoto()).filter(base64 -> !base64.isEmpty()).ifPresent(base64 -> {
			String fileName = userName + ".png";
			fileName = fs.uploadBase64ToFile(FolderPaths.USER, fileName, base64);
			req.setPhoto(fileName);
		});
		UserMaster userMaster = m.map(req, UserMaster.class);
		userMaster.setUserName(userName);
		userMaster.setPassword(password);
		userMaster.setCreatedBy(user.getUserId());
		userMaster.setUpdatedBy(user.getUserId());
		userMaster.setCompanyId(user.getCompanyId());
		userMaster = userMasterRepo.save(userMaster);
		return req;
	}

	@Override
	public UserReqDto updateUser(UserReqDto req, UserPrincipal user) {
		Optional<UserMaster> optionalUserMaster = userMasterRepo.findById(req.getUserId());
		if (optionalUserMaster.isPresent()) {
			UserMaster userMaster = optionalUserMaster.get();
			String userName = userMaster.getUserName();
			Optional.ofNullable(req.getPhoto()).filter(base64 -> !base64.isEmpty()).ifPresent(base64 -> {
				String fileName = userName + ".png";
				fileName = fs.uploadBase64ToFile(FolderPaths.USER, fileName, base64);
				req.setPhoto(fileName);
			});
			userMaster.setFirstName(req.getFirstName());
			userMaster.setLastName(req.getLastName());
			userMaster.setMobileNo(req.getMobileNo());
			userMaster.setRoleId(req.getRoleId());
			userMaster.setIsEmailVerify(req.getIsEmailVerify());
			userMaster.setIsMobileVerify(req.getIsMobileVerify());
			userMaster.setStatus(req.getStatus());
			userMaster.setUpdatedBy(user.getUserId());
			if (!req.getPhoto().isEmpty()) {
				userMaster.setPhoto(req.getPhoto());
			}
			userMaster = userMasterRepo.save(userMaster);
		}

		return req;
	}

	@Override
	public UserReqDto registerUser(UserReqDto req) throws RuntimeException {
		ModelMapper m = MapperUtils.getInstance();

		/* Validate if email already exists */
		userMasterRepo.findByEmail(req.getEmail()).ifPresent(user -> {
			throw new RuntimeException("Email already exist");
		});
		userMasterRepo.findByMobileNo(req.getMobileNo()).ifPresent(user -> {
			throw new RuntimeException("Mobile already exist");
		});

		/* Save user details */
		String userName = tranHelper.generateUserName(req.getEmail());
		String password = tranHelper.encodePassword(req.getPassword());
		Optional.ofNullable(req.getPhoto()).filter(base64 -> !base64.isEmpty()).ifPresent(base64 -> {
			String fileName = userName + ".png";
			fileName = fs.uploadBase64ToFile(FolderPaths.USER, fileName, base64);
			req.setPhoto(fileName);
		});

		UserMaster user = m.map(req, UserMaster.class);
		user.setUserName(userName);
		user.setPassword(password);
		user.setRoleId(1l);
		user.setStatus(1);
		user.setIsEmailVerify("No");
		user.setIsMobileVerify("No");
		user.setCreatedBy(1l);
		user.setUpdatedBy(1l);
		user = userMasterRepo.save(user);

		/* Send email to user */
		String token = tranHelper.generateTokenByUserId(user.getUserId(), aesSecretKey);
		emailHelper.sendEmailVerificationEmail(user, token);
		emailHelper.sendUserDetailsOnEmail(user);

		return req;
	}

	private boolean validateEmail(String email, Long userId) {
		if (userId > 0) {
			return userMasterRepo.countByEmailAndUserIdNot(email, userId) > 0;
		} else {
			return userMasterRepo.countByEmailAndUserId(email, userId) > 0;
		}
	}

	@Override
	public UserDetailsDto getUserDetailsByUserId(UserPrincipal user) {
		ModelMapper m = MapperUtils.getInstance();
		Optional<UserMaster> optionalUserMaster = userMasterRepo.findById(user.getUserId());
		if (optionalUserMaster.isPresent()) {
			UserMaster userMaster = optionalUserMaster.get();
			return m.map(userMaster, UserDetailsDto.class);
		}
		return null;
	}
}
