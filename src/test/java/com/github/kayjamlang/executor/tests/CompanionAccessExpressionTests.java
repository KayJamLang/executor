package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;
import com.github.kayjamlang.executor.libs.main.MainLibrary;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompanionAccessExpressionTests {

    public static Executor executor;

    @BeforeClass
    public static void init() throws Exception {
        executor = new Executor();
        executor.addLibrary(new MainLibrary());
        executor.execute("object Test {" +
                "var test = 443355;" +
                "fun test(): int {" +
                "return test;" +
                "}" +
                "}");

    }

    @Test
    public void variableLink() throws Exception {
        assertEquals(443355, executor.executeWithOldContext(
                "return Test::test"));
    }

    @Test(expected = KayJamNotFoundException.class)
    public void unknownVariableLink() throws Exception {
        assertEquals(443355, executor.executeWithOldContext(
                "return Test::unknown"));
    }

    @Test
    public void callFunction() throws Exception {
        assertEquals(443355, executor.executeWithOldContext(
                "return Test::test()"));
    }

    @Test(expected = KayJamNotFoundException.class)
    public void unknownCallFunction() throws Exception {
        assertEquals(443355, executor.executeWithOldContext(
                "return Test::unknown()"));
    }
}
