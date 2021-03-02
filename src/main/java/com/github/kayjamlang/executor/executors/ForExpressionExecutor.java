package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Range;
import com.github.kayjamlang.core.expressions.ForExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

public class ForExpressionExecutor extends ExpressionExecutor<ForExpression> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          ForExpression expression) throws Exception {
        Object rangeObject = mainProvider.provide(expression.range, context, argsContext);
        if(!(rangeObject instanceof Range))
            throw new KayJamRuntimeException(expression.range, "Unknown range expression");
        Range range = (Range) rangeObject;

        for (long i = range.from; i != range.to; i+=range.changeValue) {
            Context ctx = new Context(context.parent, context, true);
            ctx.variables.put(expression.variableName, i);

            Object bodyValue = mainProvider.provide(expression.body, ctx, ctx);
            if(bodyValue!=null)
                return bodyValue;
        }

        return null;
    }
}
