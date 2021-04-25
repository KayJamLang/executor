package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

public class ClassContainerExecutor extends ExpressionExecutor<ClassContainer> {

    @Override 
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          ClassContainer expression) throws Exception {
        if(!(context instanceof MainContext))
            throw new KayJamRuntimeException(expression, "Class declaration can only be in the main container");

        return Void.INSTANCE;
    }
}
