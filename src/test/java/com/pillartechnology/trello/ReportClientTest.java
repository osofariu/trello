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

        assertEquals("Usage: TrelloClientReport boardId appKey appToken\n", buffer.toString());
    }


    @Test
    public void trelloClientShouldAcceptArgsToListBoards() {
        when(reportGenerationService.generateReport(Mockito.any(String.class))).thenReturn("");

        reportClient.run(new String[]{"boardId", "appKey", "appToken"});

        verify(reportGenerationService).generateReport("boardId");
    }
}
