package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.ConstructorContainer;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.expressions.VariableExpression;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;
import com.github.kayjamlang.executor.executors.ContainerExecutor;

import java.util.ArrayList;
import java.util.List;

public class ClassUtils {
    public static ConstructorContainer findConstructor(MainContext mainContext,
                                                       List<Type> types,
                                                       ClassContainer clazz){
        if(clazz.constructors.size()==0&&types.size()==0){
            return new ConstructorContainer(new ArrayList<>(),
                    new ArrayList<>(), AccessIdentifier.NONE, 0);
        }else{
            for (ConstructorContainer constructor : clazz.constructors) {
                if (constructor.arguments.size()==types.size()&&
                        TypeUtils.isAccept(mainContext,
                                constructor.arguments, types)) {
                    return constructor;
                }
            }
        }

        return null;
    }

    public static <T extends ClassContainer> T newInstance(
            Executor executor,
            T clazz,
            Context argsContext,
            List<Expression> arguments,
            List<Type> types) throws Exception {
        Context context = executor.mainContext;
        Context classContext = new Context(clazz, context,
                false);

        ClassContainer extendsClass = (ClassContainer)
                clazz.data.getOrDefault("extends", null);

        List<String> vars = new ArrayList<>();
        for(VariableExpression variableExpression: clazz.variables)
                vars.add(variableExpression.name);

        while (extendsClass!=null){
            for(VariableExpression variableExpression: extendsClass.variables)
                    if(!vars.contains(variableExpression.name)) {
                        clazz.variables.add(variableExpression);
                        vars.add(variableExpression.name);
                    }

            clazz.functions.addAll(extendsClass.functions);

            extendsClass = (ClassContainer)
                    extendsClass.data.getOrDefault("extends", null);
        }

        ConstructorContainer constructorContainer = null;
        if(clazz.constructors.size()==0&&types.size()==0){
            constructorContainer = new ConstructorContainer(new ArrayList<>(),
                    new ArrayList<>(), AccessIdentifier.NONE, 0);
        }else{
            for (ConstructorContainer constructor : clazz.constructors) {
                if (constructor.arguments.size()==types.size()&&
                        TypeUtils.isAccept(executor.mainContext,
                                constructor.arguments, types)) {
                    constructorContainer = constructor;
                    break;
                }
            }
        }

        if(constructorContainer!=null){
            classContext.variables.clear();
            for(VariableExpression variable: clazz.variables)
                executor.provide(variable, classContext, classContext);

            Context constructorClass =
                    new Context(constructorContainer, classContext, true);
            constructorClass.addVariable("this", clazz);
            clazz.data.put("ctx", classContext);

            for (int argNum = 0; argNum < constructorContainer.arguments.size(); argNum++) {
                constructorClass.variables.put(constructorContainer.arguments.get(argNum).name,
                        new Context.LocalVariable(
                            types.get(argNum),
                            executor.provide(arguments.get(argNum), argsContext, argsContext)
                         ));
            }

            new ContainerExecutor().provide(executor,
                    constructorClass,
                    constructorClass,
                    constructorContainer);

            return clazz;
        }

        return null;
    }
}
