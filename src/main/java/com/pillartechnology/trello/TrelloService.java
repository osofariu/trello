package com.pillartechnology.trello;

import com.pillartechnology.trello.exception.TrelloServiceException;

public interface TrelloService {
    boolean createBoard(String boardName) throws TrelloServiceException;
    boolean addTask(String boardName, String taskName);
}
