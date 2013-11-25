package com.pillartechnology.trello;

import com.pillartechnology.trello.exception.BoardNotFoundException;
import com.pillartechnology.trello.exception.InitializationException;
import com.pillartechnology.trello.exception.TrelloServiceException;

import javax.json.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TrelloServiceImpl implements TrelloService {

    private static final String TRELLO_URL = "https://api.trello.com/1";
    private static final String TRELLO_INIT_FILE = "/Users/ovi/.trello";
    private static String appKey;
    private static String userToken;
    private static String initFile;

    public TrelloServiceImpl(String initFile) {
        this.initFile = initFile;
    }

    private void initializeCredentials() {
        if (appKey != null && userToken != null) {
            return; // already initialized
        }
        File trelloCredentials = new File(initFile);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(trelloCredentials));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] pair = line.split("=");
                if ("appKey".equals(pair[0])) {
                    appKey = pair[1];
                }
                if ("userToken".equals(pair[0])) {
                    userToken = pair[1];
                }
            }
            if (appKey == null || userToken == null) {
                throw new InitializationException("Trello initialization file does not contain expected credentials");
            }
        } catch (FileNotFoundException fnf) {
            throw new InitializationException("Trello initialization file does not exist. Must create it manually");
        } catch (IOException ioe) {  //TODO: not covered by tests
            throw new InitializationException("Could not read from Trello initialization file. Please check its permissions");
        }
    }

    @Override
    public boolean createBoard(String boardName) throws TrelloServiceException {
        initializeCredentials();
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", appKey);
        params.put("token", userToken);
        params.put("name", boardName);
        Response response = callTrelloWithPost("boards", params);
        if (response.getStatus() != 200) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addTask(String boardName, String taskName) throws TrelloServiceException {
        initializeCredentials();
        String boardId = getIdForBoardName(boardName);
        if (boardId == null) {
            throw new BoardNotFoundException("Name given: " + boardName);
        }
        String listId = getListIdForBoardAndListName(boardId, "To Do");
       Map<String, String> params = new HashMap<String, String>();
        params.put("key", appKey);
        params.put("token", userToken);
        params.put("idList", listId);
        params.put("name", taskName);
        Response response = callTrelloWithPost("cards", params);
        if (response.getStatus() != 200) {
            return false;
        }
        return true;
    }

    public String getIdForBoardName(String myBoardName) {
        initializeCredentials();
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", appKey);
        params.put("token", userToken);
        Response response = callTrelloWithGet("members/me/boards", params);
        return getValueForKeyInJsonList("name", myBoardName, "id", response.readEntity(String.class));
    }

    public String getListIdForBoardAndListName(String boardId, String desiredListName) {
        initializeCredentials();
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", appKey);
        params.put("token", userToken);
        Response response = callTrelloWithGet("boards/" + boardId + "/lists", params);
        return getValueForKeyInJsonList("name", desiredListName, "id", response.readEntity(String.class));
    }

    // find the record that has matching desiredKey and desiredValue,
    // and return the value of the key with the named desiredTarget
    private String getValueForKeyInJsonList(String desiredKey, String desiredValue, String desiredTarget, String json) {
        Reader reader = new StringReader(json);
        JsonReader jsonReader = Json.createReader(reader);
        JsonArray jsonArray = jsonReader.readArray();
        Iterator<JsonValue> jsonIterator = jsonArray.iterator();

        String returnValue = null;
        while (jsonIterator.hasNext()) {
            JsonValue jsonValue = jsonIterator.next();
            JsonObject jsonObject = (JsonObject) jsonValue;
            String key = jsonObject.getString(desiredKey);
            String target = jsonObject.getString(desiredTarget);
            if (desiredValue.equals(key)) {
                returnValue = target;
            }
        }
        return returnValue;
    }

    private Response callTrelloWithPost(String path, Map<String, String> queryParams) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(TRELLO_URL).path(path);
        for (Map.Entry entry: queryParams.entrySet()) {
            target =  target.queryParam((String) entry.getKey(), (String) entry.getValue());
        }
        return target.request(MediaType.TEXT_PLAIN_TYPE).post(Entity.entity("", MediaType.TEXT_PLAIN));
    }

    private Response callTrelloWithGet(String path, Map<String, String> queryParams) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(TRELLO_URL).path(path);
        for (Map.Entry entry: queryParams.entrySet()) {
            target =  target.queryParam((String) entry.getKey(), (String) entry.getValue());
        }
        Invocation.Builder invocationBuilder = target.request(MediaType.TEXT_PLAIN_TYPE);
        return invocationBuilder.get();
    }
}
