package com.example.bookingapp.controller;

import com.example.bookingapp.dto.ErrorResponse;
import com.example.bookingapp.error.EntityNotFoundException;
import com.example.bookingapp.error.IncorrectRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(EntityNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(IncorrectRequestException.class)
    public ResponseEntity<ErrorResponse> incorrectRequest(IncorrectRequestException ex) {

        return ResponseEntity
                .status(400)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> unhandledError(RuntimeException ex) {

        return ResponseEntity
                .status(500)
                .body(new ErrorResponse("Ошибка сервера. Мы уже работаем над ее устранением. Попробуйте повторить запрос позднее"));
    }
}
