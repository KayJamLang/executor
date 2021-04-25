package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.CompanionAccessExpression;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;

public class CompanionAccessExpressionExecutor extends ExpressionExecutor<CompanionAccessExpression> {
    @Override
    public Object provide(Executor mainProvider,
                          Context context,
                          Context argsContext,
                          CompanionAccessExpression expression) throws Exception {
        if(mainProvider.mainContext.classes.containsKey(expression.className)){
            ClassContainer classContainer =
                    mainProvider.mainContext.classes.get(expression.className);

            Context rootContext = (Context) classContainer.companion.data.get("ctx");
            return mainProvider.provide(expression.child, rootContext, argsContext);
        }

        throw new KayJamNotFoundException(expression, "class", expression.className);
    }

    @Override
    public Type getType(Executor mainProvider,
                        Context context,
                        Context argsContext,
                        CompanionAccessExpression expression) throws TypeException {
        if(mainProvider.mainContext.classes.containsKey(expression.className)){
            ClassContainer classContainer =
                    mainProvider.mainContext.classes.get(expression.className);

            Context rootContext = (Context) classContainer.companion.data.get("ctx");
            Type type = mainProvider.getType(expression.child, rootContext, argsContext);
            return type;
        }

        return Type.VOID;
    }
}
