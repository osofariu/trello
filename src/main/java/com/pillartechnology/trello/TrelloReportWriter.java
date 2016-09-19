package com.pillartechnology.trello;

import com.pillartechnology.trello.exception.TrelloServiceException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TrelloReportWriter {

    private TrelloProperties properties = TrelloProperties.getInstance();

    public void writeContentToFile(String content, String filename){
        BufferedWriter writer = null;

        try{
            writer = new BufferedWriter(new FileWriter(filename));
            writer.append(content);
            writer.flush();
        } catch (IOException e) {
            throw new TrelloServiceException(e.getMessage());
        } finally {
            try {
                writer.close();
            } catch(IOException e){
                //Do Nothing
            }
        }
    }

}
