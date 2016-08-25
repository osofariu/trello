package com.pillartechnology.trello;

import com.pillartechnology.trello.exception.TrelloServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    TrelloService trelloService;

    @Mock
    private Invocation.Builder invocationBuilder;


    @Test(expected = TrelloServiceException.class)
    public void givenAnUnsuccessfulResponseFromTrelloThrowAnExceptionToAlertUser() {
        when(invocationBuilder.get()).thenReturn(Response.serverError().build());
        trelloService.getBoard("1");
    }
}
