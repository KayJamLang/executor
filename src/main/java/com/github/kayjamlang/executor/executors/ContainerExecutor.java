package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.ReturnExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.Void;

public class ContainerExecutor extends ExpressionExecutor<Container> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          Container container) throws Exception {
        
        for(Expression expression: container.children){
            Object returnValue = mainProvider.provide(expression, context, argsContext);
            if(expression instanceof ReturnExpression)
                return returnValue;
        }

        return Void.INSTANCE;
    }
}
