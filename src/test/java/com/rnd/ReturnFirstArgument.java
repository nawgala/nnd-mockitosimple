package com.rnd;

import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ReturnFirstArgument implements Answer<Integer> {
    @Override
    public Integer answer(InvocationOnMock invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        if (arguments.length == 0) {
            throw new MockitoException("invalid argument");
        }
        return (Integer) arguments[0];
    }
    public static ReturnFirstArgument returnFirstArgument() {
        return new ReturnFirstArgument();
    }
}
