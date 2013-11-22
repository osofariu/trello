package com.pillartechnology.trello.exception;

public class AppServiceException extends RuntimeException {
    public AppServiceException(Throwable tse) {
        super(tse);
    }
}
