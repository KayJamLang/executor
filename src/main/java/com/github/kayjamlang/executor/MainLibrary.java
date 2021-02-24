package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.executor.libs.Library;

import java.util.List;

public class MainLibrary extends Library {
    public MainLibrary() throws Exception {
        this(new Output() {

            @Override
            public void print(Object value) {
                System.out.print(value);
            }

            @Override
            public void println(Object value) {
                System.out.println(value);
            }
        });
    }

    public MainLibrary(Output output) throws Exception {
        functions.add(new LibFunction("println", (mainContext, context) -> {
            output.println(context.variables.get("value"));
            return null;
        }, new Function.Argument(Type.ANY, "value")));

        functions.add(new LibFunction("print", (mainContext, context) -> {
            output.print(context.variables.get("value"));
            return null;
        }, new Function.Argument(Type.ANY, "value")));

        classes.put("String", new LibClass("String", libClass ->
                libClass.setCompanion(new LibObject(object -> {
                    //CharAt
                    object.addFunction(new LibFunction("charAt", (mainContext, context) -> {
                        String string = (String) context.variables.get("source");
                        Number position = (Number) context.variables.get("position");
                        return String.valueOf(string.charAt(position.intValue()));
                    }, new Function.Argument(Type.STRING, "source"),
                       new Function.Argument(Type.INTEGER, "position")));

                    //Replace
                    object.addFunction(new LibFunction("replace", (mainContext, context) -> {
                        String string = (String) context.variables.get("source");
                        Object search = context.variables.get("search");
                        Object replaceValue = context.variables.get("replaceValue");

                        return string.replaceAll(search.toString(), replaceValue.toString());
                    }, new Function.Argument(Type.STRING, "source"),
                            new Function.Argument(Type.ANY, "search"),
                            new Function.Argument(Type.ANY, "replaceValue")));

                    //Join
                    object.addFunction(new LibFunction("join", (mainContext, context) -> {
                        List<?> strings = (List<?>) context.variables.get("strings");
                        String delimiter =  context.variables.get("delimiter").toString();

                        StringBuilder result = new StringBuilder();
                        for(Object obj: strings)
                            result.append(obj).append(delimiter);

                        return result.substring(0, result.length()-1);
                    }, new Function.Argument(Type.ARRAY, "strings"),
                            new Function.Argument(Type.ANY, "delimiter")));
                }))));
    }

    public interface Output{
        void print(Object value);
        void println(Object value);
    }
}
