package com.pillartechnology.trello;

import java.io.PrintStream;

public class ReportClient {

    private PrintStream clientOutput;
    ReportGenerationService reportGenerationService;
    TrelloProperties props = TrelloProperties.getInstance();

    public ReportClient(PrintStream output){
        this.clientOutput = output;
    }

    public static void main(String[] args) {
        ReportClient client = new ReportClient(System.out);
        client.run(args);
    }

    void run(String[] args) {
        if (args.length != 1) {
            clientOutput.println("Usage: TrelloClientReport propertyFile");
            return;
        }

        props.loadPropertiesFromFile(args[0]);
        //props.loadPropertiesFromFile("TalentManagement.bak.properties");

        if (reportGenerationService == null) {
            reportGenerationService = new ReportGenerationService();
        }

        String reportOutput = reportGenerationService.generateReport();
        System.out.println(reportOutput);
    }
}
