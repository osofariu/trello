package com.pillartechnology.trello;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecruitToHireService {

    public Map<String, SummaryCount> createSummaryCountsForRecords(List<ReportRecord> reportRecords){
        Map<String, SummaryCount> summaryCountByRole = new HashMap<>();

        reportRecords.stream().filter(record -> !record.getStage().trim().equals("")).forEach(record -> {
            if(!summaryCountByRole.containsKey(record.getRole()))
                summaryCountByRole.put(record.getRole(), new SummaryCount());

            summaryCountByRole.get(record.getRole()).addToCountForStage(record.getStage());
        });

        return summaryCountByRole;
    }

}
