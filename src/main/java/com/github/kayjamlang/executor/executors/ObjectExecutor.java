package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.VariableExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.MainContext;

public class ObjectExecutor extends ExpressionExecutor<ObjectContainer> {
    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          ObjectContainer expression) throws Exception {
        Context ctx = new Context(expression, context, false);
        for(Expression child: expression.children){
            if(child.getClass()==VariableExpression.class)
                mainProvider.provide(child, ctx, ctx);
        }

        expression.data.put("ctx", ctx);
        return expression;
    }

    @Override
    public Type getType(Executor mainProvider,
                        Context context,
                        Context argsContext,
                        ObjectContainer expression) {
        return Type.OBJECT;
    }
}
