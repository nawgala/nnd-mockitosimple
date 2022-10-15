package com.rnd;

import com.rnd.mock.example.Flower;
import com.rnd.mock.example.LeafNotFoundException;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Unit test for simple App.
 */
public class AppTest {


    /**
     * Rigorous Test :-)
     */
    @Test
    public void checkMock() {
        // mock creation
        List mockedList = mock(List.class);

// using mock object - it does not throw any "unexpected interaction" exception
        mockedList.add("one");
        mockedList.clear();

// selective, explicit, highly readable verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void testStub() {
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

// stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");

// the following prints "first"
        System.out.println(mockedList.get(0));

// the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));
    }

    @Test(expected = LeafNotFoundException.class)
    public void testInterfaceWithMock() throws Exception{
        int NUMBER_OF_LEAFS = 20;
        Flower flower = mock(Flower.class);
        when(flower.getNumberOfLeafs()).thenReturn(NUMBER_OF_LEAFS);
        when(flower.getNumberOfLeaf(0)).thenThrow(LeafNotFoundException.class);

        assertEquals(NUMBER_OF_LEAFS, flower.getNumberOfLeafs());
        flower.getNumberOfLeaf(0);
    }

    @Test
    public void shouldReturnGivenValueUsingBDDSemantics() {
        //given
        final Flower mocked = mock(Flower.class);
        Integer NUMBER_OF_LEAFS = 3;
        given(mocked.getNumberOfLeafs()).willReturn(NUMBER_OF_LEAFS);

        //then -> return
        when(mocked.getNumberOfLeafs()).thenReturn(10);
        assertEquals(10, mocked.getNumberOfLeafs());
    }
}
