package com.github.kayjam.executor;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;

public class Main {

    public static void main(String[] args) throws Exception {
        KayJamLexer lexer = new KayJamLexer("{\n" +
                "class A:: Base {" +
                "   fun test():int {}" +
                "}" +
                "class Base {" +
                "   fun test():int {}" +
                "}"+
                "}\n");
        KayJamParser parser = new KayJamParser(lexer);

        Executor executor = new Executor();
        Container container = (Container) parser.readExpression();

        System.out.println(executor.execute(container));
    }
}
