package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.expressions.data.Range;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.executor.ClassUtils;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.libs.Library;

import java.util.LinkedList;
import java.util.List;

public class ArrayClass extends Library.LibClass {

    public static ArrayClass create(Executor executor, List<?> array) throws Exception {
        ArrayClass arrayClass = new ArrayClass();
        Context context = new Context(arrayClass, executor.mainContext, false);
        context.addVariable("array", array);

        arrayClass.data.put("ctx", context);
        return arrayClass;
    }

    public ArrayClass() throws Exception {
        super("array", null);

        addConstructor(new Library.LibConstructor((mainContext, context) ->{
                context.parentContext.addVariable("array", new LinkedList<>());
                return Void.TYPE;
        }));

        addConstructor(new Library.LibConstructor((mainContext, context) ->
                context.parentContext.variables.put("array", context.getVariable("array")),
                new Argument(Type.of("arr"),
                        "array")));

        addFunction(new Library.LibFunction("add", Type.of("array"), (mainContext, context) -> {
            List<Object> array = getVariable(context, "array");
            array.add(array.size(), context.getVariable("value"));
            return ArrayClass.this;
        }, new Argument(Type.ANY, "value")));

        addFunction(new Library.LibFunction("get", Type.ANY, (mainContext, context) ->
                get(context, context.getVariable("position"), false),
                new Argument(Type.INTEGER, "position")));

        addFunction(new Library.LibFunction("get", Type.ANY, (mainContext, context) ->
                get(context, context.getVariable("position"),
                        context.getVariable("defaultValue")),
                new Argument(Type.INTEGER, "position"),
                new Argument(Type.ANY, "defaultValue")));

        addFunction(new Library.LibFunction("getRange", Type.RANGE, (mainContext, context) -> {
            List<Object> array = getVariable(context, "array");
            return new Range(0, array.size(), 1L);
        }));

        addFunction(new Library.LibFunction("size", Type.INTEGER, (mainContext, context) ->{
            List<Object> array = getVariable(context, "array");
            return array.size();
        }));

        addFunction(new Library.LibFunction("getReversedRange", Type.RANGE, (mainContext, context) -> {
            List<Object> array = getVariable(context, "array");
            return new Range(array.size(), 0, 1);
        }));
    }

    public Object get(Context ctx, int position, Object defaultValue){
        List<Object> array = getVariable(ctx, "array");
        if(position>=array.size())
            return defaultValue;

        return array.get(position);
    }

    @Override
    public String toString() {
        if(data.containsKey("ctx"))
            return getVariable((Context) data.get("ctx"), "array").toString();

        return super.toString();
    }
}
