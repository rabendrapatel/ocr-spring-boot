package com.source.tran.controller.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/tran/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/login/via/user/id")
	public ResponseEntity<?> authenticateUserViaUserId(@RequestBody UserReqDto req, HttpServletRequest request) {

		try {
			UserMaster user = userService.findByUserId(req.getUserId());
			if (user == null) {
				return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(),
						HttpStatus.UNAUTHORIZED.name(), "Wrong user id"));
			}

			if (user.getStatus() != 1) {
				return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(),
						HttpStatus.UNAUTHORIZED.name(), "User is inactive"));
			}

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					user.getUserName(), null);
			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtToken = tokenProvider.generateToken(authentication, "user");

			JwtAuthenticationResponse authenticationResponse = new JwtAuthenticationResponse(jwtToken);

			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
					"Login successful", authenticationResponse));

		} catch (BadCredentialsException e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
					"Invalid access from " + req.getUserId()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
					"Internal server error " + e.getMessage()));
		}
	}

}
