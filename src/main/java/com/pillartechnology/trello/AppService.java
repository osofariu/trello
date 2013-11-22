package com.pillartechnology.trello;

import com.pillartechnology.trello.exception.AppServiceException;

import java.io.File;

public interface AppService {
    void importFile(File inFile, String boardName) throws AppServiceException;
}
