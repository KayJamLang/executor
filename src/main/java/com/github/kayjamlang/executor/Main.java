package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.executor.libs.main.MainLibrary;

public class Main {

    public static void main(String[] args) throws Exception {
        KayJamLexer lexer = new KayJamLexer(
                "{" +
                        "thread -> println(123);" +
                        "}");
        KayJamParser parser = new KayJamParser(lexer);

        Executor executor = new Executor();
        executor.addLibrary(new MainLibrary());
        Container container = (Container) parser.readExpression();

        System.out.println(executor.execute(container));
    }
}
