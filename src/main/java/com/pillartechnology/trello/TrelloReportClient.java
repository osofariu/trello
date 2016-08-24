package com.pillartechnology.trello;

import java.io.PrintStream;

public class TrelloReportClient {

    private TrelloReportService trelloService;
    private PrintStream clientOutput;

    public TrelloReportClient(TrelloReportService trelloService, PrintStream output){
        this.trelloService = trelloService;
        this.clientOutput = output;
    }

    public static void main(String[] args) {
        TrelloReportClient client = new TrelloReportClient(new TrelloReportService(), System.out);
        client.run(args);
    }

    void run(String[] args) {
        if (args.length != 3) {
            clientOutput.println("Usage: TrelloClientReport boardId appKey appToken");
            return;
        }

        TrelloBoard trelloBoard = trelloService.getBoard(args[0], args[1], args[2]);
        generateTrelloCardsFromBoard(trelloBoard);
    }

    private void generateTrelloCardsFromBoard(TrelloBoard trelloBoard){

        trelloBoard.getCards().forEach(trelloCard -> {
            System.out.println(trelloCard.makeRecord());
        });
    }
}
