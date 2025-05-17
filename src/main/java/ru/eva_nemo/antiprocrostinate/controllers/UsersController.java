package ru.eva_nemo.antiprocrostinate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eva_nemo.antiprocrostinate.dto.input.user.AuthRequest;
import ru.eva_nemo.antiprocrostinate.dto.input.user.RegisterRequest;
import ru.eva_nemo.antiprocrostinate.dto.output.AuthResponse;
import ru.eva_nemo.antiprocrostinate.service.UserService;
import ru.eva_nemo.antiprocrostinate.utils.jwt.JwtTokenUtils;
import ru.eva_nemo.antiprocrostinate.utils.jwt.UserToken;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
  private final JwtTokenUtils jwtTokenUtils;
  private final UserService userService;

  @PostMapping("/reg")
  public AuthResponse register(@RequestBody RegisterRequest dto) {
    UserToken userToken = userService.createUser(dto);
    log.info("create user with userId:" + userToken.userId().toString());
    return new AuthResponse(jwtTokenUtils.generateToken(userToken));

  }

  @PostMapping("/auth")
  public AuthResponse auth(@RequestBody AuthRequest dto) {
    UserToken userToken = userService.authenticateUser(dto);
    return new AuthResponse(jwtTokenUtils.generateToken(userToken));
  }
}
