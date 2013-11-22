package integration;

import com.pillartechnology.trello.TrelloService;
import com.pillartechnology.trello.TrelloServiceImpl;
import com.pillartechnology.trello.exception.InitializationException;
import org.junit.Test;

import java.io.File;

import static java.io.File.createTempFile;
import static org.junit.Assert.*;

public class TrelloServiceTest {

    private static final String BOARD_NAME = "TrelloExploration Test Board2";
    private static final String INIT_FILE = "/Users/ovi/.trello";


    @Test(expected = InitializationException.class)
    public void shouldThrowExceptionWhenInitializationFileDoesNotExist() throws Exception {
        String tempInitFile = "does-not-exist";
        new TrelloServiceImpl(tempInitFile);
    }

    @Test
    public void shouldThrowExceptionWhenAppKeyAndUserTokenAreMissing() throws Exception {
        String tempInitFile = makeTempFile();
        try {
            new TrelloServiceImpl(tempInitFile);
            fail("Should have gotten an exception");
        } catch (InitializationException rte) {
            removeInitFile(tempInitFile);
        }
    }

    @Test
    public void shouldNotCreateBoardWhenBoardNameIsEmpty() throws Exception {
        TrelloService trelloService = new TrelloServiceImpl(INIT_FILE);
        assertFalse(trelloService.createBoard(""));
    }

    @Test
    public void shouldCreateBoardWhenAskedToDoSoWithValidParameters() throws Exception {
        TrelloService trelloService = new TrelloServiceImpl(INIT_FILE);
        assertTrue(trelloService.createBoard(BOARD_NAME));
    }

    @Test
    public void shouldCreateATaskWhenANonEmptyTaskNameIsGiven() throws Exception {
        TrelloServiceImpl trelloService = new TrelloServiceImpl(INIT_FILE);
        if (trelloService.getIdForBoardName(BOARD_NAME) == null) {
            trelloService.createBoard(BOARD_NAME);
        }
        trelloService.addTask(BOARD_NAME, "Hello there. This is the first task");
    }

    private void removeInitFile(String initFileName) throws Exception {
        File initFile = new File(initFileName);
        initFile.delete();
    }

    private String makeTempFile() throws Exception  {
        File directory = new File("/tmp");
        File tempFile = createTempFile("_trello", "test", directory);
        return tempFile.getName();
    }
}
