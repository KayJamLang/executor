package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.FunctionRefExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.MainContext;

public class FunctionRefExpressionExecutor extends ExpressionExecutor<FunctionRefExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          FunctionRefExpression expression) {
        return expression;
    }

    @Override
    public Type getType(Executor mainProvider,
                        Context context,
                        Context argsContext,
                        FunctionRefExpression expression) {
        return Type.FUNCTION_REF;
    }
}
