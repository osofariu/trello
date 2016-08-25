package com.pillartechnology.trello;

import com.pillartechnology.trello.entities.TrelloBoard;
import com.pillartechnology.trello.entities.TrelloCard;
import com.pillartechnology.trello.entities.TrelloLabel;

import java.util.*;
import java.util.stream.Collectors;

class ReportGenerationService {

    TrelloService trelloService;

    private static final List<String> listsForKataStage = Arrays.asList("Kata Exercise (Polyglot)", "Android/iOS Kata Exercise (ADS)", "Falcon Kata Exercise", "DevOps Kata Exercise", "DevOps Presentation");
    private static final List<String> listsForLeadershipStage =  Arrays.asList("Leadership Interview", "Fully Vetted", "Offer Pending");
    private static final List<String> listsForHiredStage = Arrays.asList("Started @ Pillar", "Offer Accepted");

    ReportGenerationService(String appKey, String appToken) {
        trelloService = new TrelloService(appKey, appToken);
    }

    String generateReport(String boardId)  {
        TrelloBoard trelloBoard = trelloService.getBoard(boardId);
        List<ReportRecord> records = generateReportRecordsFromTrelloBoard(trelloBoard);

        return generateOutputFromRecords(records);
    }

    List<ReportRecord> generateReportRecordsFromTrelloBoard(TrelloBoard board) {
        Set<String> labelNames = retrieveLabelNamesFromTrello(board);
        Map<String, String> listIdToNameMap = mapLabelIdToName(board);

        Set<String> kataListIDs = getListIdsForLists(board, listsForKataStage);
        Set<String> leadershipListIDs = getListIdsForLists(board, listsForLeadershipStage);
        Set<String> hiredListIds = getListIdsForLists(board, listsForHiredStage);

        return  board.getCards().stream()
                .filter(card -> cardIsACandidate(labelNames, card.getName()))
                .filter(card -> cardIsNotInArchivedList(card, listIdToNameMap))
                .map(TrelloCard::makeRecord)
                .map(record -> addListNameToRecord(record, listIdToNameMap))
                .map(record -> classifyCandidate(record, kataListIDs, leadershipListIDs, hiredListIds))
                .collect(Collectors.toList());
    }

    private ReportRecord addListNameToRecord(ReportRecord record, Map<String, String> listIdToNameMap) {
        record.setListName(listIdToNameMap.get(record.getIdList()));
        return record;
    }

    private ReportRecord classifyCandidate(ReportRecord record,  Set<String> kataListIDs,  Set<String> leadershipListIDs,  Set<String> hiredListIds) {
        if (kataListIDs.contains(record.getIdList()))
            record.setStageKata(true);
        if (leadershipListIDs.contains(record.getIdList()))
            record.setStageLeadership(true);
        if (hiredListIds.contains(record.getIdList()))
            record.setStageHired(true);
        return record;
    }

    private boolean cardIsNotInArchivedList(TrelloCard card, Map<String, String>  listIdToNameMap) {
        return listIdToNameMap.get(card.getIdList()) != null;
    }

    private boolean cardIsACandidate(Set<String> labelNames, String name) {
        return ! labelNames.contains(name);
    }

    private Map<String, String> mapLabelIdToName(TrelloBoard board) {
        Map<String,String> idToNameListMap = new HashMap<>();
        board.getLists().forEach(treloList -> idToNameListMap.put(treloList.getId(), treloList.getName()));
        return idToNameListMap;
    }

    private Set<String> retrieveLabelNamesFromTrello(TrelloBoard board) {
        Set<String> labelNames = new HashSet<>();

        List<TrelloLabel> labelList = trelloService.getLabels(board.getId());
        labelList.forEach(label -> labelNames.add(label.getName()));
        return labelNames;
    }

    private String generateOutputFromRecords(List<ReportRecord> records) {
        StringBuilder builder = new StringBuilder();
        builder.append("Name,Location,Role,ListName,Stage\n");
        records.forEach(record -> builder.append(record).append("\n"));

        return builder.toString();
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
