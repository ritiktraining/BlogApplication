package com.springboot.blog.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.exception.BlogApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private Integer jwtExpirationInMs;

	// generate Token
	public String generateToken(Authentication authentication) {

		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expireDate)
				.signWith(SignatureAlgorithm.ES512, jwtSecret).compact();
		return token;

	}

	// get username from the token
	public String getUserNameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	// validate JWT Token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Signature");
		} catch (MalformedJwtException e) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
		} catch (ExpiredJwtException e) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
		} catch (UnsupportedJwtException e) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
		} catch (IllegalArgumentException e) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
		}
	}
}
