package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.If;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;

public class IfExecutor extends ExpressionProvider<If, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          If expression) throws Exception {
        Object condition = mainProvider.provide(expression.condition, context, argsContext);
        if(condition instanceof Boolean&&!(Boolean) condition)
            return mainProvider.provide(expression.ifFalse, context, argsContext);

        return mainProvider.provide(expression.ifTrue, context, argsContext);
    }
}
