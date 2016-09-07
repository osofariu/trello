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
        rec.setListName("list");

        assertEquals("\"John Doe\",\"OVR\",\"Delivery Lead\",\"list\",\"\"", rec.toString());

    }

    @Test
    public void givenReportRecordForKataStage_formatShouldShowThat() {
        TrelloCard tc = new TrelloCard();
        tc.setName("John Doe");
        tc.setLabels(asList(createLabel("Delivery Lead"), createLabel("OVR")));
        ReportRecord rec = tc.makeRecord();
        rec.setListName("list");
        rec.setStage(ReportRecord.STAGE_KATA);

        assertEquals("\"John Doe\",\"OVR\",\"Delivery Lead\",\"list\",\"Kata\"", rec.toString());

    }

    @Test
    public void givenReportRecordForLeadershipStage_formatShouldShowThat() {
        TrelloCard tc = new TrelloCard();
        tc.setName("John Doe");
        tc.setLabels(asList(createLabel("Delivery Lead"), createLabel("OVR")));
        ReportRecord rec = tc.makeRecord();
        rec.setListName("list");
        rec.setStage(ReportRecord.STAGE_LEADERSHIP);

        assertEquals("\"John Doe\",\"OVR\",\"Delivery Lead\",\"list\",\"Leadership\"", rec.toString());

    }

    @Test
    public void givenReportRecordForHireStage_formatShouldShowThat() {
        TrelloCard tc = new TrelloCard();
        tc.setName("John Doe");
        tc.setLabels(asList(createLabel("Delivery Lead"), createLabel("OVR")));
        ReportRecord rec = tc.makeRecord();
        rec.setListName("list");
        rec.setStage(ReportRecord.STAGE_OFFER);

        assertEquals("\"John Doe\",\"OVR\",\"Delivery Lead\",\"list\",\"Offer Pending\"", rec.toString());

    }

    @Test
    public void givenReportRecordHasList_formatShouldShowThat() {
        TrelloCard tc = new TrelloCard();
        tc.setName("John Doe");
        tc.setLabels(asList(createLabel("Delivery Lead"), createLabel("OVR")));
        ReportRecord rec = tc.makeRecord();
        rec.setListName("The List");
        rec.setStage(ReportRecord.STAGE_OFFER);

        assertEquals("\"John Doe\",\"OVR\",\"Delivery Lead\",\"The List\",\"Offer Pending\"", rec.toString());

    }

    //TODO eliminate duplicate
    private TrelloLabel createLabel(String labelName) {
        TrelloLabel l = new TrelloLabel();
        l.setName(labelName);
        return l;
    }
}
