package com.github.kayjam.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.expressions.Return;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjam.executor.exceptions.KayJamRuntimeException;

public class ReturnExecutor extends ExpressionProvider<Return, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context, Context argsContext, Return expression) throws Exception {
        Object value = mainProvider.provide(expression.expression, context, argsContext);
        if(context.parent instanceof Function){
            Function func = (Function) context.parent;

            if(func.returnType!=Type.ANY) {
                if (func.returnType == Type.VOID && value != null)
                    throw new KayJamRuntimeException(func, "Void function can't return " +
                            Type.getType(value.getClass()).name);
                else if(func.returnType != Type.VOID && value == null)
                    throw new KayJamRuntimeException(func, "The function must return any value of type " +
                            func.returnType.name);
                else if(value!=null&&func.returnType.typeClass!=value.getClass())
                    throw new KayJamRuntimeException(func, "The function must return a value of type " +
                            func.returnType.name+", not a "+Type.getType(value.getClass()).name);
            }
        }

        return value;
    }
}
