package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.expressions.ReturnExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.TypeUtils;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.Library;

public class ReturnExpressionExecutor extends ExpressionExecutor<ReturnExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context, Context argsContext, ReturnExpression expression) throws Exception {
        Object value = mainProvider.provide(expression.expression, context, argsContext);
        if(context.parent instanceof FunctionContainer&&!(context.parent instanceof Library.LibFunction)){
            FunctionContainer func = (FunctionContainer) context.parent;

            if (func.returnType == Type.VOID && value != Void.INSTANCE)
                throw new KayJamRuntimeException(func, "Void function can't return " +
                        Type.getType(value.getClass()).name);
            else if(func.returnType != Type.VOID && value == Void.INSTANCE)
                throw new KayJamRuntimeException(func, "The function must return any value of type " +
                        func.returnType.name);

            if(!TypeUtils.isAccept(mainProvider.mainContext, func.returnType,
                    mainProvider.getType(expression, context, argsContext)))
                throw new KayJamRuntimeException(func, "The function must return a value of type " +
                        func.returnType.name+", not a "+TypeUtils.getType(value.getClass()).name);
        }

        return value;
    }
}
