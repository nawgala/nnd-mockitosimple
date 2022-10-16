package com.rnd;

import com.rnd.mock.example.Flower;
import com.rnd.mock.example.LeafNotFoundException;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Spy;

import java.util.*;

import static com.rnd.ReturnFirstArgument.returnFirstArgument;
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
    public void testCreationAndVerification() {
        // mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test(expected = RuntimeException.class)
    public void checkStubbingConcreteClasse() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
//        System.out.println(mockedList.get(0));

        //following prints "null" because get(999) was not stubbed
        System.out.println("[" + mockedList.get(999)+ "]");


        //following throws runtime exception
        System.out.println(mockedList.get(1));


        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed.
        verify(mockedList).get(0);

    }

    @Test
    public void testArgumentMatching() {
        final List mockedList = mock(List.class);
        //stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
        when(mockedList.contains(argThat(isValid()))).thenReturn(true);

        //following prints "element"
        System.out.println(mockedList.get(999));

        //you can also verify using an argument matcher
        verify(mockedList).get(anyInt());

        when(mockedList.addAll(argThat(containsTwoElement()))).thenReturn(true);
        mockedList.addAll(Arrays.asList("AAA", "BBb"));
        verify(mockedList).addAll(argThat(containsTwoElement()));

    }

    private ArgumentMatcher<List> containsTwoElement() {
        return new ArgumentMatcher<List>() {
            @Override
            public boolean matches(List collection) {
                return collection.size() == 2;
            }
        };
    }

    private ArgumentMatcher<Object> isValid() {
        return new ArgumentMatcher<Object>() {
            @Override
            public boolean matches(Object o) {
                return o instanceof Integer;
            }
        };
    }


    @Test(expected = LeafNotFoundException.class)
    public void testWhenReturnAndWhenThrow() throws Exception{
        int NUMBER_OF_LEAFS = 20;
        Flower flower = mock(Flower.class);
        when(flower.getNumberOfLeafs()).thenReturn(NUMBER_OF_LEAFS);
        when(flower.getNumberOfLeaf(0)).thenThrow(LeafNotFoundException.class);

        assertEquals(NUMBER_OF_LEAFS, flower.getNumberOfLeafs());
        doThrow(LeafNotFoundException.class).when(flower).getNumberOfLeaf(3);
        flower.getNumberOfLeaf(3);
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

    @Test
    public void shouldReturnTheSameValue() {
        FlowerFilter filterMock = mock(FlowerFilter.class);
        given(filterMock.filterNoOfFlowers(anyInt())).will(returnFirstArgument());

        int TEST_NUMBER_OF_FLOWERS = 0;
        int filteredNoOfFlowers = filterMock.filterNoOfFlowers(TEST_NUMBER_OF_FLOWERS);

        assertEquals(filteredNoOfFlowers, TEST_NUMBER_OF_FLOWERS);
    }

    @Test
    public void verityExactNumberOfInvocation() {
        final List<String> mock = mock(List.class);

        mock.add("once");

        mock.add("twice");
        mock.add("twice");

        mock.add("tries");
        mock.add("tries");
        mock.add("tries");

        verify(mock).add("once");
        verify(mock, times(2)).add("twice");
        verify(mock, times(3)).add("tries");

        verify(mock, atMost(1)).add("once");
        verify(mock, atMost(2)).add("twice");
        verify(mock, atLeastOnce()).add("tries");

        verify(mock, atMost(5)).add("tries");
        verify(mock, never()).add("with this");

    }

    @Test(expected = RuntimeException.class)
    public void stubbingException() {
        final List mock = mock(List.class);
        doThrow(RuntimeException.class).when(mock).clear();
        mock.clear();
    }

    @Test
    public void testVerification() {
        final ArrayList<Integer> list = new ArrayList<>();
        final List<Integer> spy = spy(list);


        when(spy.size()).thenReturn(100);
        spy.add(3);
        spy.add(4);

        assertEquals(100, spy.size());
        assertEquals(3, (int) spy.get(0));

        verify(spy).add(3);
        verify(spy).add(4);

    }



}
