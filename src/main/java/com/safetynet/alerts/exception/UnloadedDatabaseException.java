package com.safetynet.alerts.exception;

public class UnloadedDatabaseException extends RuntimeException{
    public UnloadedDatabaseException(String message) {
        super(message);
        printStackTrace();
    }
}