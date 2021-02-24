package com.github.kayjam.executor.executors;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.exceptions.runtime.NotFoundException;
import com.github.kayjamlang.core.expressions.CallCreate;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjam.executor.TypeUtils;

import java.util.ArrayList;
import java.util.List;

public class CallCreateExecutor extends ExpressionProvider<CallCreate, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          CallCreate expression) throws Exception {
        List<Function> functions = context.findFunctions(expression.functionName);
        if(functions.size()==0)
            throw new NotFoundException(expression, "function", expression.functionName);

        List<Object> objects = new ArrayList<>();
        for(Expression arg: expression.arguments)
            objects.add(mainProvider.provide(arg, argsContext, argsContext));

        for(Function function: functions){
            if(function.arguments.size()==expression.arguments.size()&&
                TypeUtils.isAccept(function.arguments, objects)){
                Context functionContext = new Context(function, context, false);
                if(context.parent instanceof ObjectContainer)
                    functionContext.variables.put("this", context.parent);

                for (int argNum = 0; argNum < function.arguments.size(); argNum++) {
                    functionContext.variables.put(
                            function.arguments.get(argNum).name,
                            objects.get(argNum)
                    );
                }

                return new ContainerExecutor()
                        .provide(mainProvider, functionContext, functionContext, function);
            }
        }

        throw new NotFoundException(expression, "function", expression.functionName);
    }
}
