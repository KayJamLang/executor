package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.Library;

import java.lang.reflect.InvocationTargetException;

public class CodeExecExpressionExecutor extends ExpressionExecutor<Library.CodeExecExpression> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          Library.CodeExecExpression expression) throws Exception {
        Object result = expression.code.execute(mainProvider.mainContext, context);
        if(result==null)
            return Void.INSTANCE;
        return result;
    }
}
