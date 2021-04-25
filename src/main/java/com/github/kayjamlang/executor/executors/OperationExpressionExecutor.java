package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.OperationExpression;
import com.github.kayjamlang.core.expressions.data.Range;
import com.github.kayjamlang.core.expressions.loops.Operation;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.TypeUtils;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OperationExpressionExecutor extends ExpressionExecutor<OperationExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          OperationExpression expression) throws Exception {

        if(expression.operation!=null) {
            Object left = mainProvider.provide(expression.left, context, argsContext);
            Object right = mainProvider.provide(expression.right, context, argsContext);

            Type needType = getType(mainProvider, context, argsContext, expression);
            switch (expression.operation) {
                case PLUS:
                    if(needType.equals(Type.STRING))
                        return left.toString() + right.toString();
                    else if(left instanceof Number&&right instanceof Number)
                        return TypeUtils.getNumberType(needType,
                                ((Number) left).doubleValue()+
                                        ((Number) right).doubleValue());

                    break;

                case MINUS:
                    if(left instanceof Number&&right instanceof Number)
                        return TypeUtils.getNumberType(needType,
                                ((Number) left).doubleValue()-((Number) right).doubleValue());

                    break;

                case DIVISION:
                    if(left instanceof Number&&right instanceof Number)
                        return TypeUtils.getNumberType(needType,
                                ((Number) left).doubleValue()/((Number) right).doubleValue());

                    break;

                case MULTIPLY:
                    if(left instanceof Number&&right instanceof Number)
                        return TypeUtils.getNumberType(needType,
                                ((Number) left).doubleValue()*((Number) right).doubleValue());

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
                        double primaryValue = ((Number) left).doubleValue();
                        double secondaryValue = ((Number) right).doubleValue();

                        return new Range((Number) left, (Number) right, (long)
                                (primaryValue>secondaryValue?-1:1));
                    }

                    break;
            }
        }else throw new KayJamRuntimeException(expression, "This operation in executor not released");

        throw new KayJamRuntimeException(expression, "Invalid operation values");
    }

    private final static List<Operation> boolOperations = new ArrayList<>();
    static {
        boolOperations.add(Operation.AND);
        boolOperations.add(Operation.OR);
        boolOperations.add(Operation.EQUALS);
        boolOperations.add(Operation.NOT_EQUALS);
        boolOperations.add(Operation.GREATER_EQUALS_SIGN);
        boolOperations.add(Operation.GREATER_SIGN);
        boolOperations.add(Operation.LESS_EQUALS_SIGN);
        boolOperations.add(Operation.LESS_SIGN);
    }

    @Override
    public Type getType(Executor executor,
                        Context context,
                        Context argsContext,
                        OperationExpression expression) throws TypeException {
        Type leftType = executor.getType(expression.left, context, argsContext);
        Type rightType = executor.getType(expression.right, context, argsContext);

        if(expression.operation.equals(Operation.RANGE))
            return Type.RANGE;
        else if(boolOperations.contains(expression.operation))
            return Type.BOOLEAN;
        else if(expression.operation.equals(Operation.PLUS)){
            if(leftType.equals(Type.STRING)||rightType.equals(Type.STRING))
                return Type.STRING;
            else TypeUtils.getMainType(leftType, rightType);
        }else if(expression.operation.equals(Operation.DIVISION))
            return Type.DOUBLE;
        else return TypeUtils.getMainType(leftType, rightType);

        return TypeUtils.UNCHECK;
    }
}
