package com.pillartechnology.trello;

import static com.pillartechnology.trello.Stages.*;

import com.pillartechnology.trello.entities.TalentStages;
import com.pillartechnology.trello.entities.TrelloBoard;
import com.pillartechnology.trello.entities.TrelloCard;
import com.pillartechnology.trello.entities.TrelloLabel;

import java.util.*;
import java.util.stream.Collectors;

class ReportGenerationService {

    TrelloService trelloService;
    TrelloProperties trelloProps;

    public ReportGenerationService(String appKey, String appToken) {
        trelloService = new TrelloService(appKey, appToken);
        trelloProps = new TrelloProperties();
    }

    String generateReport(String boardId)  {
        TrelloBoard trelloBoard = trelloService.getBoard(boardId);
        List<ReportRecord> records = generateReportRecordsFromTrelloBoard(trelloBoard);

        return generateOutputFromRecords(records);
    }

    List<ReportRecord> generateReportRecordsFromTrelloBoard(TrelloBoard board) {
        Set<String> labelNames = retrieveLabelNamesFromTrello(board);
        Map<String, String> listIdToNameMap = mapLabelIdToName(board);

        TalentStages stages = new TalentStages();
        stages.setKataListIds(getListIdsForLists(board, trelloProps.getListNamesForKataStage()));
        stages.setLeadershipListIds(getListIdsForLists(board, trelloProps.getListNamesForLeadershipStage()));
        stages.setOfferPendingListIds(getListIdsForLists(board, trelloProps.getListNamesForOfferPendingStage()));
        stages.setPairingListIds(getListIdsForLists(board, trelloProps.getListNamesForPairingStage()));
        stages.setVettedListIds(getListIdsForLists(board, trelloProps.getListNamesForVettedStage()));

        return  board.getCards().stream()
                .filter(card -> cardIsACandidate(labelNames, card.getName()))
                .filter(card -> cardIsNotInArchivedList(card, listIdToNameMap))
                .map(TrelloCard::makeRecord)
                .map(record -> addListNameToRecord(record, listIdToNameMap))
                .map(record -> classifyCandidate(record, stages))
                .collect(Collectors.toList());
    }

    private ReportRecord addListNameToRecord(ReportRecord record, Map<String, String> listIdToNameMap) {
        record.setListName(listIdToNameMap.get(record.getIdList()));
        return record;
    }

    private ReportRecord classifyCandidate(ReportRecord record, TalentStages stages) {
        if (stages.getKataListIds().contains(record.getIdList()))
            record.setStage(STAGE_KATA);
        else if (stages.getLeadershipListIds().contains(record.getIdList()))
            record.setStage(STAGE_LEADERSHIP);
        else if (stages.getOfferPendingListIds().contains(record.getIdList()))
            record.setStage(STAGE_OFFER);
        else if (stages.getPairingListIds().contains(record.getIdList()))
            record.setStage(STAGE_PAIRING);
        else if (stages.getVettedListIds().contains(record.getIdList()))
            record.setStage(STAGE_VETTED);

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

    private Set<String> getListIdsForLists(TrelloBoard board, Set<String> listNames) {
        Set<String> listIDs = new HashSet<>();
        board.getLists().forEach(trelloList -> {
            if (listNames.contains(trelloList.getName()))
                listIDs.add(trelloList.getId());
        });
        return listIDs;
    }
}
