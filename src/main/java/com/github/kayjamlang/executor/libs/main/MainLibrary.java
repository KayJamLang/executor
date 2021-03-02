package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.executor.libs.Library;

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

        classes.put("String", new StringClass());
        classes.put("Threads", new ThreadsClass());
    }

    public interface Output{
        void print(Object value);
        void println(Object value);
    }
}
