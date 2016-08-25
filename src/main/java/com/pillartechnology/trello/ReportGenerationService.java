package com.pillartechnology.trello;

import com.pillartechnology.trello.entities.TrelloBoard;
import com.pillartechnology.trello.entities.TrelloLabel;

import java.util.*;
import java.util.stream.Collectors;

class ReportGenerationService {

    TrelloService trelloService;


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

        // TODO: Don't forget to add header row before data records
        builder.append("Name,Location,Role,ListName,Stage\n");
        records.forEach(record -> builder.append(record).append("\n"));

        return builder.toString();
    }

    List<ReportRecord> generateReportRecordsFromTrelloBoard(TrelloBoard board) {
        Set<String> labelNames = new HashSet<>();

        List<TrelloLabel> labelList = trelloService.getLabels(board.getId());

        for(TrelloLabel label : labelList){
            labelNames.add(label.getName());
        }

        Map<String,String> idToNameListMap = new HashMap<>();
        board.getLists().forEach(treloList -> {
            idToNameListMap.put(treloList.getId(), treloList.getName());
        });

        List<ReportRecord> records = new ArrayList<>();
        /// TODO: Meri to validate these mappings:

        Set<String> kataListIDs = getListIdsForLists(board, Arrays.asList("Kata Exercise (Polyglot)", "Android/iOS Kata Exercise (ADS)", "Falcon Kata Exercise", "DevOps Kata Exercise", "DevOps Presentation"));
        Set<String> leadershipListIDs = getListIdsForLists(board, Arrays.asList("Leadership Interview", "Fully Vetted", "Offer Pending"));
        Set<String> hiredListIds = getListIdsForLists(board, Arrays.asList("Started @ Pillar", "Offer Accepted"));

        board.getCards().forEach(card -> {
            String listName = idToNameListMap.get(card.getIdList());
            if(! labelNames.contains(card.getName()) && listName != null) {
                ReportRecord reportRecord = card.makeRecord();
                reportRecord.setListName(idToNameListMap.get(card.getIdList()));
                if (kataListIDs.contains(card.getIdList()))
                    reportRecord.setStageKata(true);
                if (leadershipListIDs.contains(card.getIdList()))
                    reportRecord.setStageLeadership(true);
                if (hiredListIds.contains(card.getIdList()))
                    reportRecord.setStageHired(true);
                records.add(reportRecord);
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
