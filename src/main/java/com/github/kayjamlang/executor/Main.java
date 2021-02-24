package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;

public class Main {

    public static void main(String[] args) throws Exception {
        KayJamLexer lexer = new KayJamLexer("{\n" +
                "var array = [\"Can\",\"you\",\"buy\",\"?\"];\n" +
                "println(String::join(array, \" \"));"+
                "}\n");
        KayJamParser parser = new KayJamParser(lexer);

        Executor executor = new Executor();
        executor.addLibrary(new MainLibrary());
        Container container = (Container) parser.readExpression();

        System.out.println(executor.execute(container));
    }
}
