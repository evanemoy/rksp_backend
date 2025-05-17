package ru.eva_nemo.antiprocrostinate.utils.jwt;

import java.util.UUID;

public record UserToken(String username, UUID userId) {
}
