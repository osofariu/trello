package com.pillartechnology.trello;

import static com.pillartechnology.trello.Stages.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecruitToHireServiceTest {
    private RecruitToHireService service = new RecruitToHireService();

    private List<ReportRecord> records = new ArrayList<>();

    @Test
    public void whenCountingSingleRecord_returnsCountOfOne(){
        records.add(createReportRecord("Journeyman", STAGE_KATA));

        Map<String, SummaryCount> summaryByRole = service.createSummaryCountsForRecords(records);

        assertEquals(Integer.valueOf(1), summaryByRole.get("Journeyman").getCountForStage(STAGE_KATA));
    }

    @Test
    public void whenCountingTwoOfSameRoleAndStage_returnsCountOfTwo(){
        records.add(createReportRecord("Journeyman", STAGE_KATA));
        records.add(createReportRecord("Journeyman", STAGE_KATA));

        Map<String, SummaryCount> summaryByRole = service.createSummaryCountsForRecords(records);

        assertEquals(Integer.valueOf(2), summaryByRole.get("Journeyman").getCountForStage(STAGE_KATA));
    }

    @Test
    public void whenCountingTwoOfSameRole_returnsCountOfOneForEachStage(){
        records.add(createReportRecord("Journeyman", STAGE_KATA));
        records.add(createReportRecord("Journeyman", STAGE_OFFER));

        Map<String, SummaryCount> summaryByRole = service.createSummaryCountsForRecords(records);

        assertEquals(Integer.valueOf(1), summaryByRole.get("Journeyman").getCountForStage(STAGE_KATA));
        assertEquals(Integer.valueOf(1), summaryByRole.get("Journeyman").getCountForStage(STAGE_OFFER));
    }

    @Test
    public void whenCountingTwoOfDifferentRole_returnsCountOfOneForEachRoleAndStage(){
        records.add(createReportRecord("Journeyman", STAGE_KATA));
        records.add(createReportRecord("Craftsman", STAGE_OFFER));

        Map<String, SummaryCount> summaryByRole = service.createSummaryCountsForRecords(records);

        assertEquals(Integer.valueOf(1), summaryByRole.get("Journeyman").getCountForStage(STAGE_KATA));
        assertEquals(Integer.valueOf(1), summaryByRole.get("Craftsman").getCountForStage(STAGE_OFFER));
    }

    @Test
    public void whenCountingOneRecord_returnsCountOfZeroEmptyStage(){
        records.add(createReportRecord("Journeyman", STAGE_KATA));

        Map<String, SummaryCount> summaryByRole = service.createSummaryCountsForRecords(records);

        assertEquals(Integer.valueOf(0), summaryByRole.get("Journeyman").getCountForStage(STAGE_OFFER));
    }

    private ReportRecord createReportRecord(String role, String stage){
        ReportRecord record = new ReportRecord();
        record.setRole(role);
        record.setStage(stage);

        return record;
    }
}
