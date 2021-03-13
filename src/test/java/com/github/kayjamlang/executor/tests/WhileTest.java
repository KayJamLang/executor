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

public class WhileTest {

    private static Executor executor;
    private static Container container;

    @BeforeClass
    public static void init() throws Exception {
        KayJamLexer lexer = new KayJamLexer("{\n" +
                "var test = 0;" +
                "while(test<10){" +
                "test = test+1;" +
                "var tt = 1;" +
                "}\n"+
                "}\n");
        KayJamParser parser = new KayJamParser(lexer);
        executor = new Executor();
        executor.addLibrary(new MainLibrary());

        container = (Container) parser.readExpression();
    }

    @Test
    public void test() throws Exception {
        executor.execute(container);
    }
}
