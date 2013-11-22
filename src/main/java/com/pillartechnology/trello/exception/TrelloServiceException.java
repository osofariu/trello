package com.pillartechnology.trello.exception;

public abstract class TrelloServiceException extends RuntimeException {
    public TrelloServiceException(String errorDescription) {
        super(errorDescription);
    }
}
