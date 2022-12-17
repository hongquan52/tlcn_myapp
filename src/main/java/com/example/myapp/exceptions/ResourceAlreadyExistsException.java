package com.example.myapp.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException{

  public ResourceAlreadyExistsException(String message) {
    super(message);
  }
}
