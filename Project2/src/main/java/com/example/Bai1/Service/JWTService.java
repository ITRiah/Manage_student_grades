package com.example.Bai1.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {
	@Value("${jwt.secret:1233333333333333333333333333333333333333333333333333333}") //mặc định là 111...... nếu trong file application không có.
	
	private String secretKey;
	private long timeEnd = 5; //5 minutes
	
	//Gen token
	public String create(String username) {
		Claims claims = Jwts.claims().setSubject(username);
		//claims.put(username, claims)
		Date now = new Date();
		Date exp = new Date(now.getTime()+ timeEnd * 60 * 1000);
		
		return Jwts.builder().setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(exp)
					.signWith(SignatureAlgorithm.HS256, secretKey)
					.compact();
	}
	
	//check hiệu lực và độ chính xác của token
	public boolean isValidToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); //jws not jwt
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	//Lấy subject của token
	public String getUserName(String token) {
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		} catch (Exception e) {
			
		}
		return null;
	}
}
