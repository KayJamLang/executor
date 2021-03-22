package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Argument;
import com.github.kayjamlang.core.KayJamVersion;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.libs.Library;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            return false;
        }, new Argument(Type.ANY, "value")));

        functions.add(new LibFunction("print", (mainContext, context) -> {
            output.print(context.variables.get("value"));
            return false;
        }, new Argument(Type.ANY, "value")));

        functions.add(new LibNamedFunction("thread", (mainContext, context, expression) -> new Thread(() -> {
            try {
                Context ctx = new Context(context.parent,
                        context, true);

                mainContext.executor
                        .provide(expression, ctx, ctx);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).start()));

        functions.add(new LibNamedFunction("async", (mainContext, context, expression) -> CompletableFuture.runAsync(() -> {
            try {
                Context ctx = new Context(context.parent,
                        context, true);

                mainContext.executor
                        .provide(expression, ctx, ctx);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        })));

        functions.add(new LibFunction("match", (mainContext, context) -> {
            String pattern = (String) context.variables.get("pattern");
            String string = (String) context.variables.get("string");

            Matcher matcher = Pattern.compile(pattern)
                    .matcher(string);

            if (!matcher.find())
                return false;

            ArrayClass arrayClass = new ArrayClass();
            arrayClass.children.clear();

            List<Object> array = new LinkedList<>();
            arrayClass.addVariable("array", array);
            array.add(0, matcher.group(0));
            for (int i = 0; i < matcher.groupCount(); i++) {
                array.add(i + 1, matcher.group(i + 1));
            }

            return arrayClass;
        }, new Argument(Type.STRING, "pattern"),
                new Argument(Type.STRING, "string")));

        functions.add(new LibFunction("matchAll", (mainContext, context) -> {
            String pattern = (String) context.variables.get("pattern");
            String string = (String) context.variables.get("string");

            Matcher matcher = Pattern.compile(pattern)
                    .matcher(string);

            ArrayClass result = new ArrayClass();
            result.children.clear();

            List<Object> resultArray = new LinkedList<>();
            result.addVariable("array", resultArray);
            while (matcher.find()) {
                ArrayClass arrayClass = new ArrayClass();
                arrayClass.children.clear();

                List<Object> array = new LinkedList<>();
                arrayClass.addVariable("array", array);
                array.add(0, matcher.group(0));
                for (int i = 0; i < matcher.groupCount(); i++) {
                    array.add(i + 1, matcher.group(i + 1));
                }

                resultArray.add(resultArray.size(), arrayClass);
            }

            return resultArray.size() == 0 ? false : result;
        }, new Argument(Type.STRING, "pattern"),
                new Argument(Type.STRING, "string")));

        functions.add(new LibFunction("getKayJamVersion", (mainContext, context) -> KayJamVersion.VERSION_CODE));

        classes.put("String", new StringClass());
        classes.put("Threads", new ThreadsClass());
        classes.put("Math", new MathClass());
        classes.put("map", new MapClass());
        classes.put("array", new ArrayClass());
    }

    public interface Output {
        void print(Object value);

        void println(Object value);
    }
}