package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.CallCreate;
import com.github.kayjamlang.core.expressions.FunctionRef;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.*;

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

        if(context.variables.containsKey(expression.functionName)&&
                context.variables.get(expression.functionName) instanceof FunctionRef){
            FunctionRef functionRef = (FunctionRef)
                    context.variables.get(expression.functionName);
            if(objects.size()==functionRef.arguments.size()&&
                    TypeUtils.isAccept(functionRef.arguments, objects)){
                Context functionContext = new Context(context.parent,
                        context, false);
                for (int argNum = 0; argNum < functionRef.arguments.size(); argNum++) {
                    functionContext.variables.put(
                            functionRef.arguments.get(argNum).name,
                            objects.get(argNum)
                    );
                }

                return mainProvider.provide(functionRef.expression, functionContext,
                        functionContext);
            }
        }

        if(mainProvider.mainContext.classes.containsKey(expression.functionName)){
            ClassContainer classContainer =
                    mainProvider.mainContext.classes.get(expression.functionName)
                            .clone();
            ClassContainer clazz = ClassUtils.newInstance((Executor) mainProvider,
                    classContainer, objects);
            if(clazz!=null)
                return clazz;
//            Context classContext = new Context(classContainer, context, false);
//
//            ClassContainer extendsClass = (ClassContainer)
//                    classContainer.data.getOrDefault("extends", null);
//
//            List<String> vars = new ArrayList<>();
//            for(Expression expression1: classContainer.children)
//                if(expression1 instanceof Variable)
//                    vars.add(((Variable) expression1).name);
//
//            while (extendsClass!=null){
//                for(Expression expression1: extendsClass.children)
//                    if(expression1 instanceof Variable)
//                        if(!vars.contains(((Variable) expression1).name)) {
//                            classContainer.children.add(expression1);
//                            vars.add(((Variable) expression1).name);
//                        }
//
//                classContainer.functions.addAll(extendsClass.functions);
//
//                extendsClass = (ClassContainer)
//                        extendsClass.data.getOrDefault("extends", null);
//            }
//
//            ConstructorContainer constructorContainer = null;
//            if(classContainer.constructors.size()==0&&expression.arguments.size()==0){
//                constructorContainer = new ConstructorContainer(new ArrayList<>(),
//                        new ArrayList<>(), AccessIdentifier.NONE, 0);
//            }else{
//                for (ConstructorContainer constructor : classContainer.constructors) {
//                    if (constructor.arguments.size()==expression.arguments.size()&&
//                            TypeUtils.isAccept(constructor.arguments, objects)) {
//                        constructorContainer = constructor;
//                        break;
//                    }
//                }
//            }
//
//            if(constructorContainer!=null){
//                classContext.variables.clear();
//                new ContainerExecutor().provide(mainProvider,
//                        classContext, classContext, classContainer);
//
//                Context constructorClass =
//                        new Context(constructorContainer, classContext, true);
//                constructorClass.variables.put("this", classContainer);
//                classContainer.data.put("ctx", classContext);
//
//                for (int argNum = 0; argNum < constructorContainer.arguments.size(); argNum++) {
//                    constructorClass.variables.put(
//                            constructorContainer.arguments.get(argNum).name,
//                            objects.get(argNum)
//                    );
//                }
//
//                new ContainerExecutor().provide(mainProvider,
//                                constructorClass,
//                                constructorClass,
//                                constructorContainer);
//
//                return classContainer;
//            }
        }

        Function function = (Function) context.findFunction(expression, expression.functionName, objects)
                .clone();
        Context functionContext = new Context(function, context,
                context.parent instanceof ObjectContainer);
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
