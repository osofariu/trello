package com.pillartechnology.trello;

import static com.pillartechnology.trello.Stages.*;

import static org.junit.Assert.*;

import com.pillartechnology.trello.entities.RoleLocation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecruitToHireServiceTest {
    private static final String HEADER_ROW = "Role, Location, Kata, Pairing, Leadership, Fully Vetted, Offer Pending\n";

    private RecruitToHireService service = new RecruitToHireService();

    private List<ReportRecord> records = new ArrayList<>();

    @Test
    public void whenCountingSingleRecord_returnsCountOfOne(){
        records.add(createReportRecord("Journeyman", "OVR", STAGE_KATA));

        Map<RoleLocation, SummaryCount> summaryByRole = service.createSummaryCountsForRecords(records);

        assertEquals(Integer.valueOf(1), summaryByRole.get(new RoleLocation("Journeyman", "OVR")).getCountForStage(STAGE_KATA));
    }

    @Test
    public void whenCountingTwoOfSameRoleAndStage_returnsCountOfTwo(){
        records.add(createReportRecord("Journeyman", "OVR", STAGE_KATA));
        records.add(createReportRecord("Journeyman", "OVR", STAGE_KATA));

        Map<RoleLocation, SummaryCount> summaryByRole = service.createSummaryCountsForRecords(records);

        assertEquals(Integer.valueOf(2), summaryByRole.get(new RoleLocation("Journeyman", "OVR")).getCountForStage(STAGE_KATA));
    }

    @Test
    public void whenCountingTwoOfSameRole_returnsCountOfOneForEachStage(){
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_KATA));
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_OFFER));

        Map<RoleLocation, SummaryCount> summaryByRole = service.createSummaryCountsForRecords(records);

        assertEquals(Integer.valueOf(1), summaryByRole.get(new RoleLocation("Journeyman", "OVR")).getCountForStage(STAGE_KATA));
        assertEquals(Integer.valueOf(1), summaryByRole.get(new RoleLocation("Journeyman", "OVR")).getCountForStage(STAGE_OFFER));
    }

    @Test
    public void whenCountingTwoOfDifferentRole_returnsCountOfOneForEachRoleAndStage(){
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_KATA));
        records.add(createReportRecord("Craftsman",  "OVR",STAGE_OFFER));

        Map<RoleLocation, SummaryCount> summaryByRole = service.createSummaryCountsForRecords(records);

        assertEquals(Integer.valueOf(1), summaryByRole.get(new RoleLocation("Journeyman", "OVR")).getCountForStage(STAGE_KATA));
        assertEquals(Integer.valueOf(1), summaryByRole.get(new RoleLocation("Craftsman", "OVR")).getCountForStage(STAGE_OFFER));
    }

    @Test
    public void whenCountingOneRecord_returnsCountOfZeroEmptyStage(){
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_KATA));

        Map<RoleLocation, SummaryCount> summaryByRole = service.createSummaryCountsForRecords(records);

        assertEquals(Integer.valueOf(0), summaryByRole.get(new RoleLocation("Journeyman", "OVR")).getCountForStage(STAGE_OFFER));
    }

    @Test
    public void whenConvertingSummaryByRoleToString_createsHeaderRow(){
        String printOut = service.convertSummaryByRoleToString(service.createSummaryCountsForRecords(records));

        assertEquals(HEADER_ROW, printOut);
    }

    @Test
    public void whenConvertingSummaryByRoleToString_createsRowForSingleRecord(){
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_KATA));

        String printOut = service.convertSummaryByRoleToString(service.createSummaryCountsForRecords(records));

        assertEquals(HEADER_ROW + "Journeyman,OVR,1,0,0,0,0\n", printOut);
    }

    @Test
    public void whenConvertingSummaryByRoleToString_createsRowForMultipleRecordsSingleRole(){
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_KATA));
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_KATA));
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_OFFER));
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_VETTED));
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_LEADERSHIP));
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_PAIRING));

        String printOut = service.convertSummaryByRoleToString(service.createSummaryCountsForRecords(records));

        assertEquals(HEADER_ROW + "Journeyman,OVR,2,1,1,1,1\n", printOut);
    }

    @Test
    public void whenConvertingSummaryByRoleToString_createsRowsForMultipleRoles(){
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_KATA));
        records.add(createReportRecord("Craftsman",  "OVR",STAGE_KATA));

        String printOut = service.convertSummaryByRoleToString(service.createSummaryCountsForRecords(records));

        String journeyman = "Journeyman,OVR,1,0,0,0,0\n";
        String craftsman = "Craftsman,OVR,1,0,0,0,0\n";
        assertEquals(HEADER_ROW + journeyman + craftsman, printOut);
    }

    @Test
    public void whenConvertingSummaryByRoleToString_createsRowsForMultipleRolesAndUndefined(){
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_KATA));
        records.add(createReportRecord("Craftsman", "OVR", STAGE_KATA));
        records.add(createReportRecord("", "OVR", STAGE_KATA));

        String printOut = service.convertSummaryByRoleToString(service.createSummaryCountsForRecords(records));

        String journeyman = "Journeyman,OVR,1,0,0,0,0\n";
        String craftsman = "Craftsman,OVR,1,0,0,0,0\n";
        String undefined = "Undefined,OVR,1,0,0,0,0\n";
        assertEquals(HEADER_ROW + journeyman + undefined  + craftsman, printOut);
    }

    @Test
    public void whenConvertingSummaryByRoleToString_createsMultipleRowsForSameRoleDifferentRegion(){
        records.add(createReportRecord("Journeyman",  "OVR",STAGE_KATA));
        records.add(createReportRecord("Journeyman", "GLR", STAGE_KATA));

        String printOut = service.convertSummaryByRoleToString(service.createSummaryCountsForRecords(records));

        String journeyGLR = "Journeyman,GLR,1,0,0,0,0\n";
        String journeyOVR = "Journeyman,OVR,1,0,0,0,0\n";
        assertEquals(HEADER_ROW + journeyOVR + journeyGLR, printOut);
    }


    private ReportRecord createReportRecord(String role, String loc, String stage){
        ReportRecord record = new ReportRecord();
        record.setRole(role);
        record.setStage(stage);
        record.setLocation(loc);

        return record;
    }
}
