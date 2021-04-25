package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.IfExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.Void;

public class IfExpressionExecutor extends ExpressionExecutor<IfExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          IfExpression expression) throws Exception {
        Object condition = mainProvider.provide(expression.condition, context, argsContext);
        if(condition instanceof Boolean&&!(Boolean) condition) {
            if(expression.ifFalse==null)
                return Void.INSTANCE;

            return mainProvider.provide(expression.ifFalse, context, argsContext);
        }

        return mainProvider.provide(expression.ifTrue, context, argsContext);
    }
}
