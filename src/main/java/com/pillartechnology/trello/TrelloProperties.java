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

    private static final String BOARD_ID = "BoardID";
    private static final String APP_KEY = "ApplicationKey";
    private static final String APP_TOKEN = "ApplicationToken";

    private static final String CANDIDATE_FILE_NAME = "CandidateFile";
    private static final String SUMMARY_FILE_NAME = "SummaryFile";

    private Properties properties = new Properties();
    private boolean propsLoaded = false;

    private static TrelloProperties trelloProperties;

    private TrelloProperties(){

    }

    public static TrelloProperties getInstance(){
        if(trelloProperties == null){
            trelloProperties = new TrelloProperties();
        }

        return trelloProperties;
    }

    public Set<String> getListNamesForKataStage(){
        String kataStage = properties.getProperty(KATA_STG);

        return parsePropertyIntoIndividualNames(kataStage);
    }

    public  Set<String> getListNamesForPairingStage(){
        String pairingStage = properties.getProperty(PAIR_STG);

        return parsePropertyIntoIndividualNames(pairingStage);
    }

    public Set<String> getListNamesForLeadershipStage() {
        String leadershipStg = properties.getProperty(LEADER_STG);

        return parsePropertyIntoIndividualNames(leadershipStg);
    }

    public Set<String> getListNamesForVettedStage(){
        String vettedStage = properties.getProperty(VETTED_STG);

        return parsePropertyIntoIndividualNames(vettedStage);
    }

    public Set<String> getListNamesForOfferPendingStage(){
        String offerStage = properties.getProperty(OFFER_STG);

        return parsePropertyIntoIndividualNames(offerStage);
    }

    public String getTrelloBoardId(){
        return properties.getProperty(BOARD_ID).trim();
    }

    public String getTrelloAppKey(){
        return properties.getProperty(APP_KEY).trim();
    }

    public String getTrelloAppToken(){
        return properties.getProperty(APP_TOKEN).trim();
    }

    public String getCandidateFileName(){
        return properties.getProperty(CANDIDATE_FILE_NAME).trim();
    }

    public String getSummaryFileName(){
        return properties.getProperty(SUMMARY_FILE_NAME).trim();
    }

    public void loadPropertiesFromFile(String propsFileName){
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
