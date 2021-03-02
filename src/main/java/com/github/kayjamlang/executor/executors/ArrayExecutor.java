package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.expressions.Array;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;

import java.util.ArrayList;
import java.util.List;

public class ArrayExecutor extends ExpressionExecutor<Array> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          Array expression) throws Exception {
        List<Object> array = new ArrayList<>();
        for(Expression item: expression.values)
            array.add(mainProvider.provide(item, context, argsContext));

        return array;
    }
}
