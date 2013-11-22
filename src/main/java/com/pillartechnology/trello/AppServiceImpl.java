package com.pillartechnology.trello;

import com.pillartechnology.trello.exception.AppServiceException;
import com.pillartechnology.trello.exception.TrelloServiceException;

import java.io.*;

public class AppServiceImpl implements AppService {

    TrelloService trelloService;

    public AppServiceImpl(TrelloService trelloService) {
        this.trelloService = trelloService;
    }

    @Override
    public void importFile(File inFile, String boardName) throws AppServiceException {
        try {
            trelloService.createBoard(boardName);
            BufferedReader reader = new BufferedReader(new FileReader(inFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() > 0) {
                     trelloService.addTask(boardName, line);
                }
            }
        } catch (TrelloServiceException tse) {
            throw new AppServiceException(tse);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }
}
