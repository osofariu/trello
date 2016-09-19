package com.pillartechnology.trello;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class TrelloPropertiesTest {
    @InjectMocks
    private TrelloProperties trelloProps;

    @Mock
    private Properties props;


    @Test
    public void whenGettingListNamesKataStage_returnsCorrectNamesInSet() throws Exception{
        when(props.getProperty("KataStage")).thenReturn("Kata Exercise (Polyglot):Android/iOS Kata Exercise (ADS):Falcon Kata Exercise:DevOps Kata Exercise");

        Set<String> kataLists = trelloProps.getListNamesForKataStage();

        assertTrue(kataLists.contains("Kata Exercise (Polyglot)"));
        assertTrue(kataLists.contains("Android/iOS Kata Exercise (ADS)"));
        assertTrue(kataLists.contains("Falcon Kata Exercise"));
        assertTrue(kataLists.contains("DevOps Kata Exercise"));
    }

    @Test
    public void whenGettingListNamesPairingStage_returnsCorrectNamesInSet() throws Exception{
        when(props.getProperty("PairingStage")).thenReturn("DevOps Presentation:Digital Experience (XA and XD) Technical Interview:Delivery (DL, DM, DE) Technical Interview:Pairing Session");

        Set<String> pairingLists = trelloProps.getListNamesForPairingStage();

        assertTrue(pairingLists.contains("DevOps Presentation"));
        assertTrue(pairingLists.contains("Digital Experience (XA and XD) Technical Interview"));
        assertTrue(pairingLists.contains("Delivery (DL, DM, DE) Technical Interview"));
        assertTrue(pairingLists.contains("Pairing Session"));
    }

    @Test
    public void whenGettingListNamesLeadershipStage_returnsCorrectNamesInSet() throws Exception {
        when(props.getProperty("LeadershipStage")).thenReturn("Leadership Interview");

        Set<String> leadershipLists = trelloProps.getListNamesForLeadershipStage();

        assertTrue(leadershipLists.contains("Leadership Interview"));
    }

    @Test
    public void whenGettingListNamesFullyVettedStage_returnsCorrectNamesInSet() throws Exception {
        when(props.getProperty("FullyVettedStage")).thenReturn("Fully Vetted");

        Set<String> vettedLists = trelloProps.getListNamesForVettedStage();

        assertTrue(vettedLists.contains("Fully Vetted"));
    }

    @Test
    public void whenGettingListNamesOfferStage_returnsCorrectNamesInSet() throws Exception {
        when(props.getProperty("OfferPendingStage")).thenReturn("Offer Pending");

        Set<String> offerLists = trelloProps.getListNamesForOfferPendingStage();

        assertTrue(offerLists.contains("Offer Pending"));
    }
    
    @Test
    public void whenGettingTrelloBoardId_returnsTheCorrectProperty(){
        when(props.getProperty("BoardID")).thenReturn("BoardID");

        assertEquals("BoardID", trelloProps.getTrelloBoardId());
    }

    @Test
    public void whenGettingTrelloAppKey_returnsTheCorrectProperty(){
        when(props.getProperty("ApplicationKey")).thenReturn("AppKey");

        assertEquals("AppKey", trelloProps.getTrelloAppKey());
    }

    @Test
    public void whenGettingTrelloAppToken_returnsTheCorrectProperty(){
        when(props.getProperty("ApplicationToken")).thenReturn("AppToken");

        assertEquals("AppToken", trelloProps.getTrelloAppToken());
    }

    @Test
    public void whenGettingSummaryFileName_returnsTheCorrectProperty(){
        when(props.getProperty("CandidateFile")).thenReturn("candidateFile");

        assertEquals("candidateFile", trelloProps.getCandidateFileName());
    }

    @Test
    public void whenGettingCandidateFileName_returnsTheCorrectProperty(){
        when(props.getProperty("SummaryFile")).thenReturn("file");

        assertEquals("file", trelloProps.getSummaryFileName());
    }
}
