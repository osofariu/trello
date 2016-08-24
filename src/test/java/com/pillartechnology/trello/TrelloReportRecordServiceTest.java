package com.pillartechnology.trello;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TrelloReportRecordServiceTest {

    private TrelloReportRecordService service = new TrelloReportRecordService();

    @Test
    public void whenCardHasSameNameAsLabel_cardShouldNotGenerateReportRecord(){
        TrelloBoard board = new TrelloBoard();
        board.setCards(Arrays.asList(createCard("OVR")));
        board.setLabelNames(createLabels());

        List<TrelloReportRecord> records = service.generateReportRecordsFromTrelloBoard(board);
        assertEquals(true, records.isEmpty());
    }

    private Map<String,String> createLabels() {
        Map<String, String> labels = new HashMap<String, String>();
        labels.put("Blue", "OVR");

        return labels;
    }

    private TrelloCard createCard(String name) {
        TrelloCard card = new TrelloCard();
        card.setName(name);

        return card;
    }
}
