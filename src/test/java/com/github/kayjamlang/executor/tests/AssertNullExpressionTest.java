package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.main.MainLibrary;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AssertNullExpressionTest {

    private static Executor executor;

    @BeforeClass
    public static void init() throws Exception {
        executor = new Executor();
    }

    @Test(expected = KayJamRuntimeException.class)
    public void ifNull() throws Exception {
        executor.execute("null!");
    }

    @Test
    public void ifNotNull() throws Exception {
        executor.execute("123!");
    }
}
