package com.example.personTask.exception;

import org.springframework.beans.factory.annotation.Autowired;

public class ExceptionClass extends RuntimeException {
    @Autowired
    public ExceptionClass(String message) {
        super(message);
    }
}
