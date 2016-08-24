package com.pillartechnology.trello.exception;

public class TrelloServiceException extends RuntimeException {
    public TrelloServiceException(String errorDescription) {
        super(errorDescription);
    }
}
