package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.KayJamVersion;
import com.github.kayjamlang.core.Type;
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
        functions.add(new LibFunction("println", Type.VOID, (mainContext, context) -> {
            output.println(context.getVariable("value"));
            return false;
        }, new Argument(Type.ANY, "value")));

        functions.add(new LibFunction("print", Type.VOID, (mainContext, context) -> {
            output.print(context.getVariable("value"));
            return false;
        }, new Argument(Type.ANY, "value")));

        functions.add(new LibNamedFunction("thread", (mainContext, context, expression) -> new Thread(()->{
            try {
                Context ctx = new Context(context.parentContext.parent,
                        context.parentContext, true);

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

        functions.add(new LibFunction("match", Type.ARRAY, (mainContext, context) -> {
            String pattern = context.getVariable("pattern");
            String string = context.getVariable("string");

            Matcher matcher = Pattern.compile(pattern)
                    .matcher(string);

            if(!matcher.find())
                return false;

            List<Object> array = new LinkedList<>();
            array.add(0, matcher.group(0));
            for (int i = 0; i < matcher.groupCount(); i++) {
                array.add(i+1, matcher.group(i+1));
            }

            return ArrayClass.create(mainContext.executor, array);
        }, new Argument(Type.STRING, "pattern"),
                new Argument(Type.STRING, "string")));

        functions.add(new LibFunction("matchAll", Type.ARRAY, (mainContext, context) -> {
            String pattern = context.getVariable("pattern");
            String string = context.getVariable("string");

            Matcher matcher = Pattern.compile(pattern)
                    .matcher(string);


            List<Object> resultArray = new LinkedList<>();
            while(matcher.find()) {
                List<Object> array = new LinkedList<>();
                array.add(0, matcher.group(0));
                for (int i = 0; i < matcher.groupCount(); i++) {
                    array.add(i + 1, matcher.group(i + 1));
                }

                resultArray.add(resultArray.size(), ArrayClass.create(mainContext.executor,
                        array));
            }

            return resultArray.size()==0?false:ArrayClass
                    .create(mainContext.executor, resultArray);
        }, new Argument(Type.STRING, "pattern"),
                new Argument(Type.STRING, "string")));

        functions.add(new LibFunction("getKayJamVersion", Type.INTEGER, (mainContext, context) ->
                KayJamVersion.VERSION_CODE));

        classes.put("String", new StringClass());
        classes.put("Threads", new ThreadsClass());
        classes.put("Math", new MathClass());
        classes.put("map", new MapClass());
        classes.put("array", new ArrayClass());

        addFunction(new LibFunction("rand", Type.INTEGER, (mainContext, context) -> {
            int min = context.getVariable("min");
            int max = context.getVariable("max");

            max -= min;
            return (int) (Math.random() * ++max) + min;
        }, new Argument(Type.INTEGER, "min"),
                new Argument(Type.INTEGER, "max")));
    }

    public interface Output{
        void print(Object value);
        void println(Object value);
    }
}
