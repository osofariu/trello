package com.pillartechnology.trello;

import com.pillartechnology.trello.entities.TrelloBoard;
import com.pillartechnology.trello.entities.TrelloLabel;
import com.pillartechnology.trello.exception.TrelloServiceException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

class TrelloService {

    private static final String TALENT_BASE_URL="https://api.trello.com/1/boards/";
    private Invocation.Builder invocationBuilder;

    private TrelloProperties props = TrelloProperties.getInstance();

    private Response callTrelloWithGet(String url) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        setInvocationBuilderIfUndefined(target.request(MediaType.APPLICATION_JSON));
        Response response = invocationBuilder.get();
        invocationBuilder = null;
        return response;
    }

    private void setInvocationBuilderIfUndefined(Invocation.Builder builder){
        if(invocationBuilder == null){
            invocationBuilder = builder;
        }
    }

    TrelloBoard getBoard() {
        final String TALENT_URL_ARGS="?cards=open&card_fields=name,idList,idMembers,labels&lists=open";
        final String url= TALENT_BASE_URL + props.getTrelloBoardId() + TALENT_URL_ARGS + "&key=" + props.getTrelloAppKey() + "&token=" + props.getTrelloAppToken();

        Response response = callTrelloWithGet(url);
        if( Response.Status.OK.getStatusCode() != response.getStatus()) {
            throw new TrelloServiceException("Request failed with status: "+response.getStatus());
        }
        return response.readEntity(TrelloBoard.class);
    }

    List<TrelloLabel> getLabels() {
        String url = TALENT_BASE_URL + props.getTrelloBoardId() + "/labels?" + "&key=" + props.getTrelloAppKey() + "&token=" + props.getTrelloAppToken();
        Response response = callTrelloWithGet(url);
        if (Response.Status.OK.getStatusCode() != response.getStatus()) {
            throw new TrelloServiceException("Request failed with status: " + response.getStatus());
        }

        return response.readEntity(new GenericType<List<TrelloLabel>>(){});
    }
}
