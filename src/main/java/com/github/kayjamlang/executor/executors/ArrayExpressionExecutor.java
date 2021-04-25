package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.ArrayExpression;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.libs.main.ArrayClass;

import java.util.LinkedList;
import java.util.List;

public class ArrayExpressionExecutor extends ExpressionExecutor<ArrayExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          ArrayExpression expression) throws Exception {
        List<Object> list = new LinkedList<>();
        for(Expression item: expression.values)
            list.add(mainProvider.provide(item, context, argsContext));

        return ArrayClass.create(mainProvider, list);
    }

    @Override
    public Type getType(Executor mainProvider, Context context, Context argsContext, ArrayExpression expression) throws TypeException {
        return Type.ARRAY;
    }
}
