package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.libs.Library;

import java.util.HashMap;
import java.util.Map;

public class MapClass extends Library.LibClass {

    public static final String FIELD_MAP = "m";

    public static MapClass create(Executor executor, Map<?, ?> map) throws Exception {
        MapClass mapClass = new MapClass();
        Context context = new Context(mapClass, executor.mainContext, false);
        context.addVariable(FIELD_MAP, map);

        mapClass.data.put("ctx", context);
        return mapClass;
    }

    public MapClass() throws Exception {
        super("map", null);
        addConstructor(new Library.LibConstructor((mainContext, context) ->{
                context.parentContext.addVariable(FIELD_MAP, new HashMap<>());
                return Void.TYPE;
        }));
        addConstructor(new Library.LibConstructor((mainContext, context) ->
                context.parentContext.variables.put(FIELD_MAP, context.getVariable(FIELD_MAP)),
                new Argument(Type.of(FIELD_MAP), FIELD_MAP)));
        addFunction(new Library.LibFunction("put", Type.of("map"), (mainContext, context) -> {
            Map<Object, Object> map = getVariable(context, FIELD_MAP);
            map.put(context.getVariable("key"), context.getVariable("value"));
            return this;
        }, new Argument(Type.ANY, "key"),
                new Argument(Type.ANY, "value")));

        addFunction(new Library.LibFunction("get", Type.ANY, (mainContext, context) -> {
            Map<Object, Object> map = getVariable(context, FIELD_MAP);
            return map.getOrDefault(context.getVariable("key"), false);
        }, new Argument(Type.ANY, "key")));

        addFunction(new Library.LibFunction("remove", Type.of("map"), (mainContext, context) -> {
                    Map<Object, Object> map = getVariable(context, FIELD_MAP);
                    map.remove(context.getVariable("key"), context.getVariable("value"));
                    return this;
        }, new Argument(Type.ANY, "key"),
                new Argument(Type.ANY, "value")));

        addFunction(new Library.LibFunction("get", Type.of("map"), (mainContext, context) -> {
            Map<Object, Object> map = getVariable(context, FIELD_MAP);
            return map.getOrDefault(context.getVariable("key"),
                    context.getVariable("defaultValue"));
        }, new Argument(Type.ANY, "key"),
                new Argument(Type.ANY, "defaultValue")));
    }

    @Override
    public String toString() {
        if(data.containsKey("ctx"))
            return getVariable((Context) data.get("ctx"), MapClass.FIELD_MAP)
                    .toString();

        return super.toString();
    }

    public Map<Object, Object> getMap(Context context){
        return getVariable(context, FIELD_MAP);
    }

    public Map<Object, Object> getMap(){
        return getVariable((Context) data.get("ctx"), FIELD_MAP);
    }
}
