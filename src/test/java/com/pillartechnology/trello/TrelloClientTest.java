package com.pillartechnology.trello;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TrelloClientTest {

    AppService mockAppService = mock(AppService.class);
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(buffer);

    @Test
    public void informsClientIfFileDoesNotExist() throws Exception {
        TrelloClient client = new TrelloClient(null, out);

        client.run(new String[]{"foo.txt", "bazoo"});

        assertThat(buffer.toString(), containsString("File not found"));
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