package com.example.demo.util;

import java.security.Signature;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	// 建議生產環境用環境變數或安全機制注入，不要硬寫
	private final String SECRET_KEY = "nzSNEcBiXKcCi4iASph5KD1+UtghNUw9jrZvKTsEypw=";
	
	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// 產生Token，將 username 放進token的 subject 欄位，並設定過期時間（10小時）
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	// 解析 token 中的 username（subject）
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// 通用解析 Claim 的方法
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// 解析 JWT 的所有 Claims，包含驗證簽名
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey()) 
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	// 判斷 Token 是否過期
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	// 驗證 token 是否有效
	public boolean validateToken(String token, String username) {
		final String tokenUsername = extractUsername(token);
		return (tokenUsername.equals(username) && !isTokenExpired(token));
	}
}