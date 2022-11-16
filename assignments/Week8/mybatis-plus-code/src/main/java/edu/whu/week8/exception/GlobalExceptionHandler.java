package edu.whu.week8.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConflictException.class})
    protected ResponseEntity<Object> handleConflictException(ConflictException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(exception);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(exception);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SqlExecuteException.class, NotSupportArgumentException.class, AuthenticationException.class})
    protected ResponseEntity<Object> handleSqlException(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataAccessException.class})
    protected ResponseEntity<Object> handleGeneralException(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
