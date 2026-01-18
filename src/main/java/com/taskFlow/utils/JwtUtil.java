package com.taskFlow.utils;

import com.taskFlow.constants.DBFields;
import com.taskFlow.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
  @Value("${jwt.secret.key}")
  private String SECRET_KEY;

  public String generateToken(UserDetails userDetails) {
    User user = (User) userDetails;
    Map<String, Object> claims = new HashMap<>();
    claims.put(DBFields.COMPANY_ID, user.getCompanyId());
    claims.put(DBFields.DEPARTMENT_ID, user.getDepartmentId());
    claims.put(DBFields.USER_TYPE,user.getUserType());
    return createToken(claims, userDetails.getUsername()); // here this is loginId
  }

  private String createToken(Map<String, Object> claims, String subject) {
    long JWT_EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
