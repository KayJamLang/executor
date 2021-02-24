package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.expressions.CompanionAccess;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.ExpressionProvider;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;

public class CompanionAccessExecutor extends ExpressionProvider<CompanionAccess, Object> {
    @Override
    public Object provide(MainExpressionProvider<Object> mainProvider,
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
