package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.expressions.CompanionAccess;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;

public class CompanionAccessExecutor extends ExpressionExecutor<CompanionAccess> {
    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          CompanionAccess expression) throws Exception {
        if(mainProvider.mainContext.classes.containsKey(expression.className)){
            ClassContainer classContainer =
                    mainProvider.mainContext.classes.get(expression.className);

            Context rootContext = (Context) classContainer.companion.data.get("ctx");
            return mainProvider.provide(expression.child, rootContext, argsContext);
        }

        throw new KayJamNotFoundException(expression, "class", expression.className);
    }
}
