package com.source.tran.controller.auth;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.source.config.jwt.JwtTokenProvider;
import com.source.master.dto.user.UserReqDto;
import com.source.master.entity.user.UserMaster;
import com.source.master.service.user.UserService;
import com.source.response.JwtAuthenticationResponse;
import com.source.response.ResponseRes;

@RestController
@RequestMapping(value = "/api/tran/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/login/via/username/password")
	public ResponseEntity<?> authenticateUserViaPassword(@RequestBody UserReqDto req, HttpServletRequest request) {

		try {
			UserMaster user = userService.getByUserName(req.getUserName());
			if (!Optional.ofNullable(user).isPresent()) {
				return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(),
						HttpStatus.UNAUTHORIZED.name(), "Wrong user name"));
			}

			if (!bCryptPasswordEncoder.matches(req.getPassword(), user.getPassword())) {
				return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(),
						HttpStatus.UNAUTHORIZED.name(), "Wrong password"));
			}

			if (user.getStatus() != 1) {
				return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(),
						HttpStatus.UNAUTHORIZED.name(), "User is inactive"));
			}

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(req.getUserName(), req.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtTolen = tokenProvider.generateToken(authentication, "user");

			JwtAuthenticationResponse athenticationResponse = new JwtAuthenticationResponse(jwtTolen);

			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Login successfull",
					athenticationResponse));

		} catch (BadCredentialsException e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
					"Invalid access from " + req.getUserName()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}

}
