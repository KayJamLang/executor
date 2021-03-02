package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.VariableSet;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

public class VariableSetExecutor extends ExpressionExecutor<VariableSet> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          VariableSet expression) throws Exception {
        Object value =
                mainProvider.provide(expression.expression, argsContext, argsContext);

        if(!context.setVariable(expression.name, value))
            throw new KayJamRuntimeException(expression,
                    "You cloud set not created variable \""+
                    expression.name+
                    "\"! Please create variable!");

        return value;
    }
}
