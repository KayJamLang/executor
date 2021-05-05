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
            ConstructorContainer constructor,
            List<Object> parameters) throws Exception {
        if(constructor==null||
                constructor.arguments.size()!=parameters.size())
            return null;

        Context classContext = new Context(clazz, executor.mainContext,
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

        classContext.variables.clear();
        for(VariableExpression variable: clazz.variables)
            executor.provide(variable, classContext, classContext);

        Context constructorClass =
                new Context(constructor, classContext, true);
        constructorClass.addVariable("this", clazz);
        clazz.data.put("ctx", classContext);

        for (int argNum = 0; argNum < constructor.arguments.size(); argNum++) {
            constructorClass.variables.put(constructor.arguments.get(argNum).name,
                    new Context.LocalVariable(
                            constructor.arguments.get(argNum).type,
                            parameters.get(argNum)
                    ));
        }

        new ContainerExecutor().provide(executor,
                constructorClass,
                constructorClass,
                constructor);

        return clazz;
    }

}
