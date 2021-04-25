package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.AccessExpression;
import com.github.kayjamlang.core.expressions.CastExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.TypeUtils;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.exceptions.KayJamTypeException;
import com.github.kayjamlang.executor.utils.ExecutorUtils;

public class CastExpressionExecutor extends ExpressionExecutor<CastExpression> {
    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          CastExpression expression) throws Exception {
        Object value = mainProvider.provide(expression.expression, context, argsContext);
        Type valueType = mainProvider.getType(expression.expression, context, argsContext);
        if(TypeUtils.numberTypes.contains(valueType)&&
                TypeUtils.numberTypes.contains(expression.castType)&&
                value instanceof Number){
            return TypeUtils.getNumberType(expression.castType, (Number) value);
        }else if(TypeUtils.isAccept(mainProvider.mainContext, expression.castType, valueType)){
            return value;
        }

        throw new KayJamRuntimeException(expression, "Cannon cast "+valueType.name+" as "+expression.castType.name);
    }

    @Override
    public Type getType(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                        Context context,
                        Context argsContext,
                        CastExpression expression) throws TypeException {
        Type valueType = mainProvider.getType(expression.expression, context, argsContext);
        if((TypeUtils.numberTypes.contains(valueType)&&
                TypeUtils.numberTypes.contains(expression.castType))||
                TypeUtils.isAccept(mainProvider.mainContext, expression.castType, valueType)){
            return expression.castType;
        }

        throw new KayJamTypeException(
                new KayJamRuntimeException(expression,
                        "Cannon cast "+valueType.name+" as "+expression.castType.name));
    }
}
