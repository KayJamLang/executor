package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.Variable;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;

public class ObjectExecutor extends ExpressionExecutor<ObjectContainer> {
    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          ObjectContainer expression) throws Exception {
        Context ctx = new Context(expression, context, false);
        for(Expression child: expression.children){
            if(child.getClass()==Variable.class)
                mainProvider.provide(child, ctx, ctx);
        }

        expression.data.put("ctx", ctx);
        return expression;
    }
}
