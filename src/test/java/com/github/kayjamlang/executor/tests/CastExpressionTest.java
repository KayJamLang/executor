package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import org.junit.BeforeClass;
import org.junit.Test;

public class CastExpressionTest {

    private static Executor executor;

    @BeforeClass
    public static void init() throws Exception {
        executor = new Executor();
    }

    @Test(expected = KayJamRuntimeException.class)
    public void ifNotAccept() throws Exception {
        executor.execute("123 as string");
    }

    @Test
    public void ifAccept() throws Exception {
        executor.execute("123 as double");
    }
}
