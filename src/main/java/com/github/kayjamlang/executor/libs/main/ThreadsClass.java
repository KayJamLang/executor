package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.executor.libs.Library;

import java.util.concurrent.TimeUnit;

public class ThreadsClass extends Library.LibClass {

    public ThreadsClass() throws Exception {
        super("Threads", null);

        setCompanion(new Companion());
    }

    private static class Companion extends Library.LibObject {
        public Companion() {
            super(null);

            addFunction(new Library.LibFunction("sleep", (mainContext, context) -> {
                long ms = (long) context.variables.get("ms");
                TimeUnit.MILLISECONDS.sleep(ms);
                return null;
            }, new Function.Argument(Type.INTEGER, "ms")));
        }
    }
}
