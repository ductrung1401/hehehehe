package com.trungtd.custom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse NotFoundExceptionHandler(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        System.out.println(errorResponse);
        return errorResponse;
        //return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> UnwantedExceptionHandler(Exception e) {
        // Log lỗi ra và ẩn đi message thực sự
        // Thực tế dùng logger
        e.printStackTrace();
        return ResponseEntity.status(500).body("Unknow error");
    }
}
