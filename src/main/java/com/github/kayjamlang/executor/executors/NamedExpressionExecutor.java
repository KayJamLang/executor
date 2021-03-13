package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.containers.NamedExpression;
import com.github.kayjamlang.core.containers.NamedExpressionFunction;
import com.github.kayjamlang.core.expressions.FunctionRef;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

import java.util.Collections;

public class NamedExpressionExecutor extends ExpressionExecutor<NamedExpression> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          NamedExpression expression) throws Exception {
        Function function = (Function) context.findFunction(expression, expression.name, Collections.singletonList(expression))
                .clone();
        if(!(function instanceof NamedExpressionFunction))
            throw new KayJamNotFoundException(expression, "named function", expression.name);

        Context functionContext = new Context(function, context, false);
        functionContext.variables.put(expression.name, expression);

        new ContainerExecutor()
                .provide(mainProvider, functionContext, functionContext, function);

        return null;
    }
}
