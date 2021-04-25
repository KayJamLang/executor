package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.loops.WhileExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.Void;

public class WhileExpressionExecutor extends ExpressionExecutor<WhileExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          WhileExpression expression) throws Exception {

        Context ctx = new Context(context.parent, context, true);
        while (true){
            ctx.clearVariables();
            Object condition = mainProvider.provide(expression.condition, ctx, ctx);
            if(condition instanceof Boolean&&!(Boolean) condition)
                break;

            Object bodyValue = mainProvider.provide(expression.expression, ctx, ctx);
            if(bodyValue!=Void.INSTANCE)
                return bodyValue;
        }

        return Void.INSTANCE;
    }
}
