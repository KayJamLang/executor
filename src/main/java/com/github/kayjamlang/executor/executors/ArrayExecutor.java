package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.expressions.Array;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.libs.main.ArrayClass;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArrayExecutor extends ExpressionExecutor<Array> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          Array expression) throws Exception {
        ArrayClass arrayClass = new ArrayClass();
        arrayClass.data.put("ctx", new Context(arrayClass, context, false));
        arrayClass.children.clear();

        List<Object> list = new LinkedList<>();
        arrayClass.addVariable("array", list);
        for(Expression item: expression.values)
            list.add(mainProvider.provide(item, context, argsContext));

        return arrayClass;
    }
}
