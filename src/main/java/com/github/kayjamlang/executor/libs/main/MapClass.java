package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Argument;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.libs.Library;

import java.util.HashMap;
import java.util.Map;

public class MapClass extends Library.LibClass {

    public static final String FIELD_MAP = "m";

    public MapClass() throws Exception {
        super("map", null);
        addConstructor(new Library.LibConstructor((mainContext, context) ->
                context.parentContext.variables.put(FIELD_MAP, new HashMap<>())));
        addFunction(new Library.LibFunction("put", (mainContext, context) -> {
            Map<Object, Object> map = getVariable(context, FIELD_MAP);
            map.put(context.variables.get("key"), context.variables.get("value"));
            return context.parentContext.parent;
        }, new Argument(Type.ANY, "key"),
                new Argument(Type.ANY, "value")));

        addFunction(new Library.LibFunction("get", (mainContext, context) -> {
            Map<Object, Object> map = getVariable(context, FIELD_MAP);
            return map.getOrDefault(context.variables.get("key"), false);
        }, new Argument(Type.ANY, "key")));

        addFunction(new Library.LibFunction("get", (mainContext, context) -> {
            Map<Object, Object> map = getVariable(context, FIELD_MAP);
            return map.getOrDefault(context.variables.get("key"), context.variables.get("defaultValue"));
        }, new Argument(Type.ANY, "key"),
                new Argument(Type.ANY, "defaultValue")));
    }

    public Map<Object, Object> getMap(Context context){
        return getVariable(context, FIELD_MAP);
    }
}
