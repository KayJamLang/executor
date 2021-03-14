package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Argument;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.executor.LibraryUtils;
import com.github.kayjamlang.executor.libs.Library;

import java.util.List;

public class StringClass extends Library.LibClass {

    public StringClass() throws Exception {
        super("String", null);
        setCompanion(new Companion());
    }

    private static class Companion extends Library.LibObject{

        public Companion() {
            super(null);

            //CharAt
            addFunction(new Library.LibFunction("charAt", (mainContext, context) -> {
                String string = (String) context.variables.get("source");
                Number position = (Number) context.variables.get("position");
                return String.valueOf(string.charAt(position.intValue()));
            }, new Argument(Type.STRING, "source"),
                    new Argument(Type.INTEGER, "position")));

            //Replace
            addFunction(new Library.LibFunction("replace", (mainContext, context) -> {
                String string = (String) context.variables.get("source");
                Object search = context.variables.get("search");
                Object replaceValue = context.variables.get("replaceValue");

                return string.replaceAll(search.toString(), replaceValue.toString());
            }, new Argument(Type.STRING, "source"),
                    new Argument(Type.ANY, "search"),
                    new Argument(Type.ANY, "replaceValue")));

            //Join
            addFunction(new Library.LibFunction("join", (mainContext, context) -> {
                List<?> strings = (List<?>) context.variables.get("strings");
                String delimiter =  context.variables.get("delimiter").toString();

                StringBuilder result = new StringBuilder();
                for(Object obj: strings)
                    result.append(obj).append(delimiter);

                return result.substring(0, result.length()-1);
            }, new Argument(Type.ARRAY, "strings"),
                    new Argument(Type.ANY, "delimiter")));

            //PregMatch
            addFunction(new Library.LibFunction("matches", (mainContext, context) -> {
                String pattern = (String) context.variables.get("pattern");
                String content = (String) context.variables.get("content");

                return content.matches(pattern);
            }, new Argument(Type.STRING, "pattern"),
                    new Argument(Type.STRING, "content")));
        }
    }
}
