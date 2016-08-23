package com.pillartechnology.trello;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TrelloReportService {
    private static final String TALENT_URL="https://api.trello.com/1/boards/5730953aa0854542ef8e8ccb?cards=open&card_fields=name,idList,idMembers,labels&lists=open&key=c823d8871c92196366ecc07a68b74ed7&token=ac60537bc5c86244cccec928f0689b12ad0dd437eae6836f08dc90e6f5e4e20b";
    private static final String boardId_="5730953aa0854542ef8e8ccb";
    private static final String appKey = "c823d8871c92196366ecc07a68b74ed7";
    private static final String appToken = "ac60537bc5c86244cccec928f0689b12ad0dd437eae6836f08dc90e6f5e4e20b";

    private Invocation.Builder invocationBuilder;

    private Response callTrelloWithGet() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(TALENT_URL);

//        for (Map.Entry entry: queryParams.entrySet()) {
//            target =  target.queryParam((String) entry.getKey(), (String) entry.getValue());
//        }
        invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get();
    }

    public TrelloBoard getBoard(String boardId) {
        Response response = callTrelloWithGet();
        //response.getEntity()
        //List<TrelloBoard> list = response.readEntity(new GenericType<List<TrelloBoard>>() {});
        TrelloBoard rs = response.readEntity(new GenericType<TrelloBoard>() {});
        return rs;
    }
}
