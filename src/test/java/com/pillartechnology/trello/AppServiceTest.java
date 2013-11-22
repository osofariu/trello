package com.pillartechnology.trello;

import org.junit.Test;
import java.io.File;
import java.io.PrintWriter;
import static org.mockito.Mockito.*;

public class AppServiceTest {

    public static final String BOARD_NAME = "The Board";
    TrelloService trelloServiceMock = mock(TrelloService.class);
    AppService appService = new AppServiceImpl(trelloServiceMock);


    @Test
    public void shouldCreateBoard() throws Exception {
        File tasksFile = createFileWithTasks("/tmp/trello.tasks");
        String boardName = "test board";

        appService.importFile(tasksFile, boardName);

        verify(trelloServiceMock).createBoard(boardName);
    }

    @Test
    public void shouldImportAFileWithOneTask() throws Exception {
        File tasksFile = createFileWithTasks("/tmp/trello.tasks", "A Task");

        appService.importFile(tasksFile, BOARD_NAME);

        verifyTasksAdded(BOARD_NAME, "A Task");
    }

    @Test
    public void shouldImportAFileWithTwoTasks() throws Exception {
        File tasksFile = createFileWithTasks("/tmp/trello.tasks", "First Task", "Second Task");

        appService.importFile(tasksFile, BOARD_NAME);

        verifyTasksAdded(BOARD_NAME, "First Task", "Second Task");
    }

    @Test
    public void shouldIgnoreBlankLines() throws Exception {
        File tasksFile = createFileWithTasks("/tmp/trello.tasks","", "foo");

        appService.importFile(tasksFile, BOARD_NAME);

        verifyTasksAdded(BOARD_NAME, "foo");
        verifyTasksNotAdded(BOARD_NAME, "");
    }

    @Test
    public void shouldIgnoreLinesThatContainOnlyWhiteSpace() throws Exception {
        File tasksFile = createFileWithTasks("/tmp/trello.tasks", " ", "foo", "\t", " \t");

        appService.importFile(tasksFile, BOARD_NAME);

        verifyTasksAdded(BOARD_NAME, "foo");
        verifyTasksNotAdded(BOARD_NAME, " ", "\t", " \t");
    }

    private File createFileWithTasks(String fileName, String...tasks) throws Exception {
        File file = new File(fileName);
        PrintWriter pw = new PrintWriter(file);
        for (String task: tasks) {
            pw.println(task);
        }
        pw.close();
        return file;
    }

    private void verifyBoardCreated(String boardName) {
       verify(trelloServiceMock).createBoard(boardName);
    }

    private void verifyTasksAdded(String boardName, String... tasks) {
        for (String task:tasks) {
            verify(trelloServiceMock).addTask(boardName, task);
        }
    }

    private void verifyTasksNotAdded(String boardName, String... tasks) {
        for (String task:tasks) {
            verify(trelloServiceMock,never()).addTask(boardName, task);
        }
    }
}
