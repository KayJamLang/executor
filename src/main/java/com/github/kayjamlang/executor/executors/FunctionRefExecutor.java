package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.FunctionRef;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;

public class FunctionRefExecutor extends ExpressionExecutor<FunctionRef> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          FunctionRef expression) {
        return expression;
    }
}
