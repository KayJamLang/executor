package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.FunctionRefExpression;
import com.github.kayjamlang.core.expressions.data.Argument;

import java.util.ArrayList;
import java.util.List;

public class TypeUtils {
    public static final Type UNCHECK = Type.of("~unchecked", false);
    public static final List<Type> numberTypes = new ArrayList<>();

    static {
        numberTypes.add(Type.DOUBLE);
        numberTypes.add(Type.LONG);
        numberTypes.add(Type.INTEGER);
    }

    @Deprecated
    public static boolean isAccept(List<Argument> arguments, List<Object> objects){
        if(arguments.size()!=objects.size())
            return false;

        for (int num = 0; num < arguments.size(); num++)
            if(!isAccept(arguments.get(num).type, objects.get(num)))
                return false;

        return true;
    }

    public static boolean isAccept(MainContext mainContext, List<Argument> arguments, List<Type> types){
        if(arguments.size()!=types.size())
            return false;

        if(arguments.size()==0)
            return true;

        for (int num = 0; num < arguments.size(); num++)
            if(isAccept(mainContext, arguments.get(num).type, types.get(num)))
                return true;

        return false;
    }

    public static Type getType(Class<?> clazz){
        if(clazz==ClassContainer.class)
            return Type.OBJECT;

        return isNumber(clazz)?
                Type.INTEGER:
                clazz.getSimpleName().equals("double") ?
                Type.DOUBLE: Type.getType(clazz);
    }

    public static Number getNumberType(Type type, Number number){
        if(type.equals(Type.DOUBLE))
            return number.doubleValue();
        else if(type.equals(Type.LONG))
            return number.longValue();

        return number.intValue();
    }


    public static boolean isNumber(Class<?> clazz){
        switch (clazz.getSimpleName()){
            case "int":
            case "short":
            case "long":
            case "float":
                return true;
        }

        return false;
    }

    public static Type getMainType(Type one, Type two){
        int oneIndex = numberTypes.indexOf(one);
        int twoIndex = numberTypes.indexOf(two);

        return oneIndex>twoIndex?one:two;
    }

    public static boolean isAccept(MainContext ctx, Type type, Type valueType){
        if(type.equals(Type.ANY))
            return true;

        if(type.primitive&&valueType.primitive)
            return type.isAccept(valueType) &&
                    (!valueType.nullable || type.nullable);
        else if(ctx.classes.containsKey(valueType.name)){
            ClassContainer classContainer = ctx.classes.get(valueType.name);
            while (classContainer!=null){
                if(classContainer.name.equals(type.name))
                    return false;

                classContainer = (ClassContainer) classContainer.data
                        .getOrDefault("extends", null);
            }
        }

        return true;
    }

    @Deprecated
    public static boolean isAccept(Type type, Object value){
        if(value==null)
            return type.nullable;

        if(type==Type.ANY)
            return true;

        if(type == Type.VOID && value == Void.INSTANCE)
            return true;

        if(type==Type.OBJECT&&value instanceof ObjectContainer)
            return true;

        if(type==Type.FUNCTION_REF&&value instanceof FunctionRefExpression)
            return true;

        if(value instanceof Number)
            return type == Type.INTEGER;

        if(value instanceof ClassContainer){
            ClassContainer classContainer = (ClassContainer) value;
            while (classContainer!=null){
                if(classContainer.name.equals(type.name))
                    return true;

                classContainer = (ClassContainer) classContainer.data
                        .getOrDefault("extends", null);
            }

            return false;
        }

        return type.typeClass.isAssignableFrom(value.getClass());
    }
}
