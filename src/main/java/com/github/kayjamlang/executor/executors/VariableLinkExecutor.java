package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.VariableLink;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;

public class VariableLinkExecutor extends ExpressionExecutor<VariableLink> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          VariableLink expression) throws KayJamNotFoundException {
        Object result = context.variables.getOrDefault(expression.name, null);

        if(result==null)
            throw new KayJamNotFoundException(expression, "var", expression.name);

        return result;
    }
}
