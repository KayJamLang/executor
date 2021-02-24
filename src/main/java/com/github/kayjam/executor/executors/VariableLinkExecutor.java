package com.github.kayjam.executor.executors;

import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.exceptions.runtime.NotFoundException;
import com.github.kayjamlang.core.expressions.VariableLink;

public class VariableLinkExecutor extends ExpressionProvider<VariableLink, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          VariableLink expression) throws NotFoundException {
        if(!context.variables.containsKey(expression.name))
            throw new NotFoundException(expression, expression.name, "var");

        return context.variables.get(expression.name);
    }
}
