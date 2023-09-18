package com.jv.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jv.dscatalog.services.exceptions.DatabaseException;
import com.jv.dscatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandardError> entityNotFound(
    ResourceNotFoundException error, 
    HttpServletRequest request
  ) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    StandardError standardError = new StandardError();
    standardError.setTimestamp(Instant.now());
    standardError.setStatus(status.value());
    standardError.setError("Resource not found");
    standardError.setMessage(error.getMessage());
    standardError.setPath(request.getRequestURI());
    return ResponseEntity.status(status).body(standardError);
  }

    @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<StandardError> database(
    DatabaseException error, 
    HttpServletRequest request
  ) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    StandardError standardError = new StandardError();
    standardError.setTimestamp(Instant.now());
    standardError.setStatus(status.value());
    standardError.setError("Database Exception");
    standardError.setMessage(error.getMessage());
    standardError.setPath(request.getRequestURI());
    return ResponseEntity.status(status).body(standardError);
  }
}
