package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.Access;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

public class AccessExecutor extends ExpressionExecutor<Access> {
    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          Access expression) throws Exception {
        Object root = mainProvider.provide(expression.root, context, argsContext);
        if(root instanceof ObjectContainer){
            ObjectContainer object = (ObjectContainer) root;

            Context rootContext = (Context) object.data.get("ctx");
            return mainProvider.provide(expression.child, rootContext, argsContext);
        }

        throw new KayJamRuntimeException(expression.root, "Expected object or class root");
    }
}
