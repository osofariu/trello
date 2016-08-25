package com.pillartechnology.trello;

import java.io.PrintStream;

public class TrelloReportClient {

    private PrintStream clientOutput;
    TrelloReportRecordService trelloReportService;

    public TrelloReportClient(PrintStream output){
        this.clientOutput = output;
    }

    public static void main(String[] args) {
        TrelloReportClient client = new TrelloReportClient(System.out);
        client.run(args);
    }

    void run(String[] args) {
        if (args.length != 3) {
            clientOutput.println("Usage: TrelloClientReport boardId appKey appToken");
            return;
        }

        if (trelloReportService == null) {
            trelloReportService = new TrelloReportRecordService(args[1], args[2]);
        }

        String reportOutput = trelloReportService.generateReport(args[0]);
        System.out.println(reportOutput);
    }
}
