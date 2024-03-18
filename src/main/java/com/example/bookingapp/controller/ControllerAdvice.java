package com.example.bookingapp.controller;

import com.example.bookingapp.dto.ErrorResponse;
import com.example.bookingapp.error.EntityNotFoundException;
import com.example.bookingapp.error.IncorrectRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(EntityNotFoundException ex) {
        log.debug("handles exception with notFound() method");


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(IncorrectRequestException.class)
    public ResponseEntity<ErrorResponse> incorrectRequest(IncorrectRequestException ex) {
        log.debug("handles exception with incorrectRequest() method");

        return ResponseEntity
                .status(400)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDenied(AccessDeniedException ex) {
        log.debug("handles exception with accessDenied() method");

        return ResponseEntity
                .status(400)
                .body(new ErrorResponse("У вас недостаточно прав для данного запроса"));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> unhandledError(RuntimeException ex) {
        log.debug("handles exception with unhandledError() method");

        return ResponseEntity
                .status(500)
                .body(new ErrorResponse("Ошибка сервера. Попробуйте повторить запрос позже"));
    }
}
