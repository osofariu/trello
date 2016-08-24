package com.pillartechnology.trello;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TrelloClientReportTest {

    TrelloReportService mockAppService = mock(TrelloReportService.class);
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(buffer);

    @Test
    public void trelloClientShouldAcceptArgsToListBoards() {
        TrelloReportClient client = new TrelloReportClient(mockAppService, out);
        client.run(new String[]{"boardId", "appKey", "token"});
        verify(mockAppService).getBoard("boardID", "appKey", "token");
    }
}
