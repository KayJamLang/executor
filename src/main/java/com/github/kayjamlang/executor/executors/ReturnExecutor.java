package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.expressions.Return;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.TypeUtils;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

public class ReturnExecutor extends ExpressionExecutor<Return> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context, Context argsContext, Return expression) throws Exception {
        Object value = mainProvider.provide(expression.expression, context, argsContext);
        if(context.parent instanceof Function){
            Function func = (Function) context.parent;

            if (func.returnType == Type.VOID && value != null)
                throw new KayJamRuntimeException(func, "Void function can't return " +
                        Type.getType(value.getClass()).name);
            else if(func.returnType != Type.VOID && value == null)
                throw new KayJamRuntimeException(func, "The function must return any value of type " +
                        func.returnType.name);

            if(value!=null&&!TypeUtils.isAccept(func.returnType, value))
                throw new KayJamRuntimeException(func, "The function must return a value of type " +
                        func.returnType.name+", not a "+TypeUtils.getType(value.getClass()).name);
        }

        return value;
    }
}
