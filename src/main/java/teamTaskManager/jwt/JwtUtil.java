package teamTaskManager.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private int expiration;
  public String generateToken(Authentication authentication) {
    UserDetails mainUser = (UserDetails) authentication.getPrincipal();
    SecretKey key        = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    return Jwts.builder().setSubject(mainUser.getUsername())
           .setIssuedAt(new Date())
           .setExpiration(new Date(new Date().getTime() + expiration + 1000L))
           .signWith(key)
           .compact();
  }
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
  public Date extractExpiration(String token) {
    return extractAllClaims(token).getExpiration();
  }
  public Claims extractAllClaims(String token) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }
  public String extractUserName(String token) {
    return extractAllClaims(token).getSubject();
  }
}
