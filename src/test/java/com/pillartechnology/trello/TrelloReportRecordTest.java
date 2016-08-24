package com.pillartechnology.trello;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class TrelloReportRecordTest {

    @Test
    public void givenTrelloReportRecordFormat_formatShouldFormatRecordAsCSV() {
        TrelloCard tc = new TrelloCard();
        tc.setName("John Doe");
        tc.setLabels(asList(createLabel("Delivery Lead"), createLabel("OVR")));
        TrelloReportRecord rec = tc.makeRecord();

        assertEquals("John Doe,OVR,Delivery Lead", rec.format());

    }

    //TODO eliminate duplicate
    private TrelloLabel createLabel(String labelName) {
        TrelloLabel l = new TrelloLabel();
        l.setName(labelName);
        return l;
    }
}
