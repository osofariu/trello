package com.pillartechnology.trello;

import org.junit.Test;

import static com.pillartechnology.trello.TrelloLabel.createLabel;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class TrelloCardTest {
    @Test
    public void givenCardWithName_nameFieldSetOnRecord() {
        TrelloCard card = new TrelloCard();
        card.setName("John");

        TrelloReportRecord rec = card.makeRecord();

        assertEquals("John", rec.getName());
    }

    @Test
    public void givenCardWithLocationIHSLocationFieldIsIHS() {
        TrelloCard card = new TrelloCard();
        card.setLabels(singletonList(createLabel("IHS")));

        TrelloReportRecord rec = card.makeRecord();

        assertEquals(rec.getLocation(), "IHS");
    }

    @Test
    public void givenCardWithRoleDL_roleFieldSetOnRecord(){
        TrelloCard card = new TrelloCard();
        card.setLabels(singletonList(createLabel("Delivery Lead")));

        TrelloReportRecord rec = card.makeRecord();

        assertEquals(rec.getRole(), "Delivery Lead");
    }
}
