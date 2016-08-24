package com.pillartechnology.trello;

import java.io.OutputStream;

public class TrelloReportClient {

    private TrelloReportService trelloService;

    public TrelloReportClient(TrelloReportService trelloService, OutputStream output){
        this.trelloService = trelloService;
    }

    public static void main(String[] args) {
        TrelloReportClient client = new TrelloReportClient(new TrelloReportService(), System.out);
        client.run(args);
    }

    public void run(String[] args) {
        trelloService.getBoard();
    }
}
