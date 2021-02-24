package com.github.kayjam.executor.executors;

import com.github.kayjamlang.core.expressions.Variable;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjam.executor.exceptions.KayJamRuntimeException;

public class VariableExecutor extends ExpressionProvider<Variable, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          Variable expression) throws Exception {

        if(context.variables.containsKey(expression.name))
            throw new KayJamRuntimeException(expression, "Variable already set");

        Object value = mainProvider.provide(expression.expression, context, argsContext);
        context.variables.put(expression.name, value);

        return value;
    }
}
