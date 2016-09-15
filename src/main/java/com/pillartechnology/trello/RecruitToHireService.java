package com.pillartechnology.trello;

import com.pillartechnology.trello.entities.RoleLocation;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pillartechnology.trello.Stages.*;

public class RecruitToHireService {

    public Map<RoleLocation, SummaryCount> createSummaryCountsForRecords(List<ReportRecord> reportRecords){
        Map<RoleLocation, SummaryCount> summaryCountByRole = new HashMap<>();

        reportRecords.stream().filter(record -> !record.getStage().trim().equals("")).forEach(record -> {
            RoleLocation roleLocation = new RoleLocation(record.getRole(), record.getLocation());
            if(!summaryCountByRole.containsKey(roleLocation))
                summaryCountByRole.put(roleLocation, new SummaryCount());

            summaryCountByRole.get(roleLocation).addToCountForStage(record.getStage());
        });

        return summaryCountByRole;
    }

    public String convertSummaryByRoleToString(Map<RoleLocation, SummaryCount> summaryCountByRole){
        StringBuilder builder = new StringBuilder();

        builder.append("Role, Location, Kata, Pairing, Leadership, Fully Vetted, Offer Pending\n");

        summaryCountByRole.forEach((role, summaryCount) -> {
            builder.append(role.getRole().trim().equals("") ? "Undefined," : role.getRole() + ",");
            builder.append(role.getLocation() + ",");
            builder.append(summaryCount.getCountForStage(STAGE_KATA) + ",");
            builder.append(summaryCount.getCountForStage(STAGE_PAIRING) + ",");
            builder.append(summaryCount.getCountForStage(STAGE_LEADERSHIP) + ",");
            builder.append(summaryCount.getCountForStage(STAGE_VETTED) + ",");
            builder.append(summaryCount.getCountForStage(STAGE_OFFER) + "\n");
        });

        return builder.toString();
    }

}
