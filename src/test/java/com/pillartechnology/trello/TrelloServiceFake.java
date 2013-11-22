package com.pillartechnology.trello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrelloServiceFake implements TrelloService {
    Map<String, List<String>> boards = new HashMap<String, List<String>>();

    @Override
    public boolean createBoard(String boardName) {
        boards.put(boardName, new ArrayList<String>());
        return true;
    }

    @Override
    public boolean addTask(String boardName, String taskName) {
        List<String> tasks = boards.get(boardName);
        tasks.add(taskName);
        return true;
    }

    public List<String> board(String boardName) {
        return boards.get(boardName);
    }
}
