package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.TypeUtils;

public class ExpressionExecutor<ExpressionType> extends ExpressionProvider<ExpressionType,
        Object, Context, MainContext> {

    @Override
    public final Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          ExpressionType expression) throws Exception {
        return provide((Executor) mainProvider, context, argsContext, expression);
    }

    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          ExpressionType expression) throws Exception {
        return super.provide(mainProvider, context, argsContext, expression);
    }

    @Override
    public Type getType(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                        Context context,
                        Context argsContext,
                        ExpressionType expression) throws TypeException {
        return getType((Executor) mainProvider, context, argsContext, expression);
    }

    public Type getType(Executor mainProvider,
                        Context context,
                        Context argsContext,
                        ExpressionType expression) throws TypeException {
        return TypeUtils.UNCHECK;
    }
}
