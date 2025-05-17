package ru.eva_nemo.antiprocrostinate.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@Setter
public class JirAuthentication implements Authentication {
  private String username;
  @Getter
  private UUID userId;
  @Getter
  private String token;
  private boolean isAuthenticated;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return username;
  }

  @Override
  public boolean isAuthenticated() {
    return this.isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.isAuthenticated = isAuthenticated;
  }


  @Override
  public String getName() {
    return username;
  }


}
