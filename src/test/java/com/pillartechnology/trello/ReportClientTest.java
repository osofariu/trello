package com.pillartechnology.trello;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ReportClientTest {

    private ReportGenerationService reportGenerationService;
    private ReportClient reportClient;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private PrintStream out = new PrintStream(buffer);

    @Before
    public void setUp() {
        reportGenerationService = mock(ReportGenerationService.class);
        reportClient = new ReportClient(out);
        reportClient.reportGenerationService = reportGenerationService;

    }
    @Test
    public void trelloCalledWithLessThanTreeArgumentsReturnsUsage() {
        reportClient.run(new String[]{});

        assertEquals("Usage: TrelloClientReport propertyFile\n", buffer.toString());
    }


    @Test
    public void trelloClientShouldAcceptArgsToListBoards() {

        reportClient.run(new String[]{"TalentManagement.properties"});

        verify(reportGenerationService).generateReport();
    }
}
