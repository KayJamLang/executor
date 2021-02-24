package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.expressions.If;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.expressions.Return;

public class ContainerExecutor extends ExpressionProvider<Container, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
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
