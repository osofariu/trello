package integration;

import com.pillartechnology.trello.*;
import com.pillartechnology.trello.exception.InitializationException;
import org.junit.Test;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.*;
import static org.junit.Assert.*;

public class TrelloServiceTest {

    private static final String BOARD_NAME = "TrelloExploration Test Board2";


    @Test
    public void shouldThrowExceptionWhenInitializationFileDoesNotExist() throws Exception {
        preserveInitFile();
        removeInitFile();
        try {
            TrelloServiceImpl trelloService = new TrelloServiceImpl();
            fail("Should have gotten an exception");
        } catch (InitializationException rte) {
        }
        restoreInitFile();
    }

    @Test
    public void shouldThrowExceptionWhenAppKeyAndUserTokenAreMissing() throws Exception {
        preserveInitFile();
        setInitFileToEmpty();
        try {
            TrelloServiceImpl trelloService = new TrelloServiceImpl();
            fail("Should have gotten a Runtime Exception");
        } catch (InitializationException rte) {}
        restoreInitFile();
    }

    @Test
    public void shouldNotCreateBoardWhenBoardNameIsEmpty() throws Exception {
        TrelloService trelloService = new TrelloServiceImpl();
        assertFalse(trelloService.createBoard(""));
    }

    // no exception means it worked (response was 200)
    @Test
    public void shouldCreateBoardWhenAskedToDoSoWithValidParameters() throws Exception {
        TrelloService trelloService = new TrelloServiceImpl();
        assertTrue(trelloService.createBoard(BOARD_NAME));
    }

    // assumes that board already exists..
    @Test
    public void shouldCreateATaskWhenANonEmptyTaskNameIsGiven() throws Exception {
        TrelloServiceImpl trelloService = new TrelloServiceImpl();
        if (trelloService.getIdForBoardName(BOARD_NAME) == null) {
            trelloService.createBoard(BOARD_NAME);
        }
        trelloService.addTask(BOARD_NAME, "Hello there. This is the first task");
    }

    private void preserveInitFile() throws Exception {
        Path srcPath = FileSystems.getDefault().getPath("/Users/ovi", ".trello");
        Path destPath = FileSystems.getDefault().getPath("/Users/ovi", ".trello_sav");
        Files.copy(srcPath, destPath, REPLACE_EXISTING);
    }

    private void restoreInitFile() throws Exception {
        Path srcPath = FileSystems.getDefault().getPath("/Users/ovi", ".trello_sav");
        Path destPath = FileSystems.getDefault().getPath("/Users/ovi", ".trello");
        Files.copy(srcPath, destPath, REPLACE_EXISTING);
    }

    private void removeInitFile() throws Exception {
        File initFile = new File("/Users/ovi/.trello");
        initFile.delete();
    }

    private void setInitFileToEmpty() throws Exception {
        File initFileBefore = new File("/Users/ovi/.trello");
        initFileBefore.delete();
        File initFileAfter = new File("/Users/ovi/.trello");
        initFileAfter.createNewFile();
    }
}
