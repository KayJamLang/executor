package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.libs.main.MainLibrary;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NullableTest {

    private static Executor executor;
    private static Container container;
    private long start;

    @BeforeClass
    public static void init() throws Exception {
        KayJamLexer lexer = new KayJamLexer("{\n" +
                "fun f(string? value): string{" +
                "   return if(value==null) \"nullable\" else value;" +
                "}" +
                "return f(null);"+
                "}\n");
        KayJamParser parser = new KayJamParser(lexer);
        executor = new Executor();
        executor.addLibrary(new MainLibrary());

        container = (Container) parser.readExpression();
    }

    @Before
    public void initTime(){
        start = System.currentTimeMillis();
    }

    @Test
    public void test() throws Exception {
        assertEquals("nullable", executor.execute(container));
    }

    @After
    public void end(){
        long end = System.currentTimeMillis();
        System.out.println("Hello World without Parser: "+(end-start)+" ms");
    }
}
