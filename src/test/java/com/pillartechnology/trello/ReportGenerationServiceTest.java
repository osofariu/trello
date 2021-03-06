package com.pillartechnology.trello;

import com.pillartechnology.trello.entities.TrelloBoard;
import com.pillartechnology.trello.entities.TrelloCard;
import com.pillartechnology.trello.entities.TrelloLabel;
import com.pillartechnology.trello.entities.TrelloList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static com.pillartechnology.trello.Stages.*;
import static com.pillartechnology.trello.builders.TrelloLabelBuilder.trelloLabel;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportGenerationServiceTest {

    @InjectMocks
    private ReportGenerationService reportGenerationService;

    @Mock
    private TrelloService trelloReportService;

    @Mock
    private TrelloProperties trelloProps;

    @Mock
    private RecruitToHireService recruitToHireService;

    @Mock
    private TrelloReportWriter reportWriter;

    @Captor
    private ArgumentCaptor<String> candidateCaptor;

    @Before
    public void setUp() {
        reportGenerationService.trelloService = trelloReportService;
        reportGenerationService.trelloProps = trelloProps;

        when(recruitToHireService.createSummaryCountsForRecords(Mockito.anyList())).thenReturn(new HashMap());
        when(recruitToHireService.convertSummaryByRoleToString(Mockito.anyMap())).thenReturn("");

        when(trelloProps.getListNamesForKataStage()).thenReturn(new HashSet<String>(Arrays.asList("Kata Exercise (Polyglot)")));
        when(trelloProps.getListNamesForLeadershipStage()).thenReturn(new HashSet<String>(Arrays.asList("Leadership Interview")));
        when(trelloProps.getListNamesForOfferPendingStage()).thenReturn(new HashSet<String>(Arrays.asList("Offer Pending")));
        when(trelloProps.getListNamesForPairingStage()).thenReturn(new HashSet<String>(Arrays.asList("DevOps Presentation")));
        when(trelloProps.getListNamesForVettedStage()).thenReturn(new HashSet<String>(Arrays.asList("Fully Vetted")));

        when(trelloProps.getTrelloAppKey()).thenReturn("1");
        when(trelloProps.getTrelloAppToken()).thenReturn("12");
        when(trelloProps.getTrelloBoardId()).thenReturn("board");
        when(trelloProps.getCandidateFileName()).thenReturn("fileName");
    }

    @Test
    public void whenCardHasSameNameAsLabel_cardShouldNotGenerateReportRecord(){
        TrelloBoard board = new TrelloBoard();
        board.setId("board123");
        board.setLists(new ArrayList<>());
        board.setCards(singletonList(createCard("card-name", "loc", "role", "listID")));

        when(trelloReportService.getBoard()).thenReturn(board);
        List<TrelloLabel> labels = singletonList(trelloLabel().name("card-name").build());
        when(trelloReportService.getLabels()).thenReturn(labels);

        List<ReportRecord> records = reportGenerationService.generateReportRecordsFromTrelloBoard(board);
        assertEquals(true, records.isEmpty());
    }

    @Test
    public void whenGeneratingReport_callsTrelloRecordServiceGetBoard(){
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("name", "OVR", "role", "listID")));
        board.setLists(asList(createList("listID", "Test List")));
        when(trelloReportService.getBoard()).thenReturn(board);

        reportGenerationService.generateReport();

        verify(trelloReportService).getBoard();
    }

    @Test
    public void whenGeneratingReportWithOneCard_CreateCSVFormatRecordsAsString() {
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("Joe", "OVR", "Journeyman", "123")));
        board.setLists(asList(createList("123", "Test List")));
        when(trelloReportService.getBoard()).thenReturn(board);

        reportGenerationService.generateReport();

        verify(reportWriter).writeContentToFile(candidateCaptor.capture(), Mockito.eq("fileName"));
        assertEquals("Name,Location,Role,ListName,Stage\n\"Joe\",\"OVR\",\"Journeyman\",\"Test List\",\"\"\n", candidateCaptor.getValue());
    }

    @Test
    public void whenCardBelongsToKataList_ReportRecordShouldIndicateThat() {
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("Joe", "OVR", "Journeyman", "123")));
        board.setLists(asList(createList("123", "Kata Exercise (Polyglot)")));

        when(trelloReportService.getLabels()).thenReturn(asList(new TrelloLabel()));

        List<ReportRecord> records = reportGenerationService.generateReportRecordsFromTrelloBoard(board);
        ReportRecord cardRecord = records.get(0);

        assertEquals(STAGE_KATA, cardRecord.getStage());
    }

    @Test
    public void whenCardBelongsToLeadershipList_ReportRecordShouldIndicateThat() {
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("Joe", "OVR", "Journeyman", "123")));
        board.setLists(asList(createList("123", "Leadership Interview")));

        when(trelloReportService.getLabels()).thenReturn(asList(new TrelloLabel()));

        List<ReportRecord> records = reportGenerationService.generateReportRecordsFromTrelloBoard(board);
        ReportRecord cardRecord = records.get(0);

        assertEquals(STAGE_LEADERSHIP, cardRecord.getStage());
    }

    @Test
    public void whenCardBelongsToOfferPending_ReportRecordShouldIndicateThat() {
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("Joe", "OVR", "Journeyman", "123")));
        board.setLists(asList(createList("123", "Offer Pending")));

        when(trelloReportService.getLabels()).thenReturn(asList(new TrelloLabel()));

        List<ReportRecord> records = reportGenerationService.generateReportRecordsFromTrelloBoard(board);
        ReportRecord cardRecord = records.get(0);

        assertEquals(STAGE_OFFER, cardRecord.getStage());
    }

    @Test
    public void whenCardBelongsToPairing_ReportRecordShouldIndicateThat() {
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("Joe", "OVR", "Journeyman", "123")));
        board.setLists(asList(createList("123", "DevOps Presentation")));

        when(trelloReportService.getLabels()).thenReturn(asList(new TrelloLabel()));

        List<ReportRecord> records = reportGenerationService.generateReportRecordsFromTrelloBoard(board);
        ReportRecord cardRecord = records.get(0);

        assertEquals(STAGE_PAIRING, cardRecord.getStage());
    }

    @Test
    public void whenCardBelongsToFullyVetted_ReportRecordShouldIndicateThat() {
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("Joe", "OVR", "Journeyman", "123")));
        board.setLists(asList(createList("123", "Fully Vetted")));

        when(trelloReportService.getLabels()).thenReturn(asList(new TrelloLabel()));

        List<ReportRecord> records = reportGenerationService.generateReportRecordsFromTrelloBoard(board);
        ReportRecord cardRecord = records.get(0);

        assertEquals(STAGE_VETTED, cardRecord.getStage());
    }

    @Test
    public void givenCardBelongsToList_ReportRecordShouldContainNameOfList() {
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("Joe", "OVR", "Journeyman", "123")));
        board.setLists(asList(createList("123", "The Cool Exclusive List")));
        when(trelloReportService.getLabels()).thenReturn(asList(new TrelloLabel()));


        List<ReportRecord> records = reportGenerationService.generateReportRecordsFromTrelloBoard(board);
        ReportRecord cardRecord = records.get(0);

        assertEquals("The Cool Exclusive List", cardRecord.getListName());
    }

    @Test
    public void whenGeneratingReport_TheFirstRowIsColumnNames() {
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("Joe", "OVR", "Journeyman", "123")));
        board.setLists(asList(createList("123", "Kata Exercise (Polyglot)")));

        when(trelloReportService.getBoard()).thenReturn(board);
        when(trelloReportService.getLabels()).thenReturn(asList(new TrelloLabel()));

        reportGenerationService.generateReport();

        verify(reportWriter).writeContentToFile(candidateCaptor.capture(), Mockito.eq("fileName"));
        assertEquals("Name,Location,Role,ListName,Stage\n\"Joe\",\"OVR\",\"Journeyman\",\"Kata Exercise (Polyglot)\",\"Kata\"\n", candidateCaptor.getValue());
    }

    @Test
    public void whenCardDoesntBelongToAnOpenList_ThenShouldFilterItOut() {
        TrelloBoard board = new TrelloBoard();
        board.setCards(asList(createCard("Joe", "OVR", "Journeyman", "archivedListId")));
        board.setLists(new ArrayList<TrelloList>());

        List<ReportRecord> recorcs = reportGenerationService.generateReportRecordsFromTrelloBoard(board);
        assertTrue(recorcs.isEmpty());
    }

    private TrelloCard createCard(String name, String loc, String role, String listId) {
        TrelloCard card = new TrelloCard();
        card.setName(name);
        card.setLabels(new ArrayList<>());
        card.getLabels().add(trelloLabel().name(loc).build());
        card.getLabels().add(trelloLabel().name(role).build());
        card.setIdList(listId);

        return card;
    }

    private TrelloList createList(String listId, String listName) {
        TrelloList trelloList = new TrelloList();
        trelloList.setId(listId);
        trelloList.setName(listName);
        return trelloList;
    }
}
