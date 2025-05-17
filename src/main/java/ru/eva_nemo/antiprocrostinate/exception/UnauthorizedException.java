package ru.eva_nemo.antiprocrostinate.exception;

public class UnauthorizedException extends RuntimeException{
  public UnauthorizedException(String message){
    super(message);
  }
}
