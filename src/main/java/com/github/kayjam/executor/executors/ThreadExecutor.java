package com.github.kayjam.executor.executors;

import com.github.kayjamlang.core.containers.ThreadContainer;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;

public class ThreadExecutor extends ExpressionProvider<ThreadContainer, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          ThreadContainer expression) {
        new Thread(()->{
            try {
                mainProvider.provide(expression, context, context);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).start();

        return null;
    }
}
