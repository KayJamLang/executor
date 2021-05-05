package com.github.kayjamlang.executor.tests.library;

import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;
import com.github.kayjamlang.executor.libs.main.MainLibrary;
import com.github.kayjamlang.executor.libs.main.MapClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapClassTests {

    public static Executor executor;

    @BeforeClass
    public static void init() throws Exception {
        executor = new Executor();
        executor.addLibrary(new MainLibrary());
    }

    @Test
    public void createMap() throws Exception {
        assertEquals(MapClass.class, executor.execute(
                "return map()").getClass());
    }

    @Test
    public void putAndGetTest() throws Exception {
        Object map = executor.execute("var map = map()." +
                "put(1, 123); return map");
        assertEquals(MapClass.class, map.getClass());
        assertEquals(123, executor.executeWithOldContext(
                "return map.get(1)"));
    }
}
