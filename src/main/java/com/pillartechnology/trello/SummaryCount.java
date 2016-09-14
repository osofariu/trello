package com.pillartechnology.trello;

import java.util.HashMap;
import java.util.Map;

public class SummaryCount {

    private Map<String, Integer> countByStage = new HashMap<>();

    public void addToCountForStage(String stage){
        if(!countByStage.containsKey(stage))
            countByStage.put(stage, new Integer(0));

        countByStage.put(stage, countByStage.get(stage) + 1);
    }

    public Integer getCountForStage(String stage){
        return (countByStage.containsKey(stage) ? countByStage.get(stage) : new Integer(0));
    }

}
