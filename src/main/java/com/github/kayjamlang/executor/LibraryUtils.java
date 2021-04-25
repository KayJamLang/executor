package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.executor.libs.Lib;
import com.github.kayjamlang.executor.libs.Library;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class LibraryUtils {

    public static void importFunctionsFormClass(Lib library, Class<?> clazz){
        for(Method method: clazz.getDeclaredMethods()){
            if(Modifier.isStatic(method.getModifiers())){
                ArrayList<Argument> arguments = new ArrayList<>();
                for(Parameter parameter:
                        method.getParameters())
                    arguments.add(new Argument(
                            TypeUtils.getType(parameter.getType()),
                            parameter.getName()));

                /*library.addFunction(new Library.LibFunction(method.getName(),
                        (mainContext, context) -> {
                            Object[] args =
                                    new Object[method.getParameters().length];
                            for (int i = 0; i < method.getParameters().length; i++) {
                                Object arg = context.getVariable(
                                        method.getParameters()[i].getName());
                                args[i] = arg;
                            }

                            Method invokeMethod = method.getClass()
                                    .getMethod("invoke",
                                            Object.class, Object[].class);
                            return invokeMethod.invoke(method, null, args);
                        }, arguments));*/
            }
        }
    }
}
