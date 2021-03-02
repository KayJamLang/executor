package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.containers.ThreadContainer;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;

public class ThreadExecutor extends ExpressionExecutor<ThreadContainer> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          ThreadContainer expression) {
        new Thread(()->{
            try {
                Context ctx = new Context(expression, context, true);

                new ContainerExecutor()
                        .provide(mainProvider, ctx, ctx, expression);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).start();

        return null;
    }
}
