package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.Library;

public class CodeExecExpressionExecutor extends ExpressionProvider<Library.CodeExecExpression, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          Library.CodeExecExpression expression) throws KayJamRuntimeException {
        return expression.code.execute(mainProvider.mainContext, context);
    }
}
