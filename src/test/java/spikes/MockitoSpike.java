package spikes;

import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MockitoSpike {

    @Test
    public void verifyInteractionsExampleFromDocs() throws Exception {

        List mockedList = mock(List.class);
        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void verifyInteractionsWithStubExamplesFromDocs() throws Exception {
        List mockedList = mock(List.class);
        when(mockedList.get(0)).thenReturn("first");
        assertEquals("first", mockedList.get(0));
        assertNull(mockedList.get(99));
    }

    @Test
    public void verifyInteractionsThatAlwaysReturnExpectedValuesFromDocs() throws  Exception {
        List mockedList = mock(List.class);
        when(mockedList.get(anyInt())).thenReturn("hello");
        assertEquals("hello", mockedList.get(3));
        assertEquals("hello", mockedList.get(234));
    }
}
