package com.pillartechnology.trello;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ReportClientTest {

    ReportGenerationService trelloReportRecordService = mock(ReportGenerationService.class);

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(buffer);

    @Test
    public void trelloCalledWithLessThanTreeArgumentsReturnsUsage() {
        ReportClient client = new ReportClient(out);
        client.trelloReportService = trelloReportRecordService;

        client.run(new String[]{});

        assertEquals("Usage: TrelloClientReport boardId appKey appToken\n", buffer.toString());
    }


    @Test
    public void trelloClientShouldAcceptArgsToListBoards() {
        ReportClient client = new ReportClient(out);
        client.trelloReportService = trelloReportRecordService;

        client.run(new String[]{"boardId", "appKey", "appToken"});

        verify(trelloReportRecordService).generateReport("boardId");
    }
}
