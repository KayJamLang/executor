package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.libs.main.MainLibrary;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Main {

    private static KayJamParser parser;
    private static Executor executor;

    @BeforeClass
    public static void init(){
        KayJamLexer lexer = new KayJamLexer("{\n" +
                "object Test {" +
                "fun test(): int {" +
                "return 123;" +
                "}" +
                "}" +
                "" +
                "" +
                "var test = Test::test();" +
                "return test; }");
        parser = new KayJamParser(lexer);
        executor = new Executor();
    }

    @Test
    public void test() throws Exception {
        assertEquals(123, executor.execute((Container) parser.readExpression()));
    }
}
