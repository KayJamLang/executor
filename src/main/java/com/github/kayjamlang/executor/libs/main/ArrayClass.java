package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Argument;
import com.github.kayjamlang.core.Range;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.libs.Library;

import java.util.LinkedList;
import java.util.List;

public class ArrayClass extends Library.LibClass {

    public ArrayClass() throws Exception {
        super("array", null);

        addConstructor(new Library.LibConstructor((mainContext, context) ->
                context.variables.put("array", new LinkedList<>())));
        addFunction(new Library.LibFunction("add", (mainContext, context) -> {
            List<Object> array = getVariable(context, "array");
            array.add(array.size(), context.variables.get("value"));
            return ArrayClass.this;
        }, new Argument(Type.ANY, "value")));

        addFunction(new Library.LibFunction("get", (mainContext, context) ->
                get(context, ((Number) context.variables.get("position")).intValue(), false),
                new Argument(Type.INTEGER, "position")));

        addFunction(new Library.LibFunction("get", (mainContext, context) ->
                get(context, ((Number) context.variables.get("position")).intValue(),
                        context.variables.get("defaultValue")),
                new Argument(Type.INTEGER, "position"),
                new Argument(Type.ANY, "defaultValue")));

        addFunction(new Library.LibFunction("getRange", (mainContext, context) -> {
            List<Object> array = getVariable(context, "array");
            return new Range(0L, (long) array.size(), 1L);
        }));

        addFunction(new Library.LibFunction("size", (mainContext, context) ->{
            List<Object> array = getVariable(context, "array");
            return array.size();
        }));

        addFunction(new Library.LibFunction("getReversedRange", (mainContext, context) -> {
            List<Object> array = getVariable(context, "array");
            return new Range((long) array.size(), 0L, 1L);
        }));
    }

    public Object get(Context ctx, int position, Object defaultValue){
        List<Object> array = getVariable(ctx, "array");
        if(position>=array.size())
            return defaultValue;

        return array.get(position);
    }
}
