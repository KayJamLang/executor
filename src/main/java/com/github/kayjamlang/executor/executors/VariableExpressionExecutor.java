package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.VariableExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.TypeUtils;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

public class VariableExpressionExecutor extends ExpressionExecutor<VariableExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          VariableExpression expression) throws Exception {

        if(context.variables.containsKey(expression.name))
            throw new KayJamRuntimeException(expression, "Variable already set");


        Object value = mainProvider.provide(expression.expression, argsContext, argsContext);
        if(value instanceof Void)
            throw new KayJamRuntimeException(expression, "Variable cannot contain void type");

        context.variables.put(expression.name, new Context.LocalVariable(
                mainProvider.getType(expression.expression, argsContext, argsContext), value));

        return value;
    }


    @Override
    public Type getType(Executor executor,
                        Context context,
                        Context argsContext,
                        VariableExpression expression) throws TypeException {
        return executor.getType(expression.expression, argsContext, argsContext);
    }
}
