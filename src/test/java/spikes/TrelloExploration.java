package spikes;

import org.junit.Test;

import static org.junit.Assert.*;
import javax.json.*;
import javax.json.Json;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class TrelloExploration {

    private static final String APP_KEY = "c823d8871c92196366ecc07a68b74ed7";
    private static final String APP_SECRET = "51a8b9aa3e33be33df3006e4f307c9baff78e5b81702de396d6ee90db8af0072";
    private static final String TOKEN = "a7967a6e18c7c707dcb4f1a13f16dfd82e3f572bb471738298fa37400428ca4b";
    private Client client = ClientBuilder.newClient();

    @Test
    public void shouldRetrieveAListOfBoards() {

        String entity = client.target("https://api.trello.com/1")
                .path("members/me/boards")
                .queryParam("key", APP_KEY)
                .queryParam("token", TOKEN)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class);

        Reader reader = new StringReader(entity);
        JsonReader jsonReader = Json.createReader(reader);
        JsonArray jsonArray = jsonReader.readArray();
        Iterator<JsonValue> jsonIterator = jsonArray.iterator();

        while (jsonIterator.hasNext()) {
            JsonValue jsonValue = jsonIterator.next();
            JsonObject jsonObject = (JsonObject) jsonValue;
            String boardName = jsonObject.getString("name");
            assertNotNull(boardName);
        }
    }


    @Test
    public void shouldCreateABoard() throws Exception {
        WebTarget createBoardWebTarget = client.target("https://api.trello.com/1")
                .path("boards")
                .queryParam("key", APP_KEY)
                .queryParam("token", TOKEN);
//                .queryParam("name", "Testing discription");
//                .queryParam("desc", "every day!");


        JsonObject postObject = Json.createObjectBuilder()
                .add("name", "Testing discription2")
                .add("desc",  "Use this for testing purposes only")
                .build();

        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("name", "Test");
        bodyParams.put("desc", "Description");

        String postDescription = createRequestBody(bodyParams);

        Response postResponse = createBoardWebTarget.request(MediaType.TEXT_PLAIN_TYPE).post(Entity.entity(postDescription, MediaType.TEXT_PLAIN));
        assertEquals(200, postResponse.getStatus());
    }

    private String createRequestBody(Map<String, String> params) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry: params.entrySet()) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append( URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return sb.toString();
    }
}
