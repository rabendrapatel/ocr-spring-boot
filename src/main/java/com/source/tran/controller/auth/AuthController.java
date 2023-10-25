package com.source.tran.controller.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.source.config.jwt.JwtTokenProvider;
import com.source.master.dto.user.UserReqDto;
import com.source.master.entity.user.UserMaster;
import com.source.master.service.user.AuthService;
import com.source.master.service.user.UserService;
import com.source.response.JwtAuthenticationResponse;
import com.source.response.ResponseRes;

@RestController
@RequestMapping(value = "/api/tran/auth")
public class AuthController {

	@Autowired
	AuthService authService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/login/via/username/password")
	public ResponseEntity<?> authenticateUserViaPassword(@RequestBody UserReqDto req) {

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
			
			if (user.getIsEmailVerify().equalsIgnoreCase("no")) {
				return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(),
						HttpStatus.UNAUTHORIZED.name(), "Email not verified, Please verify to continue"));
			}

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), req.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtTolen = tokenProvider.generateToken(authentication, "user");

			JwtAuthenticationResponse athenticationResponse = new JwtAuthenticationResponse(jwtTolen);

			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Login successfull",
					athenticationResponse));

		} catch (BadCredentialsException e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
					"Invalid access from " + req.getUserName()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}

	@PostMapping("/register/new/user")
	public ResponseEntity<?> registerUser(@RequestBody UserReqDto req) {
		try {
			req = userService.registerUser(req);
			return ResponseEntity
					.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Registration successful. Please verify your email; a link has been sent to your registered email", req));
		} catch (RuntimeException e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
					e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}
	
	@ResponseBody
	@GetMapping("/verify/email")
	public ResponseEntity<String> verifyEmail(@RequestParam String token) {
	    try {
	        String res = authService.verifyEmail(token);
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Content-Type", "text/html");
	        return new ResponseEntity<>(res, headers, HttpStatus.OK);
	    } catch (RuntimeException e) {
	        String errorHtml = "<html><body><h1>Error</h1><p>" + e.getMessage() + "</p></body></html>";
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Content-Type", "text/html");
	        return new ResponseEntity<>(errorHtml, headers, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        String internalServerErrorHtml = "<html><body><h1>Internal Server Error</h1><p>Internal server error: " + e.getMessage() + "</p></body></html>";
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Content-Type", "text/html");
	        return new ResponseEntity<>(internalServerErrorHtml, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PostMapping("/resend/email/verification/email")
	public ResponseEntity<?> resendverificationEmail(@RequestBody UserReqDto req) {
		try {
			req = authService.resendVerificationEmail(req);
			return ResponseEntity
					.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Sent successfully", req));
		} catch (RuntimeException e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
					e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}
	
	@PostMapping("/send/reste/password/email")
	public ResponseEntity<?> sendResetPasswordEmail(@RequestBody UserReqDto req) {
		try {
			req = authService.sendResetPasswordEmail(req);
			return ResponseEntity
					.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Password reset email sent successfully", req));
		} catch (RuntimeException e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
					e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}
	
	@PostMapping("/reset/password")
	public ResponseEntity<?> resetPassword(@RequestBody UserReqDto req) {
		try {
			req = authService.resetPassword(req);
			return ResponseEntity
					.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Password changed successfully", req));
		} catch (RuntimeException e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
					e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}
	
	

}
