package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.executor.libs.Library;

import java.util.List;

public class StringClass extends Library.LibClass {

    public StringClass() throws Exception {
        super("String", null);
        setCompanion(new Companion());
    }

    private static class Companion extends Library.LibObject {

        public Companion() throws Exception {
            super(null);

            //CharAt
            addFunction(new Library.LibFunction("charAt", Type.STRING, (mainContext, context) -> {
                String string = context.getVariable("source");
                Number position = context.getVariable("position");
                return String.valueOf(string.charAt(position.intValue()));
            }, new Argument(Type.STRING, "source"),
                    new Argument(Type.INTEGER, "position")));

            //Replace
            addFunction(new Library.LibFunction("replace", Type.STRING, (mainContext, context) -> {
                String string = context.getVariable("source");
                Object search = context.getVariable("search");
                Object replaceValue = context.getVariable("replaceValue");

                return string.replaceAll(search.toString(), replaceValue.toString());
            }, new Argument(Type.STRING, "source"),
                    new Argument(Type.ANY, "search"),
                    new Argument(Type.ANY, "replaceValue")));

            //Join
            addFunction(new Library.LibFunction("join", Type.STRING, (mainContext, context) -> {
                List<?> strings = context.getVariable("strings");
                String delimiter =  context.getVariable("delimiter").toString();

                StringBuilder result = new StringBuilder();
                for(Object obj: strings)
                    result.append(obj).append(delimiter);

                return result.substring(0, result.length()-1);
            }, new Argument(Type.ARRAY, "strings"),
                    new Argument(Type.ANY, "delimiter")));
        }
    }
}
