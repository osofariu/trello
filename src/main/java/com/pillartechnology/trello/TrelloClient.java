package com.pillartechnology.trello;

import com.pillartechnology.trello.exception.AppServiceException;

import java.io.File;
import java.io.PrintStream;

public class TrelloClient {
    private AppService appService;

    private PrintStream out;

    public static void main(String[] args) {
        TrelloService trelloService = new TrelloServiceImpl("/Users/ovi/.trello");
        AppService appService = new AppServiceImpl(trelloService);
        TrelloClient client = new TrelloClient(appService, System.out);
        client.run(args);
    }

    public TrelloClient(AppService appService, PrintStream out) {
        this.appService = appService;
        this.out = out;
    }

    public void run(String[] args) {
        if (args.length <  2) {
            out.println("Usage: TrelloClient <fileName> <boardName>");
            return;
        }
        File inFile = new File(args[0]);
        if (!inFile.isFile()) {
            out.println("File not found");
            return;
        }
        try {
            appService.importFile(inFile, args[1]);
            out.println("File " + inFile.toString() + " was imported");
        } catch (AppServiceException ase) {
            out.println("AppServiceException: " + ase.getMessage());
        }
    }
}
