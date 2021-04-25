package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.AccessExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.utils.ExecutorUtils;

public class AccessExpressionExecutor extends ExpressionExecutor<AccessExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          AccessExpression expression) throws Exception {
        Object root = ExecutorUtils.provideOnce(mainProvider, expression.root, context);
        if(root instanceof ClassContainer){
            ClassContainer classContainer = (ClassContainer) root;

            Context rootContext = (Context) classContainer.data.get("ctx");
            return mainProvider.provide(expression.child, rootContext, argsContext);
        }

        throw new KayJamRuntimeException(expression.root, "Expected object or class root");
    }

    @Override
    public Type getType(Executor mainProvider,
                        Context context,
                        Context argsContext,
                        AccessExpression expression) throws TypeException {
        try {
            Object root = ExecutorUtils.provideOnce(mainProvider, expression.root, context);
            if(root instanceof ObjectContainer){
                ObjectContainer object = (ObjectContainer) root;

                Context rootContext = (Context) object.data.get("ctx");
                return mainProvider.getType(expression.child, rootContext, argsContext);
            }

        }catch (Exception ignored){}

        return super.getType(mainProvider, context, argsContext, expression);
    }
}
