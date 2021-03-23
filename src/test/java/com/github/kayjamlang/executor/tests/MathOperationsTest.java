package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.executor.Executor;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MathOperationsTest {

    private static KayJamParser parser;
    private static Executor executor;
    private long start;

    @BeforeClass
    public static void init(){
        KayJamLexer lexer = new KayJamLexer("{\n" +
                "return (2*2+2)/2\n"+
                "}\n");
        parser = new KayJamParser(lexer);
        executor = new Executor();
    }

    @Before
    public void initTime(){
        start = System.currentTimeMillis();
    }

    @Test(timeout = 90)
    public void test() throws Exception {
        assertEquals(3.0, executor.execute((Container) parser.readExpression()));
    }

    @After
    public void end(){
        long end = System.currentTimeMillis();
        System.out.println("Math: "+(end-start)+" ms");
    }
}
