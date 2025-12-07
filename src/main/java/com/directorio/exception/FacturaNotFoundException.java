package com.directorio.exception;

public class FacturaNotFoundException extends RuntimeException {
    public FacturaNotFoundException(String message) {
        super(message);
    }
}
