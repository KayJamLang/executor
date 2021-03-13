package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.GetExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.main.ArrayClass;
import com.github.kayjamlang.executor.libs.main.MapClass;

public class GetExecutor extends ExpressionExecutor<GetExpression> {
    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          GetExpression expression) throws Exception {
        Object root = mainProvider.provide(expression.root, context, argsContext);
        Object index = mainProvider.provide(expression.value, context, argsContext);
        if(root instanceof ArrayClass){
            if(!(index instanceof Number))
                throw new KayJamRuntimeException(expression.value, "Unknown index value");
            ArrayClass arrayClass = (ArrayClass) root;

            return arrayClass.get(((Number) index).intValue(), false);
        }else if(root instanceof MapClass){
            MapClass mapClass = (MapClass) root;
            return mapClass.map.getOrDefault(index, false);
        }

        throw new KayJamRuntimeException(expression.root, "unknown type of root");
    }
}
