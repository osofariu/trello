package com.pillartechnology.trello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static com.pillartechnology.trello.builders.TrelloLabelBuilder.trelloLabel;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrelloReportRecordServiceTest {

    private TrelloReportService trelloReportService = mock(TrelloReportService.class);
    private TrelloReportRecordService service = new TrelloReportRecordService("appKey", "appToken");

    @Before
    public void setUp() {
        service.trelloReportService = trelloReportService;
    }

    @Test
    public void whenCardHasSameNameAsLabel_cardShouldNotGenerateReportRecord(){
        TrelloBoard board = new TrelloBoard();
        board.setId("board123");
        board.setLists(new ArrayList<>());
        board.setCards(singletonList(createCard("card-name", "loc", "role")));

        when(trelloReportService.getBoard("board123")).thenReturn(board);
        List<TrelloLabel> labels = singletonList(trelloLabel().name("card-name").build());
        when(trelloReportService.getLabels("board123")).thenReturn(labels);

        List<TrelloReportRecord> records = service.generateReportRecordsFromTrelloBoard(board);
        assertEquals(true, records.isEmpty());
    }

    @Test
    public void whenGeneratingReport_callsTrelloRecordServiceGetBoard(){
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("OVR", "OVR", "OVR")));
        board.setLabelNames(createLabels());
        board.setLists(new ArrayList<>());
        when(trelloReportService.getBoard("1")).thenReturn(board);

        service.generateReport("1");

        verify(trelloReportService).getBoard("1");
    }

    @Test
    public void whenGeneratingReportWithOneCard_CreateCSVFormatRecordsAsString() {
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("Joe", "OVR", "Journeyman")));
        board.setLabelNames(createLabels());
        board.setLists(new ArrayList<>());
        when(trelloReportService.getBoard("1")).thenReturn(board);


        String result = service.generateReport("1");

        assertEquals("Joe,OVR,Journeyman\n", result);
    }

    @Test
    public void whenBoardHasCardBelongingToKataList_ReportRecordShouldIndicateListMembership() {
        TrelloBoard board = new TrelloBoard();
        TrelloCard aCard = createCard("Joe", "OVR", "Journeyman");
        aCard.setIdList("123");
        board.setCards(asList(aCard));
        TrelloList trelloList = new TrelloList();
        trelloList.setName("Kata Exercise (Polyglot)");
        trelloList.setId("123");
        board.setLists(asList(trelloList));

        when(trelloReportService.getLabels(anyString())).thenReturn(asList(new TrelloLabel()));

        List<TrelloReportRecord> records = service.generateReportRecordsFromTrelloBoard(board);
        TrelloReportRecord cardRecord = records.get(0);

        assertTrue(cardRecord.getStageKata());
    }

    private Map<String,String> createLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("Blue", "OVR");

        return labels;
    }

    private TrelloCard createCard(String name, String loc, String role) {
        TrelloCard card = new TrelloCard();
        card.setName(name);
        card.setLabels(new ArrayList<>());
        card.getLabels().add(trelloLabel().name(loc).build());
        card.getLabels().add(trelloLabel().name(role).build());

        return card;
    }
}
