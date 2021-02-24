package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.expressions.Array;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;

import java.util.ArrayList;
import java.util.List;

public class ArrayExecutor extends ExpressionProvider<Array, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          Array expression) throws Exception {
        List<Object> array = new ArrayList<>();
        for(Expression item: expression.values)
            array.add(mainProvider.provide(item, context, argsContext));

        return array;
    }
}
