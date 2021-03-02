package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.ConstructorContainer;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.CallCreate;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;

import java.util.ArrayList;
import java.util.List;

public class CallCreateExecutor extends ExpressionExecutor<CallCreate> {

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          CallCreate expression) throws Exception {
        List<Object> objects = new ArrayList<>();
        for(Expression arg: expression.arguments)
            objects.add(mainProvider.provide(arg, argsContext, argsContext));

        if(mainProvider.mainContext.classes.containsKey(expression.functionName)){
            ClassContainer classContainer =
                    (ClassContainer) mainProvider.mainContext.classes.get(expression.functionName)
                            .clone();
            Context classContext = new Context(classContainer, context, false);

            ConstructorContainer constructorContainer = null;
            if(classContainer.constructors.size()==0&&expression.arguments.size()==0){
                constructorContainer = new ConstructorContainer(new ArrayList<>(),
                        new ArrayList<>(), AccessIdentifier.NONE, 0);
            }else{
                for (ConstructorContainer constructor : classContainer.constructors) {
                    if (constructor.arguments.size()==expression.arguments.size()) {
                        constructorContainer = constructor;
                        break;
                    }
                }
            }

            if(constructorContainer!=null){
                Context constructorClass =
                        new Context(constructorContainer, classContext, false);
                constructorClass.variables.put("this", classContainer);
                classContainer.data.put("ctx", classContext);

                for (int argNum = 0; argNum < constructorContainer.arguments.size(); argNum++) {
                    constructorClass.variables.put(
                            constructorContainer.arguments.get(argNum),
                            objects.get(argNum)
                    );
                }

                new ContainerExecutor().provide(mainProvider,
                        classContext, classContext, classContainer);

                new ContainerExecutor().provide(mainProvider,
                                constructorClass,
                                constructorClass,
                                constructorContainer);

                return classContainer;
            }
        }

        Function function = (Function) context.findFunction(expression, expression.functionName, objects)
                .clone();
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
