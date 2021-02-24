package com.github.kayjam.executor.executors;

import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.expressions.VariableSet;
import com.github.kayjam.executor.exceptions.KayJamRuntimeException;

public class VariableSetExecutor extends ExpressionProvider<VariableSet, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          VariableSet expression) throws Exception {
        if(!context.variables.containsKey(expression.name))
            throw new KayJamRuntimeException(expression,
                    "You cloud set not created variable \""+
                    expression.name+
                    "\"! Please create variable!");

        Object value =
                mainProvider.provide(expression.expression, context, argsContext);
        context.variables.put(expression.name, value);

        return value;
    }
}
