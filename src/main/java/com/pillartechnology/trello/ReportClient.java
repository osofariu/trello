package com.pillartechnology.trello;

import java.io.PrintStream;

public class ReportClient {

    private PrintStream clientOutput;
    ReportGenerationService reportGenerationService;

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

        if (reportGenerationService == null) {
            reportGenerationService = new ReportGenerationService(args[1], args[2]);
        }

        String reportOutput = reportGenerationService.generateReport(args[0]);
        System.out.println(reportOutput);
    }
}
