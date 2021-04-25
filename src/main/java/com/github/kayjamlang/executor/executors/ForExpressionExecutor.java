package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.data.Range;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

public class ForExpressionExecutor extends ExpressionExecutor<ForExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          ForExpression expression) throws Exception {
        Object rangeObject = mainProvider.provide(expression.range, context, argsContext);
        if(!(rangeObject instanceof Range))
            throw new KayJamRuntimeException(expression.range, "Unknown range expression");
        Range range = (Range) rangeObject;

        Context ctx = new Context(context.parent, context, true);
        for (Number i = range.from; !i.equals(range.to); i=
                i.doubleValue()+range.changeValue.doubleValue()) {
            ctx.clearVariables();
            ctx.addVariable(expression.variableName, i);

            Object bodyValue = mainProvider.provide(expression.body, ctx, ctx);
            if(bodyValue!=Void.INSTANCE)
                return bodyValue;
        }

        return Void.INSTANCE;
    }
}
