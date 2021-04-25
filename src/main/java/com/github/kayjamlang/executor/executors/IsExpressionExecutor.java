package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.IsExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;

public class IsExpressionExecutor extends ExpressionExecutor<IsExpression> {

    @Override
    public Object provide(Executor executor,
                          Context context,
                          Context argsContext,
                          IsExpression expression) throws Exception {
        Type valueType = executor.getType(expression.expression, context, argsContext);

        return valueType.equals(expression.verifyType);
    }

    @Override
    public Type getType(Executor executor,
                        Context context,
                        Context argsContext,
                        IsExpression expression) {
        return Type.BOOLEAN;
    }
}
