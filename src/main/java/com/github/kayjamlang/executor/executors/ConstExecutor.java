package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;

public class ConstExecutor extends ExpressionExecutor<Const> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          Const expression) {
        return expression.value;
    }
}
