package com.github.kayjam.executor;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.containers.ObjectContainer;

import java.util.List;

public class TypeUtils {

    public static boolean isAccept(List<Function.Argument> arguments, List<Object> objects){
        if(arguments.size()!=objects.size())
            return false;

        for (int num = 0; num < arguments.size(); num++)
            if(!isAccept(arguments.get(num).type, objects.get(num)))
                return false;

        return true;
    }

    public static boolean isAccept(Type type, Object value){
        if(type==Type.OBJECT&&!(value instanceof ObjectContainer))
            return false;

        if(type==Type.ANY)
            return true;

        return type.typeClass.equals(value.getClass());
    }
}
