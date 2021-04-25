package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.VariableSetExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.*;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

public class VariableSetExpressionExecutor extends ExpressionExecutor<VariableSetExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          VariableSetExpression expression) throws Exception {
        Object value =
                mainProvider.provide(expression.expression, argsContext, argsContext);

        if(value instanceof Void)
            throw new KayJamRuntimeException(expression, "Variable cannot contain void type");

        if(!context.setVariable(expression.name,
                mainProvider.getType(expression.expression, argsContext, argsContext),
                value))
            throw new KayJamRuntimeException(expression,
                    "You cloud set not created variable \""+
                    expression.name+
                    "\"! Please create variable!");

        return value;
    }

    @Override
    public Type getType(Executor executor,
                        Context context,
                        Context argsContext,
                        VariableSetExpression expression) throws TypeException {
        return executor.getType(expression.expression, argsContext, argsContext);
    }
}
