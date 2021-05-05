package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetExpressionTest {

    private static Executor executor;

    @BeforeClass
    public static void init() {
        executor = new Executor();
    }

    @Test
    public void test() throws Exception {
        assertEquals(123, executor.execute("return [123][0]"));
    }
}
