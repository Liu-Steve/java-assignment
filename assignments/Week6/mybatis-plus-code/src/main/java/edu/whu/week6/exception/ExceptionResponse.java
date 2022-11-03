package edu.whu.week6.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {

    private String message;
    private LocalDateTime dateTime;

    public ExceptionResponse(Exception e) {
        setMessage(e.getMessage());
        setDateTime(LocalDateTime.now());
    }

}
