package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Argument;
import com.github.kayjamlang.core.Range;
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
        }, new Argument(Type.ANY, "value")));

        addFunction(new Library.LibFunction("get", (mainContext, context) ->
                get(((Number) context.variables.get("position")).intValue(), false),
                new Argument(Type.INTEGER, "position")));

        addFunction(new Library.LibFunction("get", (mainContext, context) ->
                get(((Number) context.variables.get("position")).intValue(),
                        context.variables.get("defaultValue")),
                new Argument(Type.INTEGER, "position"),
                new Argument(Type.ANY, "defaultValue")));

        addFunction(new Library.LibFunction("getRange", (mainContext, context) ->
                new Range(0L, (long) array.size(), 1L)));

        addFunction(new Library.LibFunction("size", (mainContext, context) ->
                array.size()));

        addFunction(new Library.LibFunction("getReversedRange", (mainContext, context) ->
                new Range((long) array.size(), 0L, 1L)));
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
