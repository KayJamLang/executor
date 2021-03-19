package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.Argument;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.FunctionRef;

import java.util.List;

public class TypeUtils {

    public static boolean isAccept(List<Argument> arguments, List<Object> objects){
        if(arguments.size()!=objects.size())
            return false;

        for (int num = 0; num < arguments.size(); num++)
            if(!isAccept(arguments.get(num).type, objects.get(num)))
                return false;

        return true;
    }

    public static Type getType(Class<?> clazz){
        if(clazz==ClassContainer.class)
            return Type.OBJECT;

        return isNumber(clazz)?
                Type.INTEGER:
                Type.getType(clazz);
    }

    public static boolean isNumber(Class<?> clazz){
        switch (clazz.getSimpleName()){
            case "int":
            case "short":
            case "long":
            case "float":
            case "double":
                return true;
        }

        return false;
    }

    public static boolean isAccept(Type type, Object value){
        if(type==Type.ANY)
            return true;

        if(type==Type.OBJECT&&value instanceof ObjectContainer)
            return true;

        if(type==Type.FUNCTION_REF&&value instanceof FunctionRef)
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

        return type.typeClass.equals(value.getClass());
    }
}
