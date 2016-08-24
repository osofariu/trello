package com.pillartechnology.trello;

import java.util.*;

public class TrelloReportRecordService {

    public List<TrelloReportRecord> generateReportRecordsFromTrelloBoard(TrelloBoard board) {
        Set<String> labelDescs = new HashSet<String>(board.getLabelNames().values());

        List<TrelloReportRecord> records = new ArrayList<TrelloReportRecord>();

        board.getCards().forEach(card -> {
            if(! labelDescs.contains(card.getName())){
                records.add(card.makeRecord());
            }
        });

        return records;
    }
}
