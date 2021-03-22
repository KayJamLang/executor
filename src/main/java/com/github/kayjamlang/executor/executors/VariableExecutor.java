package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.expressions.Variable;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

public class VariableExecutor extends ExpressionExecutor<Variable> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          Variable expression) throws Exception {

        if(context.variables.containsKey(expression.name))
            throw new KayJamRuntimeException(expression, "Variable already set");


        Object value = mainProvider.provide(expression.expression, argsContext, argsContext);
        if(value instanceof Void)
            throw new KayJamRuntimeException(expression, "Variable cannot contain void type");

        context.variables.put(expression.name, value);

        return value;
    }
}
