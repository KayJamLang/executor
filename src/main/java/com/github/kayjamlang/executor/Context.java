package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.Expression;
import com.github.kayjamlang.core.containers.Container;
import com.github.kayjamlang.core.containers.FunctionContainer;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context implements Cloneable {
    private static int count = 0;

    public final int id;
    public final Container parent;
    public final Context parentContext;
    public final boolean useParentVars;
    public final Map<String, LocalVariable> variables = new HashMap<>();

    public Context(Container parent, Context parentContext, boolean useParentVars) {
        count++;

        this.id = count;
        this.parent = parent;
        this.parentContext = parentContext;
        this.useParentVars = useParentVars;

        if(parentContext!=null&&useParentVars)
            variables.putAll(parentContext.variables);
    }

    public void clearVariables(){
        variables.clear();
        if(parentContext!=null&&useParentVars)
            variables.putAll(parentContext.variables);
    }

    public Context(Container parent, Context parentContext) {
        this(parent, parentContext, true);
    }

    public FunctionContainer findFunction(MainContext context,
                                          Expression expression, String name, List<Type> args)
            throws KayJamNotFoundException {

        for(FunctionContainer function: parent.functions)
            if(function.name.equals(name)) {
                if(function.arguments.size()==args.size()&&
                        TypeUtils.isAccept(context, function.arguments, args)){
                    return function;
                }
            }

        if(parentContext!=null)
            return parentContext.findFunction(context, expression, name, args);

        throw new KayJamNotFoundException(expression, "function", name);
    }

    public void addVariable(String name, Object value){
        variables.put(name, new LocalVariable(TypeUtils.getType(value.getClass()), value));
    }

    @SuppressWarnings("unchecked")
    public <T> T getVariable(String name){
        return (T) variables.get(name).value;
    }

    public boolean setVariable(String name, Type type, Object value){
        if(useParentVars&&
                parentContext!=null&&parentContext.variables.containsKey(name)){
            variables.put(name, new LocalVariable(type, value));
            return parentContext.setVariable(name, type, value);
        }else if(variables.containsKey(name)) {
            variables.put(name, new LocalVariable(type, value));
            return true;
        }

        return false;
    }

    public static class LocalVariable {
        public final Type type;
        public final Object value;

        public LocalVariable(Type type, Object value) {
            this.type = type;
            this.value = value;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
