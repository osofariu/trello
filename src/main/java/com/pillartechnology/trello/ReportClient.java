package com.pillartechnology.trello;

import java.io.PrintStream;

public class ReportClient {

    private PrintStream clientOutput;
    ReportGenerationService trelloReportService;

    public ReportClient(PrintStream output){
        this.clientOutput = output;
    }

    public static void main(String[] args) {
        ReportClient client = new ReportClient(System.out);
        client.run(args);
    }

    void run(String[] args) {
        if (args.length != 3) {
            clientOutput.println("Usage: TrelloClientReport boardId appKey appToken");
            return;
        }

        if (trelloReportService == null) {
            trelloReportService = new ReportGenerationService(args[1], args[2]);
        }

        String reportOutput = trelloReportService.generateReport(args[0]);
        System.out.println(reportOutput);
    }
}
