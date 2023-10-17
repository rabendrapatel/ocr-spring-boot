package com.source.config.jwt;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.source.enums.TokenErrorType;
import com.source.response.ResponseRes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	ObjectMapper objectMapper = new ObjectMapper();

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationMin}")
	private int jwtExpirationMin;

	public String generateToken(Authentication authentication,String userType) {

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(jwtExpirationMin);

		return Jwts.builder()
				.setAudience(userType)
				.setSubject(Long.toString(userPrincipal.getUserId()))
				.setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(Date.from(expiryDate.atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String generateTempToken(String userName, String pass) {
		return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(userName)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).claim("Role", "Admin")
				.claim("abc", "asds").claim("asasas", "sdsad").compact();
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}
	
	public String getUserTypeFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getAudience();
	}

	public String validateToken(String authToken, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return TokenErrorType.TOKEN_VALID.name();
		} catch (io.jsonwebtoken.SignatureException ex) {
			ResponseRes<Object> errorResponse = new ResponseRes<>(1100, "FAILURE", "Invalid JWT signature");
			response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		} catch (MalformedJwtException ex) {
			ResponseRes<Object> errorResponse = new ResponseRes<>(1100, "FAILURE", "Invalid JWT token");
			response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
			return TokenErrorType.TOKEN_INVALID.name();
		} catch (ExpiredJwtException ex) {
			ResponseRes<Object> errorResponse = new ResponseRes<>(1100, "FAILURE", "Session expired, Please login again");
			response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
			return TokenErrorType.TOKEN_EXPIRE.name();
		} catch (UnsupportedJwtException ex) {
			return TokenErrorType.TOKEN_INVALID.name();
		} catch (IllegalArgumentException ex) {
			return TokenErrorType.TOKEN_INVALID.name();
		}
		return TokenErrorType.TOKEN_INVALID.name();
	}

	public String validateToken(String authToken) throws IOException {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return TokenErrorType.TOKEN_VALID.name();
		} catch (io.jsonwebtoken.SignatureException ex) {
		} catch (MalformedJwtException ex) {
			return TokenErrorType.TOKEN_INVALID.name();
		} catch (ExpiredJwtException ex) {
			return TokenErrorType.TOKEN_EXPIRE.name();
		} catch (UnsupportedJwtException ex) {
			return TokenErrorType.TOKEN_INVALID.name();
		} catch (IllegalArgumentException ex) {
			return TokenErrorType.TOKEN_INVALID.name();
		}
		return TokenErrorType.TOKEN_INVALID.name();
	}
}
