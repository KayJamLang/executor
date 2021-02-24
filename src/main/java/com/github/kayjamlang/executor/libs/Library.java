package com.github.kayjamlang.executor.libs;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.Function;
import com.github.kayjamlang.core.containers.ObjectContainer;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.Return;
import com.github.kayjamlang.core.expressions.Variable;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;
import com.github.kayjamlang.core.provider.Context;
import com.github.kayjamlang.core.provider.MainContext;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;

import java.util.*;

public class Library {
    public final List<LibFunction> functions = new ArrayList<>();
    public final Map<String, ClassContainer> classes = new HashMap<>();

    public static class LibFunction extends Function {

        public LibFunction(String name, CodeExecExpression.Code code, Argument... arguments) {
            super(name, new ArrayList<>(), AccessIdentifier.NONE, Arrays.asList(arguments),
                    0, Type.ANY, new ArrayList<>());

            children.add(new Return(new CodeExecExpression(code), 0));
        }
    }

    public static class LibObject extends ObjectContainer{
        public LibObject(ObjectBind binder) {
            super(new ArrayList<>(), AccessIdentifier.NONE, 0);

            binder.bind(this);
        }

        public void addFunction(LibFunction function){
            functions.add(function);
        }

        public void addVariable(String name, Object value){
            children.add(new Variable(name, new Const(value, 0),
                    AccessIdentifier.PUBLIC, 0));
        }

        public interface ObjectBind {
            void bind(LibObject object);
        }
    }

    public static class LibClass extends ClassContainer {

        public LibClass(String name, ClassBind binder) throws Exception {
            super(name, null, new ArrayList<>(), new ArrayList<>(),
                    AccessIdentifier.NONE, 0);

            binder.bind(this);
        }

        public void setCompanion(LibObject object){
            companion = object;
        }

        public void addFunction(LibFunction function){
            functions.add(function);
        }

        public void addVariable(String name, Object value){
            children.add(new Variable(name, new Const(value, 0),
                    AccessIdentifier.PUBLIC, 0));
        }

        public interface ClassBind {
            void bind(LibClass clazz);
        }
    }

    public static class CodeExecExpression extends Expression {

        public final Code code;

        public CodeExecExpression(Code code) {
            super(AccessIdentifier.NONE, 0);
            this.code = code;
        }

        public interface Code {
            Object execute(MainContext mainContext, Context context) throws KayJamRuntimeException;
        }
    }
}
