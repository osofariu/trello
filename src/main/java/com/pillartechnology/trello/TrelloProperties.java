package com.pillartechnology.trello;

import com.pillartechnology.trello.exception.TrelloServiceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class TrelloProperties {
    private static final String KATA_STG = "KataStage";
    private static final String PAIR_STG = "PairingStage";
    private static final String LEADER_STG = "LeadershipStage";
    private static final String VETTED_STG = "FullyVettedStage";
    private static final String OFFER_STG = "OfferPendingStage";

    private Properties properties = new Properties();
    private String propsFileName = "TalentManagement.properties";
    private boolean propsLoaded = false;

    public void setPropertiesFileName(String fileName){
        propsFileName = fileName;
    }

    public Set<String> getListNamesForKataStage(){
        loadPropertiesIfNotLoaded();
        String kataStage = properties.getProperty(KATA_STG);

        return parsePropertyIntoIndividualNames(kataStage);
    }

    public  Set<String> getListNamesForPairingStage(){
        loadPropertiesIfNotLoaded();
        String pairingStage = properties.getProperty(PAIR_STG);

        return parsePropertyIntoIndividualNames(pairingStage);
    }

    public Set<String> getListNamesForLeadershipStage() {
        loadPropertiesIfNotLoaded();
        String leadershipStg = properties.getProperty(LEADER_STG);

        return parsePropertyIntoIndividualNames(leadershipStg);
    }

    public Set<String> getListNamesForVettedStage(){
        loadPropertiesIfNotLoaded();
        String vettedStage = properties.getProperty(VETTED_STG);

        return parsePropertyIntoIndividualNames(vettedStage);
    }

    public Set<String> getListNamesForOfferPendingStage(){
        loadPropertiesIfNotLoaded();
        String offerStage = properties.getProperty(OFFER_STG);

        return parsePropertyIntoIndividualNames(offerStage);
    }

    private void loadPropertiesIfNotLoaded(){
        if(!propsLoaded){
            try {
                properties.load(new FileInputStream(new File(propsFileName)));
            } catch(FileNotFoundException e){
                throw new TrelloServiceException("File "+propsFileName+" could not be found");
            } catch(IOException e){
                throw new TrelloServiceException(e.getMessage());
            }

            propsLoaded = true;
        }
    }

    private Set<String> parsePropertyIntoIndividualNames(String property){
        Set<String> parsedNames = new HashSet<>();

        for(String name : property.split(":")){
            parsedNames.add(name.trim());
        }

        return parsedNames;
    }
}
