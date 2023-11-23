package com.safetynet.alerts.exception;

public class UnreachableDatabaseException extends RuntimeException{
    public UnreachableDatabaseException(String message) {
        super(message);
    }
}
