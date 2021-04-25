package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.executor.libs.Library;

import java.util.concurrent.TimeUnit;

public class ThreadsClass extends Library.LibClass {

    public ThreadsClass() throws Exception {
        super("Threads", null);

        setCompanion(new Companion());
    }

    private static class Companion extends Library.LibObject {
        public Companion() throws Exception {
            super(null);

            addFunction(new Library.LibFunction("sleep", Type.VOID, (mainContext, context) -> {
                long ms = context.getVariable("ms");
                TimeUnit.MILLISECONDS.sleep(ms);
                return null;
            }, new Argument(Type.INTEGER, "ms")));
        }
    }
}
