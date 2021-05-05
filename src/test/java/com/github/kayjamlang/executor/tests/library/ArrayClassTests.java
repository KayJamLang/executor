package com.github.kayjamlang.executor.tests.library;

import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.libs.main.ArrayClass;
import com.github.kayjamlang.executor.libs.main.MainLibrary;
import com.github.kayjamlang.executor.libs.main.MapClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayClassTests {

    public static Executor executor;

    @BeforeClass
    public static void init() throws Exception {
        executor = new Executor();
        executor.addLibrary(new MainLibrary());
    }

    @Test
    public void createArray() throws Exception {
        assertEquals(ArrayClass.class, executor.execute(
                "return []").getClass());
    }

    @Test
    public void createArrayClass() throws Exception {
        assertEquals(ArrayClass.class, executor.execute(
                "return array()").getClass());
    }

    @Test
    public void createAndGetTest() throws Exception {
        Object map = executor.execute("var array = [123, 132];" +
                "return array");
        assertEquals(ArrayClass.class, map.getClass());
        assertEquals(132, executor.executeWithOldContext(
                "return array.get(1)"));
    }

    @Test
    public void createPushAndGetTest() throws Exception {
        Object map = executor.execute("var array = [123];" +
                "array.add(132);" +
                "return array");
        assertEquals(ArrayClass.class, map.getClass());
        assertEquals(132, executor.executeWithOldContext(
                "return array.get(1)"));
    }
}
