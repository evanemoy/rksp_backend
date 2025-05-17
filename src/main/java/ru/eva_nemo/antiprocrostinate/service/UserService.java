package ru.eva_nemo.antiprocrostinate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.eva_nemo.antiprocrostinate.dto.input.user.AuthRequest;
import ru.eva_nemo.antiprocrostinate.dto.input.user.RegisterRequest;
import ru.eva_nemo.antiprocrostinate.exception.UnauthorizedException;
import ru.eva_nemo.antiprocrostinate.exception.UserAlreadyExistException;
import ru.eva_nemo.antiprocrostinate.models.UserEntity;
import ru.eva_nemo.antiprocrostinate.repository.UserRepository;
import ru.eva_nemo.antiprocrostinate.utils.jwt.UserToken;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserToken createUser(RegisterRequest dto) {
        checkExist(dto.username());
        var userEntity = UserEntity.builder()
            .username(dto.username())
            .email(dto.email())
            .passwordHash(passwordEncoder.encode(dto.password()))
            .build();

        userEntity = userRepository.save(userEntity);
        return new UserToken(userEntity.getUsername(), userEntity.getId());
    }

    private void checkExist(String username) {
        userRepository.findByUsername(username)
            .ifPresent((v) -> {
                throw new UserAlreadyExistException("user with such username already exist");
            });
    }

    public UserToken authenticateUser(AuthRequest dto) {
        var userEntity = userRepository.findByUsername(dto.username())
            .orElseThrow(
                () -> new UnauthorizedException("no user with username: " + dto.username())
            );
        if (!passwordEncoder.matches(dto.password(), userEntity.getPasswordHash())) {
            throw new UnauthorizedException("wrong password");
        }
        return new UserToken(dto.username(), userEntity.getId());
    }
}
