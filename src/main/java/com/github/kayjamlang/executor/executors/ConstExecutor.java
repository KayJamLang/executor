package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.expressions.Const;

public class ConstExecutor extends ExpressionProvider<Const, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          Const expression) {
        return expression.value;
    }
}
