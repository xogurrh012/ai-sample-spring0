package com.example.demo._core.handler.exception;

import lombok.Getter;

@Getter
public class Exception400 extends RuntimeException {
    public Exception400(String message) {
        super(message);
    }
}
