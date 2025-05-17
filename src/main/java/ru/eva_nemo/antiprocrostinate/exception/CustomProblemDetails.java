package ru.eva_nemo.antiprocrostinate.exception;

import lombok.Builder;

@Builder
public record CustomProblemDetails(String title, int status, String details, String path) {

}
