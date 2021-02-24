package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.OperationExpression;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

import java.util.Objects;

public class OperationExpressionExecutor extends ExpressionProvider<OperationExpression, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          OperationExpression expression) throws Exception {

        if(expression.operation!=null) {
            Object left = mainProvider.provide(expression.left, context, argsContext);
            Object right = mainProvider.provide(expression.right, context, argsContext);

            switch (expression.operation) {
                case PLUS:
                    if(left instanceof String||right instanceof String)
                        return left.toString() + right.toString();
                    else if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).longValue()+((Number) right).longValue();

                    break;

                case MINUS:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).longValue()-((Number) right).longValue();

                    break;

                case DIVISION:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).longValue()/((Number) right).longValue();

                    break;

                case MULTIPLY:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).longValue()*((Number) right).longValue();

                    break;

                case LESS_SIGN:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).longValue()<((Number) right).longValue();

                    break;

                case LESS_EQUALS_SIGN:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).longValue()<=((Number) right).longValue();

                    break;

                case GREATER_SIGN:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).longValue()>((Number) right).longValue();

                    break;

                case GREATER_EQUALS_SIGN:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).longValue()>=((Number) right).longValue();

                    break;

                case EQUALS:
                    return Objects.equals(left, right);

                case NOT_EQUALS:
                    return !Objects.equals(left, right);

                case OR:
                    if(left instanceof Boolean&&right instanceof Boolean)
                        return ((Boolean) left)||((Boolean) right);

                    break;

                case AND:
                    if(left instanceof Boolean&&right instanceof Boolean)
                        return ((Boolean) left)&&((Boolean) right);

                    break;
            }
        }else throw new KayJamRuntimeException(expression, "This operation in executor not released");

        throw new KayJamRuntimeException(expression, "Invalid operation values");
    }
}
