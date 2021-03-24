package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Range;
import com.github.kayjamlang.core.expressions.OperationExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

import java.util.Objects;

public class OperationExpressionExecutor extends ExpressionExecutor<OperationExpression> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
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
                        return ((Number) left).doubleValue()+((Number) right).doubleValue();

                    break;

                case MINUS:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).doubleValue()-((Number) right).doubleValue();

                    break;

                case DIVISION:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).doubleValue()/((Number) right).doubleValue();

                    break;

                case MULTIPLY:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).doubleValue()*((Number) right).doubleValue();

                    break;

                case LESS_SIGN:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).doubleValue()<((Number) right).doubleValue();

                    break;

                case LESS_EQUALS_SIGN:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).doubleValue()<=((Number) right).doubleValue();

                    break;

                case GREATER_SIGN:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).doubleValue()>((Number) right).doubleValue();

                    break;

                case GREATER_EQUALS_SIGN:
                    if(left instanceof Number&&right instanceof Number)
                        return ((Number) left).doubleValue()>=((Number) right).doubleValue();

                    break;

                case EQUALS: {
                    if(left instanceof Number&&right instanceof Number)
                        return left==right;

                    return Objects.equals(left, right);
                }

                case NOT_EQUALS:
                    if(left instanceof Number&&right instanceof Number)
                        return left!=right;

                    return !Objects.equals(left, right);

                case OR:
                    if(left instanceof Boolean&&right instanceof Boolean)
                        return ((Boolean) left)||((Boolean) right);

                    break;

                case AND:
                    if(left instanceof Boolean&&right instanceof Boolean)
                        return ((Boolean) left)&&((Boolean) right);

                    break;

                case RANGE:
                    if(left instanceof Number&&right instanceof Number) {
                        long primaryValue = ((Number) left).longValue();
                        long secondaryValue = ((Number) right).longValue();

                        return new Range(primaryValue, secondaryValue, (long)
                                (primaryValue>secondaryValue?-1:1));
                    }

                    break;
            }
        }else throw new KayJamRuntimeException(expression, "This operation in executor not released");

        throw new KayJamRuntimeException(expression, "Invalid operation values");
    }
}
