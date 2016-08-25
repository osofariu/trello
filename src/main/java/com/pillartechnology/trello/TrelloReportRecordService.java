package com.pillartechnology.trello;

import java.util.*;

class TrelloReportRecordService {

    TrelloReportService trelloReportService;

    /// TODO: Meri to validate these mappings:
    //List<String> leadershipColumns = Arrays.asList("Leadership Interview", "Fully Vetted", "Offer Pending");
    //List<String> hiredColumns = Arrays.asList("Started @ Pillar", "Offer Accepted");

    TrelloReportRecordService(String appKey, String appToken) {
        trelloReportService = new TrelloReportService(appKey, appToken);
    }

    String generateReport(String boardId)  {
        TrelloBoard trelloBoard = trelloReportService.getBoard(boardId);
        List<TrelloReportRecord> records = generateReportRecordsFromTrelloBoard(trelloBoard);

        return generateOutputFromRecords(records);
    }

    private String generateOutputFromRecords(List<TrelloReportRecord> records) {
        StringBuilder builder = new StringBuilder();

        // TODO: Don't foget to add header row before data records
        records.forEach(record -> builder.append(record).append("\n"));

        return builder.toString();
    }

    List<TrelloReportRecord> generateReportRecordsFromTrelloBoard(TrelloBoard board) {
        Set<String> labelNames = new HashSet<>();

        List<TrelloLabel> labelList = trelloReportService.getLabels(board.getId());

        for(TrelloLabel label : labelList){
            labelNames.add(label.getName());
        }

        List<TrelloReportRecord> records = new ArrayList<>();
        Set<String> kataListIDs = getListIdsForLists(board, Arrays.asList("Kata Exercise (Polyglot)", "Android/iOS Kata Exercise (ADS)", "Falcon Kata Exercise", "DevOps Kata Exercise", "DevOps Presentation"));

        board.getCards().forEach(card -> {
            if(! labelNames.contains(card.getName())){
                TrelloReportRecord cardRecord = card.makeRecord();
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
