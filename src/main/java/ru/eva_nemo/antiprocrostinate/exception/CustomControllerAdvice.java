package ru.eva_nemo.antiprocrostinate.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomProblemDetails> handleException(
      Exception ex,
      HttpServletRequest request) {
    int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("internal error")
        .details(ex.getMessage())
        .path(request.getMethod() + " " + request.getRequestURI())
        .build();
    return ResponseEntity.status(status)
        .body(customProblemDetails);
  }


  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<CustomProblemDetails> handleUnauthorizedException(
      UnauthorizedException ex,
      HttpServletRequest request) {
    int status = HttpStatus.UNAUTHORIZED.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("Error authorizing")
        .details(ex.getMessage())
        .path(request.getMethod() + " " +request.getRequestURI())
        .build();
    return ResponseEntity.status(status).body(customProblemDetails);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<CustomProblemDetails> handleNotFoundException(
      NotFoundException ex,
      HttpServletRequest request) {
    int status = HttpStatus.NOT_FOUND.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("Not found")
        .details(ex.getMessage())
        .path(request.getMethod() + " " +request.getRequestURI())
        .build();
    return ResponseEntity.status(status).body(customProblemDetails);
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<CustomProblemDetails> handleUserAlreadyExist(
      UserAlreadyExistException ex,
      HttpServletRequest request) {
    int status = HttpStatus.CONFLICT.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("User already exist")
        .details(ex.getMessage())
        .path(request.getMethod() + " " + request.getRequestURI())
        .build();
    return ResponseEntity.status(status)
        .body(customProblemDetails);
  }
}
