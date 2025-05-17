package ru.eva_nemo.antiprocrostinate.exception;


public class NotFoundException extends RuntimeException{
  public NotFoundException(String message){
    super(message);
  }
}
