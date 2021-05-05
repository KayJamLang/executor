package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.main.MainLibrary;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionCallTests {

    public static Executor executor;

    @BeforeClass
    public static void init() throws Exception {
        executor = new Executor();
        executor.addLibrary(new MainLibrary());
        executor.execute("fun test(): int {" +
                "return 8745364;" +
                "}" +
                "fun errorFunction(): void {" +
                "return 100;" +
                "}");

    }

    @Test
    public void call() throws Exception {
        assertEquals(8745364, executor.executeWithOldContext(
                "return test()"));
    }

    @Test(expected = KayJamNotFoundException.class)
    public void callUnknown() throws Exception {
        executor.executeWithOldContext("return unknown()");
    }

    @Test(expected = KayJamRuntimeException.class)
    public void callErrorFunction() throws Exception {
        Object value = executor.executeWithOldContext("return errorFunction()");
        System.out.println("Oops.. "+value);
    }
}
