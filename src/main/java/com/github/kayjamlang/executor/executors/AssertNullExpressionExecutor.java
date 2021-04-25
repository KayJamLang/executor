package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.AssertNullExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

public class AssertNullExpressionExecutor extends ExpressionExecutor<AssertNullExpression> {
    @Override
    public Object provide(Executor mainProvider, Context context,
                          Context argsContext,
                          AssertNullExpression expression) throws Exception {
        Object value = mainProvider.provide(expression.expression, context, argsContext);
        if(value==null)
            throw new KayJamRuntimeException(expression.expression, "Value is null");

        return value;
    }

    @Override
    public Type getType(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                        Context context,
                        Context argsContext,
                        AssertNullExpression expression) throws TypeException {
        Type type = mainProvider.getType(expression.expression, context, argsContext);
        type.nullable = false;

        return type;
    }
}
