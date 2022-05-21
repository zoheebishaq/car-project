package com.example.carproject.exception;

import org.springframework.http.HttpStatus;

public class CarProjectAPIException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public CarProjectAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public CarProjectAPIException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
