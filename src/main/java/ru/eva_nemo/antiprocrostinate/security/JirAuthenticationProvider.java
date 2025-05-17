package ru.eva_nemo.antiprocrostinate.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.eva_nemo.antiprocrostinate.utils.jwt.JwtTokenUtils;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class JirAuthenticationProvider implements AuthenticationProvider {
  private final JwtTokenUtils jwtTokenUtils;
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    JirAuthentication jirAuthentication = (JirAuthentication) authentication;
    try{
      Claims claims = jwtTokenUtils.parseToken(jirAuthentication.getToken());
      if (claims.getSubject()!=null){
        jirAuthentication.setUserId(UUID.fromString(claims.get("userId").toString()));
        jirAuthentication.setUsername(claims.getSubject());
        jirAuthentication.setAuthenticated(true);
      }
    }catch(JwtException exception){
      jirAuthentication.setAuthenticated(false);
    }
    return jirAuthentication;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(JirAuthentication.class);
  }
}
