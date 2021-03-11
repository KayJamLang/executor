package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.expressions.If;
import com.github.kayjamlang.core.expressions.Return;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;

public class ContainerExecutor extends ExpressionExecutor<Container> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          Container container) throws Exception {
        
        for(Expression expression: container.children){
            Object returnValue = mainProvider.provide(expression, context, argsContext);
            if(returnValue!=null&&
                    (expression instanceof Return||
                    expression instanceof If ||
                    expression instanceof Container))
                return returnValue;
        }

        return null;
    }
}
