package ru.eva_nemo.antiprocrostinate.utils.security;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.eva_nemo.antiprocrostinate.security.JirAuthentication;

public class SecurityContextHolderUtils {
  public static JirAuthentication getUser(){
    return (JirAuthentication) SecurityContextHolder.getContext().getAuthentication();
  }
}
