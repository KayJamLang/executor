package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;
import com.github.kayjamlang.executor.executors.ContainerExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    public final Container parent;
    public final Context parentContext;
    public final boolean useParentVars;
    public final Map<String, Object> variables = new HashMap<>();

    public Context(Container parent, Context parentContext, boolean useParentVars) {
        this.parent = parent;
        this.parentContext = parentContext;
        this.useParentVars = useParentVars;

        if(parentContext!=null&&useParentVars)
            variables.putAll(parentContext.variables);
    }

    public Context(Container parent, Context parentContext) {
        this(parent, parentContext, true);
    }

    public Function findFunction(Expression expression, String name, List<Object> args)
            throws KayJamNotFoundException {

        for(Function function: parent.functions)
            if(function.name.equals(name)) {
                if(function.arguments.size()==args.size()&&
                        TypeUtils.isAccept(function.arguments, args)){
                    return function;
                }
            }

        if(parentContext!=null)
            return parentContext.findFunction(expression, name, args);

        throw new KayJamNotFoundException(expression, "function", name);
    }

    public boolean setVariable(String name, Object value){
        if(variables.containsKey(name)) {
            variables.put(name, value);
            return true;
        }else if(useParentVars&&
                parentContext!=null&&parentContext.variables.containsKey(name)){
            variables.put(name, value);
            return parentContext.setVariable(name, value);
        }

        return false;
    }
}
