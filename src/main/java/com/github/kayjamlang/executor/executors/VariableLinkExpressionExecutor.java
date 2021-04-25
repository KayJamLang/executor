package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.VariableLinkExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;

public class VariableLinkExpressionExecutor extends ExpressionExecutor<VariableLinkExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          VariableLinkExpression expression) throws KayJamNotFoundException {
        Context.LocalVariable result = context
                .variables.get(expression.name);

        if(result==null)
            throw new KayJamNotFoundException(expression, "var", expression.name);

        return result.value;
    }

    @Override
    public Type getType(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                        Context context,
                        Context argsContext,
                        VariableLinkExpression expression) throws TypeException {
        Context.LocalVariable result = argsContext
                .variables.getOrDefault(expression.name,
                        context.variables.getOrDefault(expression.name, null));


        if(result==null)
            throw new TypeException();

        return result.type;
    }
}
