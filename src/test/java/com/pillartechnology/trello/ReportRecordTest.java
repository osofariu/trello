package com.pillartechnology.trello;

import com.pillartechnology.trello.entities.TrelloCard;
import com.pillartechnology.trello.entities.TrelloLabel;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ReportRecordTest {

    @Test
    public void givenTrelloReportRecordFormat_NoListsformatShouldFormatRecordAsCSV() {
        TrelloCard tc = new TrelloCard();
        tc.setName("John Doe");
        tc.setLabels(asList(createLabel("Delivery Lead"), createLabel("OVR")));
        ReportRecord rec = tc.makeRecord();
        rec.setIdList("list");

        assertEquals("\"John Doe\",\"OVR\",\"Delivery Lead\",\"list\",\"\"", rec.toString());

    }

    @Test
    public void givenReportRecordForKataStage_formatShouldShowThat() {
        TrelloCard tc = new TrelloCard();
        tc.setName("John Doe");
        tc.setLabels(asList(createLabel("Delivery Lead"), createLabel("OVR")));
        ReportRecord rec = tc.makeRecord();
        rec.setIdList("list");
        rec.setStageKata(true);

        assertEquals("\"John Doe\",\"OVR\",\"Delivery Lead\",\"list\",\"kata\"", rec.toString());

    }

    @Test
    public void givenReportRecordForLeadershipStage_formatShouldShowThat() {
        TrelloCard tc = new TrelloCard();
        tc.setName("John Doe");
        tc.setLabels(asList(createLabel("Delivery Lead"), createLabel("OVR")));
        ReportRecord rec = tc.makeRecord();
        rec.setIdList("list");
        rec.setStageLeadership(true);

        assertEquals("\"John Doe\",\"OVR\",\"Delivery Lead\",\"list\",\"leadership\"", rec.toString());

    }

    @Test
    public void givenReportRecordForHireStage_formatShouldShowThat() {
        TrelloCard tc = new TrelloCard();
        tc.setName("John Doe");
        tc.setLabels(asList(createLabel("Delivery Lead"), createLabel("OVR")));
        ReportRecord rec = tc.makeRecord();
        rec.setIdList("list");
        rec.setStageHired(true);

        assertEquals("\"John Doe\",\"OVR\",\"Delivery Lead\",\"list\",\"hired\"", rec.toString());

    }

    @Test
    public void givenReportRecordHasList_formatShouldShowThat() {
        TrelloCard tc = new TrelloCard();
        tc.setName("John Doe");
        tc.setLabels(asList(createLabel("Delivery Lead"), createLabel("OVR")));
        ReportRecord rec = tc.makeRecord();
        rec.setIdList("The List");
        rec.setStageHired(true);

        assertEquals("\"John Doe\",\"OVR\",\"Delivery Lead\",\"The List\",\"hired\"", rec.toString());

    }

    //TODO eliminate duplicate
    private TrelloLabel createLabel(String labelName) {
        TrelloLabel l = new TrelloLabel();
        l.setName(labelName);
        return l;
    }
}
