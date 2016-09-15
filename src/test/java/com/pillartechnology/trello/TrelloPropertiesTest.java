package com.pillartechnology.trello;

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
        Mockito.when(props.getProperty("KataStage")).thenReturn("Kata Exercise (Polyglot):Android/iOS Kata Exercise (ADS):Falcon Kata Exercise:DevOps Kata Exercise");

        Set<String> kataLists = trelloProps.getListNamesForKataStage();

        Assert.assertTrue(kataLists.contains("Kata Exercise (Polyglot)"));
        Assert.assertTrue(kataLists.contains("Android/iOS Kata Exercise (ADS)"));
        Assert.assertTrue(kataLists.contains("Falcon Kata Exercise"));
        Assert.assertTrue(kataLists.contains("DevOps Kata Exercise"));
    }

    @Test
    public void whenGettingListNamesPairingStage_returnsCorrectNamesInSet() throws Exception{
        Mockito.when(props.getProperty("PairingStage")).thenReturn("DevOps Presentation:Digital Experience (XA and XD) Technical Interview:Delivery (DL, DM, DE) Technical Interview:Pairing Session");

        Set<String> pairingLists = trelloProps.getListNamesForPairingStage();

        Assert.assertTrue(pairingLists.contains("DevOps Presentation"));
        Assert.assertTrue(pairingLists.contains("Digital Experience (XA and XD) Technical Interview"));
        Assert.assertTrue(pairingLists.contains("Delivery (DL, DM, DE) Technical Interview"));
        Assert.assertTrue(pairingLists.contains("Pairing Session"));
    }

    @Test
    public void whenGettingListNamesLeadershipStage_returnsCorrectNamesInSet() throws Exception {
        Mockito.when(props.getProperty("LeadershipStage")).thenReturn("Leadership Interview");

        Set<String> leadershipLists = trelloProps.getListNamesForLeadershipStage();

        Assert.assertTrue(leadershipLists.contains("Leadership Interview"));
    }

    @Test
    public void whenGettingListNamesFullyVettedStage_returnsCorrectNamesInSet() throws Exception {
        Mockito.when(props.getProperty("FullyVettedStage")).thenReturn("Fully Vetted");

        Set<String> vettedLists = trelloProps.getListNamesForVettedStage();

        Assert.assertTrue(vettedLists.contains("Fully Vetted"));
    }

    @Test
    public void whenGettingListNamesOfferStage_returnsCorrectNamesInSet() throws Exception {
        Mockito.when(props.getProperty("OfferPendingStage")).thenReturn("Offer Pending");

        Set<String> offerLists = trelloProps.getListNamesForOfferPendingStage();

        Assert.assertTrue(offerLists.contains("Offer Pending"));
    }
}
