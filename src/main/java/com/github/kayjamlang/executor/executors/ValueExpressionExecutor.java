package com.github.kayjamlang.executor.executors;

import com.github.kayjamlang.core.expressions.ValueExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.core.provider.ValueExpressionProvider;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueExpressionExecutor extends ValueExpressionProvider<Object, Context, MainContext> {
    private static final Pattern pattern = Pattern.compile("\\{\\$(\\w+)\\}");
    private static final Pattern posix = Pattern.compile("\\\\(.)");

    @Override
    public Object provide(MainExpressionProvider<Object, Context, MainContext> mainProvider,
                          Context context,
                          Context argsContext,
                          ValueExpression expression) throws KayJamNotFoundException {
        if(expression.value instanceof String){
            String string = (String) expression.value;

            Matcher matcher = posix.matcher(string);
            while (matcher.find()){
                String posixSymbol = matcher.group(1);
                switch (posixSymbol){
                    case "0": posixSymbol = "\0";
                            break;
                    case "t": posixSymbol = "\t";
                            break;
                    case "b": posixSymbol = "\b";
                            break;
                    case "r":
                    case "n": posixSymbol = "\n";
                        break;

                    case "f": posixSymbol = "\f";
                        break;
                }

                string = matcher.replaceFirst(posixSymbol);
            }

            matcher = pattern.matcher(string);
            while (matcher.find()){
                Object value = argsContext
                        .variables.getOrDefault(matcher.group(1),
                                context.variables.getOrDefault(matcher.group(1),
                                        null));

                if(value==null)
                    throw new KayJamNotFoundException(expression, "var", matcher.group(1));

                string = matcher.replaceFirst(value.toString());
            }

            return string;
        }

        return expression.value;
    }
}
