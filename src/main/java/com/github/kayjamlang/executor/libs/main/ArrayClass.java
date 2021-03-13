package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.executor.libs.Library;

import java.util.LinkedList;
import java.util.List;

public class ArrayClass extends Library.LibClass {

    public List<Object> array = new LinkedList<>();

    public ArrayClass() throws Exception {
        super("array", null);
        addFunction(new Library.LibFunction("add", (mainContext, context) -> {
            array.add(array.size(), context.variables.get("value"));
            return ArrayClass.this;
        }, new Function.Argument(Type.ANY, "value")));

        addFunction(new Library.LibFunction("get", (mainContext, context) ->
                get(((Number) context.variables.get("position")).intValue(), false),
                new Function.Argument(Type.INTEGER, "position")));

        addFunction(new Library.LibFunction("get", (mainContext, context) ->
                get(((Number) context.variables.get("position")).intValue(),
                        context.variables.get("defaultValue")),
                new Function.Argument(Type.INTEGER, "position"),
                new Function.Argument(Type.ANY, "defaultValue")));
    }

    public Object get(int position, Object defaultValue){
        if(position>=array.size())
            return defaultValue;

        return array.get(position);
    }

    @Override
    public String toString() {
        return array.toString();
    }
}
