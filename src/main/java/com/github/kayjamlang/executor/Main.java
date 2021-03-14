package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.executor.libs.main.MainLibrary;

public class Main {

    public static void main(String[] args) throws Exception {
        KayJamLexer lexer = new KayJamLexer(
                "{" +
                        "class A {" +
                        "fun test(): int{" +
                        "return 123;" +
                        "}" +
                        "}" +
                        "class B: A {}" +
                        "test(B());" +
                        "" +
                        "fun test(A clazz){" +
                        "println(clazz.test());" +
                        "}" +
                        "}");
        KayJamParser parser = new KayJamParser(lexer);

        Executor executor = new Executor();
        executor.addLibrary(new MainLibrary());
        Container container = (Container) parser.readExpression();

        System.out.println(executor.execute(container));
    }
}
