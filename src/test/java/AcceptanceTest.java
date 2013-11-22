import com.pillartechnology.trello.AppService;
import com.pillartechnology.trello.AppServiceImpl;
import com.pillartechnology.trello.TrelloClient;
import com.pillartechnology.trello.TrelloServiceFake;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

public class AcceptanceTest {

    // can accept board name and file name
    // create a Client
    @Test
    public void canCreateAndUseClientToImportTasksIntoTrelloBoard() throws Exception {
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBuffer);
        File boardTasks = new File("/tmp/boardTasks.txt");
        String[] tasks = new String[] {"Build CI", "Order Pairing Stations", "Practice a Kata"};
        PrintWriter writer = new PrintWriter(new FileWriter(boardTasks));
        for (String task: tasks) {
            writer.println(task);
        }
        writer.close();

        TrelloServiceFake serviceFake = new TrelloServiceFake();
        AppService appService = new AppServiceImpl(serviceFake);
        TrelloClient client = new TrelloClient(appService, out);

        client.run(new String[] {boardTasks.toString(), "test board"});

        assertNotNull(serviceFake.board("test board"));
        List<String> boardTaskNames = serviceFake.board("test board");
        assertArrayEquals(tasks, boardTaskNames.toArray());
        assertEquals("File /tmp/boardTasks.txt was imported\n", outBuffer.toString());
    }
}
