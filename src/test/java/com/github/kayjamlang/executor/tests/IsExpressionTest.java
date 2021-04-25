package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsExpressionTest {

    private static Executor executor;

    @BeforeClass
    public static void init() {
        executor = new Executor();
    }

    @Test
    public void ifNotAccept() throws Exception {
        assertFalse((Boolean) executor.execute("return 123 is string"));
    }

    @Test
    public void ifAccept() throws Exception {
        assertTrue((Boolean) executor.execute("return 123 is int"));
    }
}
