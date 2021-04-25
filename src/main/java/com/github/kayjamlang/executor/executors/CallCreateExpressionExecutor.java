package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.FunctionRefExpression;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.*;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.Library;

import java.util.ArrayList;
import java.util.List;

public class CallCreateExpressionExecutor extends ExpressionExecutor<CallOrCreateExpression> {

    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          CallOrCreateExpression expression) throws Exception {
        List<Type> types = new ArrayList<>();
        for(Expression arg: expression.arguments)
            types.add(mainProvider.getType(arg, argsContext, argsContext));

        if(context.variables.containsKey(expression.name)&&
                context.getVariable(expression.name) instanceof FunctionRefExpression){
            FunctionRefExpression functionRef = context.getVariable(expression.name);
            if(types.size()==functionRef.arguments.size()&&
                    TypeUtils.isAccept(mainProvider.mainContext, functionRef.arguments, types)){
                Context functionContext = new Context(context.parent,
                        context, false);
                for (int argNum = 0; argNum < functionRef.arguments.size(); argNum++) {
                    functionContext.variables.put(
                            functionRef.arguments.get(argNum).name,
                            new Context.LocalVariable(
                                    types.get(argNum),
                                    mainProvider.provide(expression.arguments.get(argNum),
                                        argsContext, argsContext))
                    );
                }

                return mainProvider.provide(functionRef.expression, functionContext,
                        functionContext);
            }
        }

        if(mainProvider.mainContext.classes.containsKey(expression.name)){
            ClassContainer classContainer =
                    mainProvider.mainContext.classes.get(expression.name)
                            .clone();
            ClassContainer clazz = ClassUtils.newInstance(mainProvider,
                    classContainer, argsContext, expression.arguments, types);
            if(clazz!=null)
                return clazz;
        }

        FunctionContainer function = (FunctionContainer)
                context.findFunction(mainProvider.mainContext, expression, expression.name, types)
                .clone();

        List<Object> args = new ArrayList<>();
        for(Expression arg: expression.arguments)
            args.add(mainProvider.provide(arg,
                    argsContext, argsContext));

        return FunctionUtils.callFunction(mainProvider, context, function, args);
    }

    @Override
    public Type getType(Executor executor,
                        Context context,
                        Context argsContext,
                        CallOrCreateExpression expression) throws TypeException {
        List<Type> types = new ArrayList<>();
        for(Expression arg: expression.arguments)
            types.add(executor.getType(arg, argsContext, argsContext));


        if(context.variables.containsKey(expression.name)&&
                context.getVariable(expression.name) instanceof FunctionRefExpression){
            FunctionRefExpression functionRef = context.getVariable(expression.name);
            if(types.size()==functionRef.arguments.size()&&
                    TypeUtils.isAccept(executor.mainContext, functionRef.arguments, types)){
                return functionRef.typeOfReturn;
            }
        }

        try {
            if (executor.mainContext.classes.containsKey(expression.name)) {
                ClassContainer classContainer = executor.mainContext.classes
                        .get(expression.name)
                        .clone();
                if (ClassUtils.findConstructor(executor.mainContext, types, classContainer) != null)
                    return Type.of(classContainer.name);

            }

            FunctionContainer function = (FunctionContainer)
                    context.findFunction(executor.mainContext, expression,
                            expression.name, types)
                            .clone();

            return function.returnType;
        }catch (Exception ignored){}

        return TypeUtils.UNCHECK;
    }
}
