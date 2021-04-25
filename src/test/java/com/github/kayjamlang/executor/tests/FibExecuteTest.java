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

public class FibExecuteTest {

    private static Executor executor;
    private static Container container;
    private long start;

    @BeforeClass
    public static void init() throws Exception {
        KayJamLexer lexer = new KayJamLexer("{\n" +
                "fun fib(int? x): int? {\n" +
                "   return if(x<3) 1 else\n" +
                "       fib(x-1)+fib(x-2);\n" +
                "}\n" +
                "return fib(10);\n"+
                "}\n");
        KayJamParser parser = new KayJamParser(lexer);
        executor = new Executor();

        container = (Container) parser.readExpression();
    }

    @Before
    public void initTime(){
        start = System.currentTimeMillis();
    }

    @Test(timeout = 90)
    public void test() throws Exception {
        assertEquals(55, executor.execute(container));
    }

    @After
    public void end(){
        long end = System.currentTimeMillis();
        System.out.println("Fib number without Parser: "+(end-start)+" ms");
    }
}
