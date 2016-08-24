package com.pillartechnology.trello;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrelloClientReportTest {

    TrelloReportService mockAppService = mock(TrelloReportService.class);
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(buffer);

    @Test
    public void trelloCalledWithLessThanTreeArgumentsReturnsUsage() {
        TrelloReportClient client = new TrelloReportClient(mockAppService, out);
        client.run(new String[]{});
        assertEquals("Usage: TrelloClientReport boardId appKey appToken\n", buffer.toString());
    }


    @Test
    public void trelloClientShouldAcceptArgsToListBoards() {
        TrelloReportClient client = new TrelloReportClient(mockAppService, out);
        client.run(new String[]{"boardId", "appKey", "appToken"});
        verify(mockAppService).getBoard("boardId", "appKey", "appToken");
    }
}
