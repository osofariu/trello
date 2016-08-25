package com.pillartechnology.trello;

import com.pillartechnology.trello.entities.TrelloBoard;
import com.pillartechnology.trello.entities.TrelloLabel;

import java.util.*;

class ReportGenerationService {

    TrelloService trelloService;

    /// TODO: Meri to validate these mappings:
    //List<String> leadershipColumns = Arrays.asList("Leadership Interview", "Fully Vetted", "Offer Pending");
    //List<String> hiredColumns = Arrays.asList("Started @ Pillar", "Offer Accepted");

    ReportGenerationService(String appKey, String appToken) {
        trelloService = new TrelloService(appKey, appToken);
    }

    String generateReport(String boardId)  {
        TrelloBoard trelloBoard = trelloService.getBoard(boardId);
        List<ReportRecord> records = generateReportRecordsFromTrelloBoard(trelloBoard);

        return generateOutputFromRecords(records);
    }

    private String generateOutputFromRecords(List<ReportRecord> records) {
        StringBuilder builder = new StringBuilder();

        // TODO: Don't foget to add header row before data records
        records.forEach(record -> builder.append(record).append("\n"));

        return builder.toString();
    }

    List<ReportRecord> generateReportRecordsFromTrelloBoard(TrelloBoard board) {
        Set<String> labelNames = new HashSet<>();

        List<TrelloLabel> labelList = trelloService.getLabels(board.getId());

        for(TrelloLabel label : labelList){
            labelNames.add(label.getName());
        }

        List<ReportRecord> records = new ArrayList<>();
        Set<String> kataListIDs = getListIdsForLists(board, Arrays.asList("Kata Exercise (Polyglot)", "Android/iOS Kata Exercise (ADS)", "Falcon Kata Exercise", "DevOps Kata Exercise", "DevOps Presentation"));

        board.getCards().forEach(card -> {
            if(! labelNames.contains(card.getName())){
                ReportRecord cardRecord = card.makeRecord();
                if (kataListIDs.contains(card.getIdList()))
                    cardRecord.setStageKata(true);
                records.add(cardRecord);
            }
        });

        return records;
    }

    private Set<String> getListIdsForLists(TrelloBoard board, List<String> listNames) {
        Set<String> listIDs = new HashSet<>();
        board.getLists().forEach(trelloList -> {
            if (listNames.contains(trelloList.getName()))
                listIDs.add(trelloList.getId());
        });
        return listIDs;
    }
}
