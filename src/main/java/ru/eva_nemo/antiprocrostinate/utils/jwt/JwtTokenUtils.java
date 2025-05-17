package ru.eva_nemo.antiprocrostinate.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtils {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String generateToken(UserToken userToken) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userToken.userId());
    return createToken(claims, userToken.username());
  }


  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSigningKey())
        .compact();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public String extractUsername(String token) {
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
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public Claims parseToken(String token) throws JwtException {
    try {
      return Jwts.parser()
          .verifyWith(getSigningKey())
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (SignatureException e) {
      throw new JwtException("Invalid JWT signature");
    } catch (MalformedJwtException e) {
      throw new JwtException("Invalid JWT token");
    } catch (ExpiredJwtException e) {
      throw new JwtException("JWT token is expired");
    } catch (UnsupportedJwtException e) {
      throw new JwtException("JWT token is unsupported");
    } catch (IllegalArgumentException e) {
      throw new JwtException("JWT claims string is empty");
    }
  }
}