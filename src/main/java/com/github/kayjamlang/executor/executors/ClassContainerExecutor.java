package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainContext;
import com.github.kayjamlang.core.provider.MainExpressionProvider;

public class ClassContainerExecutor extends ExpressionProvider<ClassContainer, Object> {

    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
                          Context context,
                          Context argsContext,
                          ClassContainer expression) throws Exception {
        if(!(context instanceof MainContext))
            throw new KayJamRuntimeException(expression, "Class declaration can only be in the main container");

        return null;
    }
}
