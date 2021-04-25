package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.executors.ContainerExecutor;
import com.github.kayjamlang.executor.libs.Library;

import java.util.List;

public class FunctionUtils {

    public static Object callFunction(Executor executor, Context context,
                               FunctionContainer function, List<Object> args) throws Exception {
        Context functionContext = new Context(function, context,
                context.parent instanceof ClassContainer);
        if(context.parent instanceof ClassContainer)
            functionContext.variables.put("this",
                    new Context.LocalVariable(Type.of(((ClassContainer) context.parent).name),
                            context.parent));

        for (int argNum = 0; argNum < function.arguments.size(); argNum++) {
            functionContext.variables.put(
                    function.arguments.get(argNum).name,
                    new Context.LocalVariable(
                            function.arguments.get(argNum).type,
                            args.get(argNum))
            );
        }

        Object value = new ContainerExecutor()
                .provide(executor, functionContext, functionContext, function);
        if(!(function instanceof Library.LibFunction)&&
                value instanceof Void&&function.returnType!=Type.VOID)
            throw new KayJamRuntimeException(function, "The function must return a value of type " +
                    function.returnType.name+", not a "+TypeUtils.getType(value.getClass()).name);

        return value;
    }
}
