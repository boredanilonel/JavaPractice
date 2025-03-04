package com.example.petstore.exception;

import org.springframework.beans.factory.annotation.Autowired;

public class NotFoundException extends RuntimeException {
    @Autowired
    public NotFoundException(String message) {
        super(message);
    }
}
