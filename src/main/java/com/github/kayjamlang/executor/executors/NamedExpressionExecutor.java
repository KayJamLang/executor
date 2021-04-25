package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.containers.NamedExpressionFunctionContainer;
import com.github.kayjamlang.core.expressions.NamedExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;

import java.util.Collections;

public class NamedExpressionExecutor extends ExpressionExecutor<NamedExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          NamedExpression expression) throws Exception {
        FunctionContainer function = (FunctionContainer) context
                .findFunction(mainProvider.mainContext,
                        expression, expression.name, Collections.singletonList(Type.FUNCTION_REF))
                .clone();
        if(!(function instanceof NamedExpressionFunctionContainer))
            throw new KayJamNotFoundException(expression, "named function", expression.name);

        Context functionContext = new Context(function, context, false);
        functionContext.addVariable(expression.name, expression);

        new ContainerExecutor()
                .provide(mainProvider, functionContext, functionContext, function);

        return Void.INSTANCE;
    }
}
