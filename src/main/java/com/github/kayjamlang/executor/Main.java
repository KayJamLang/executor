package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.executor.libs.main.MainLibrary;

public class Main {

    public static void main(String[] args) throws Exception {
        KayJamLexer lexer = new KayJamLexer(
                "{" +
                        "class Element {\n" +
                        "   var name = \"div\";\n" +
                        "   var children = \"\";\n" +
                        "   \n" +
                        "   constructor(name){\n" +
                        "      this.name = name;\n" +
                        "   }\n" +
                        "   \n" +
                        "   fun addChild(Element element): Element{\n" +
                        "      this.children = this.children + element.build();\n" +
                        "      return this;\n" +
                        "   }\n" +
                        "   \n" +
                        "   fun addChild(string element): Element{\n" +
                        "      this.children = this.children + element;\n" +
                        "      return this;\n" +
                        "   }\n" +
                        "   \n" +
                        "   fun build(): string{      \n" +
                        "      return \"<\"+this.name+\">\"+this.children+\"</\"+this.name+\">\";\n" +
                        "   }\n" +
                        "}\n" +
                        "\n" +
                        "class div: Element {\n" +
                        "   constructor(){\n" +
                        "      this.name = \"div\";\n" +
                        "   }\n" +
                        "}" +
                        "" +
                        "var test = div();\n" +
                        "test.addChild(\"test\");\n" +
                        "print(div()\n" +
                        ".addChild(test)\n" +
                        ".build());" +
                        "" +
                        "}");
        KayJamParser parser = new KayJamParser(lexer);

        Executor executor = new Executor();
        executor.addLibrary(new MainLibrary());
        Container container = (Container) parser.readExpression();

        System.out.println(executor.execute(container));
    }
}
