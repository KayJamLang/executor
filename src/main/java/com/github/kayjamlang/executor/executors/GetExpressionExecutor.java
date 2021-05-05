package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.GetExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.FunctionUtils;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.main.ArrayClass;
import com.github.kayjamlang.executor.libs.main.MapClass;
import com.github.kayjamlang.executor.utils.ExecutorUtils;

import java.util.Collections;

public class GetExpressionExecutor extends ExpressionExecutor<GetExpression> {
    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          GetExpression expression) throws Exception {
        Object root = ExecutorUtils.provideOnce(mainProvider, expression.root, context);
        if(root instanceof ClassContainer){
            ClassContainer classContainer = (ClassContainer) root;
            Context rootContext = (Context) classContainer.data.get("ctx");
            FunctionContainer func = rootContext.findFunction(mainProvider.mainContext,
                    expression, "get", Collections.singletonList(
                            mainProvider.getType(expression.value, context, argsContext)));

            return FunctionUtils
                    .callFunction(mainProvider, rootContext, func, Collections.singletonList(
                            mainProvider.provide(expression.value, context, argsContext)));
        }

        throw new KayJamRuntimeException(expression.root, "unknown type");
    }

    @Override
    public Type getType(Executor mainProvider,
                        Context context,
                        Context argsContext,
                        GetExpression expression) throws TypeException {

        try{
            Object root = ExecutorUtils.provideOnce(mainProvider, expression.root, context);
            if(root instanceof ClassContainer){
                ClassContainer classContainer = (ClassContainer) root;
                Context rootContext = (Context) classContainer.data.get("ctx");
                FunctionContainer func = rootContext.findFunction(mainProvider.mainContext,
                        expression, "get", Collections.singletonList(
                                mainProvider.getType(expression.value, context, argsContext)));

                return func.returnType;
            }
        }catch (Exception ignored){}

        return super.getType(mainProvider, context, argsContext, expression);
    }
}
