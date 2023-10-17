package com.source.master.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.source.master.dto.user.UserListDto;
import com.source.master.dto.user.UserReqDto;
import com.source.master.service.user.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/master/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/list")
	public ResponseEntity<?> getInvoiceList(
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = Integer.MAX_VALUE) Pageable pageable,
			@RequestBody UserReqDto req ) {
		try {
			Page<UserListDto> list = userService.getUserList(pageable,req);

			if (list.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No record found.");
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody UserReqDto req) {
		try {
			req = userService.saveUser(req);
			if (req!=null) {
				return new ResponseEntity<>("Invoice uploaded successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Invoice upload failed - ", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Invoice upload failed - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserReqDto req) {
		try {
			req = userService.registerUser(req);
			if (req!=null) {
				return new ResponseEntity<>("Invoice uploaded successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Invoice upload failed - ", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Invoice upload failed - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
