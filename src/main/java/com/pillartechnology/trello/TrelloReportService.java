package com.pillartechnology.trello;

import com.pillartechnology.trello.exception.TrelloServiceException;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TrelloReportService {
    private static final String TALENT_BASE_URL="https://api.trello.com/1/boards/";
    private static final String TALENT_URL_ARGS="?cards=open&card_fields=name,idList,idMembers,labels";
    private Invocation.Builder invocationBuilder;

    private Response callTrelloWithGet(String boardId, String appKey, String appToken) {

        Client client = ClientBuilder.newClient();
        String url= TALENT_BASE_URL + boardId + TALENT_URL_ARGS + "&key=" + appKey + "&token=" + appToken;
        WebTarget target = client.target(url);
        setInvocationBuilderIfUndefined(target.request(MediaType.APPLICATION_JSON));
        return invocationBuilder.get();
    }

    private void setInvocationBuilderIfUndefined(Invocation.Builder builder){
        if(invocationBuilder == null){
            invocationBuilder = builder;
        }
    }

    public TrelloBoard getBoard(String boardId, String appKey, String appToken) {
        Response response = callTrelloWithGet(boardId, appKey, appToken);
        if(! Response.Status.ACCEPTED.equals(response.getStatus())){
            throw new TrelloServiceException("Request failed with status: "+response.getStatus());
        }

        TrelloBoard rs = response.readEntity(TrelloBoard.class);

        for(TrelloCard card : rs.getCards()){
            System.out.print(card.getName()+" : ");
            for(TrelloLabel label : card.getLabels()){
                System.out.print(label.getName()+",");
            }
            System.out.println();
        }
        System.out.println(rs);
        return rs;
    }
}
