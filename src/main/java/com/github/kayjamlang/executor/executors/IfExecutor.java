package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.If;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;

public class IfExecutor extends ExpressionExecutor<If> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          If expression) throws Exception {
        Object condition = mainProvider.provide(expression.condition, context, argsContext);
        if(condition instanceof Boolean&&!(Boolean) condition)
            return mainProvider.provide(expression.ifFalse, context, argsContext);

        return mainProvider.provide(expression.ifTrue, context, argsContext);
    }
}
