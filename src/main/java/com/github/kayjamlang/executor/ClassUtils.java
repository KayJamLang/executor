package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.ConstructorContainer;
import com.github.kayjamlang.core.expressions.Variable;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;
import com.github.kayjamlang.executor.executors.ContainerExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassUtils {
    public static <T extends ClassContainer> T newInstance(
            Executor executor,
            T clazz,
            List<Object> parameters) throws Exception {
        Context context = executor.mainContext;
        Context classContext = new Context(clazz, context,
                false);

        ClassContainer extendsClass = (ClassContainer)
                clazz.data.getOrDefault("extends", null);

        List<String> vars = new ArrayList<>();
        for(Expression expression1: clazz.children)
            if(expression1 instanceof Variable)
                vars.add(((Variable) expression1).name);

        while (extendsClass!=null){
            for(Expression expression1: extendsClass.children)
                if(expression1 instanceof Variable)
                    if(!vars.contains(((Variable) expression1).name)) {
                        clazz.children.add(expression1);
                        vars.add(((Variable) expression1).name);
                    }

            clazz.functions.addAll(extendsClass.functions);

            extendsClass = (ClassContainer)
                    extendsClass.data.getOrDefault("extends", null);
        }

        ConstructorContainer constructorContainer = null;
        if(clazz.constructors.size()==0&&parameters.size()==0){
            constructorContainer = new ConstructorContainer(new ArrayList<>(),
                    new ArrayList<>(), AccessIdentifier.NONE, 0);
        }else{
            for (ConstructorContainer constructor : clazz.constructors) {
                if (constructor.arguments.size()==parameters.size()&&
                        TypeUtils.isAccept(constructor.arguments, parameters)) {
                    constructorContainer = constructor;
                    break;
                }
            }
        }

        if(constructorContainer!=null){
            classContext.variables.clear();
            new ContainerExecutor().provide(executor,
                    classContext, classContext, clazz);

            Context constructorClass =
                    new Context(constructorContainer, classContext, true);
            constructorClass.variables.put("this", clazz);
            clazz.data.put("ctx", classContext);

            for (int argNum = 0; argNum < constructorContainer.arguments.size(); argNum++) {
                constructorClass.variables.put(
                        constructorContainer.arguments.get(argNum).name,
                        parameters.get(argNum)
                );
            }

            new ContainerExecutor().provide(executor,
                    constructorClass,
                    constructorClass,
                    constructorContainer);

            return clazz;
        }

        return null;
    }

    public static <T extends ClassContainer> T newSimpleInstance(
            Executor executor,
            T clazz,
            Object... parameters) throws Exception {
        return ClassUtils.newInstance(executor, clazz, Arrays.asList(parameters));
    }
}
