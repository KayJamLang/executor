package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.executor.Executor;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FibTest {

    private static KayJamParser parser;
    private static Executor executor;
    private long start;

    @BeforeClass
    public static void init(){
        KayJamLexer lexer = new KayJamLexer("{\n" +
                "fun fib(int x): int {\n" +
                "   return if(x<3) 1 else\n" +
                "       fib(x-1)+fib(x-2);\n" +
                "}\n" +
                "return fib(10);\n"+
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
        assertEquals(55L, executor.execute((Container) parser.readExpression()));
    }

    @After
    public void end(){
        long end = System.currentTimeMillis();
        System.out.println("Fib number with Parser: "+(end-start)+" ms");
    }
}
