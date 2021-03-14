package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Argument;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.executor.LibraryUtils;
import com.github.kayjamlang.executor.libs.Library;

import java.util.HashMap;

public class MapClass extends Library.LibClass {

    public HashMap<Object, Object> map = new HashMap<>();

    public MapClass() throws Exception {
        super("map", null);
        addFunction(new Library.LibFunction("put", (mainContext, context) -> {
            map.put(context.variables.get("key"), context.variables.get("value"));
            return MapClass.this;
        }, new Argument(Type.ANY, "key"),
                new Argument(Type.ANY, "value")));

        addFunction(new Library.LibFunction("get", (mainContext, context) ->
                map.getOrDefault(context.variables.get("key"), false),
                new Argument(Type.ANY, "key")));

        addFunction(new Library.LibFunction("get", (mainContext, context) ->
                map.getOrDefault(context.variables.get("key"), context.variables.get("defaultValue")),
                new Argument(Type.ANY, "key"),
                new Argument(Type.ANY, "defaultValue")));
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
