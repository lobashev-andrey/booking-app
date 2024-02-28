package com.example.bookingapp.error;

public class IncorrectRequestException extends RuntimeException{

    public IncorrectRequestException(String message) {
        super(message);
    }
}
