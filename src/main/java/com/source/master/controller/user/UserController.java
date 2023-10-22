package com.source.master.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.source.config.jwt.CurrentUser;
import com.source.config.jwt.UserPrincipal;
import com.source.master.dto.user.UserDetailsDto;
import com.source.master.dto.user.UserListDto;
import com.source.master.dto.user.UserReqDto;
import com.source.master.service.user.UserService;
import com.source.response.ResponseRes;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/master/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/list")
	public ResponseEntity<?> getUserList(
			@PageableDefault(sort = "userId", direction = Sort.Direction.DESC, size = Integer.MAX_VALUE) Pageable pageable,
			@RequestBody UserReqDto req, @CurrentUser UserPrincipal user) {
		try {
			Page<UserListDto> list = userService.getUserList(pageable, req, user);

			if (!list.isEmpty()) {
				return ResponseEntity
						.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Record found", list));
			} else {
				return ResponseEntity
						.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "No record found", list));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody UserReqDto req, @CurrentUser UserPrincipal user) {
		try {
			req = userService.saveUser(req, user);
			return ResponseEntity
					.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Saved Successfully", req));
		} catch (RuntimeException e) {
			return ResponseEntity.ok(
					new ResponseRes<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody UserReqDto req, @CurrentUser UserPrincipal user) {
		try {
			req = userService.updateUser(req, user);
			return ResponseEntity
					.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Updated Successfully", req));
		} catch (RuntimeException e) {
			return ResponseEntity.ok(
					new ResponseRes<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}

	@GetMapping("/get/user-details")
	public ResponseEntity<?> getUserDetailsByUserId(@CurrentUser UserPrincipal user) {
		try {
			UserDetailsDto res = userService.getUserDetailsByUserId(user);

			if (res != null) {
				return ResponseEntity
						.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Record found", res));
			} else {
				return ResponseEntity
						.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "No record found", res));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}
}
