package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.Library;

public class CodeExecExpressionExecutor extends ExpressionExecutor<Library.CodeExecExpression> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          Library.CodeExecExpression expression) throws KayJamRuntimeException, InterruptedException {
        return expression.code.execute(mainProvider.mainContext, context);
    }
}
