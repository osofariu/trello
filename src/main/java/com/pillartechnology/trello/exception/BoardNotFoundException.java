package com.pillartechnology.trello.exception;

public class BoardNotFoundException extends TrelloServiceException {

    public BoardNotFoundException(String boardName) {
        super("Board not found: " + boardName);
    }
}
