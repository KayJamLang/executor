package com.github.kayjamlang.executor.utils;

import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;

public class ExecutorUtils {
    public static Object provideOnce(Executor executor, Expression expression, Context context)
            throws Exception {
        if(expression.data.containsKey("instance"))
            return expression.data.get("instance");

        Object value = executor.provide(expression, context, context);
        expression.data.put("instance", value);
        return value;
    }
}
