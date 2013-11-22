package com.pillartechnology.trello;

import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TrelloClientTest {

    AppService mockAppService = mock(AppService.class);
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(buffer);

    @Test
    public void informsClientIfFileDoesNotExist() throws Exception {
        TrelloClient client = new TrelloClient(null, out);

        client.run(new String[]{"foo.txt", "bazoo"});

        assertEquals("File not found\n", buffer.toString());
    }

    @Test
    public void informsClientIfFileWasProcessedSuccessfully() throws Exception {
        TrelloClient client = new TrelloClient(mockAppService, out);
        new File("/tmp/foo.txt").createNewFile();

        client.run(new String[]{"/tmp/foo.txt", "bazoo"});

        assertEquals("File /tmp/foo.txt was imported\n", buffer.toString());
    }

    @Test
    public void informUserIfFileNotSuppliedOnCommandLine() throws Exception {
        TrelloClient client = new TrelloClient(null, out);

        client.run(new String[]{});

        assertTrue(buffer.toString().contains("Usage:"));
    }

    @Test
    public void informUserIfBoarNameMissingFromCommandLine() throws Exception {
        TrelloClient client = new TrelloClient(null, out);
        new File("/tmp/foo.txt").createNewFile();

        client.run(new String[]{"/tmp/foo.txt"});

        assertTrue(buffer.toString().contains("Usage:"));
    }

    @Test
    public void callsAppServiceToDoTheImport() throws Exception {

        TrelloClient client = new TrelloClient(mockAppService, out);
        new File("/tmp/foo.txt").createNewFile();

        client.run(new String[]{"/tmp/foo.txt", "bazoo"});

        verify(mockAppService).importFile(new File("/tmp/foo.txt"),"bazoo");
    }


}