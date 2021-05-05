package com.github.kayjamlang.executor.libs;

import com.github.kayjamlang.core.exceptions.TypeException;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.*;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;

import java.util.*;

public class Library implements Lib {

    public final List<FunctionContainer> functions = new ArrayList<>();
    public final Map<String, ClassContainer> classes = new HashMap<>();

    public static class LibFunction extends FunctionContainer {

        public LibFunction(String name, Type returnType,
                           CodeExecExpression.Code code, Argument... arguments) {
            this(name, returnType, code, Arrays.asList(arguments));

            children.add(new ReturnExpression(new CodeExecExpression(code), 0));
        }

        public LibFunction(String name, Type returnType,
                           CodeExecExpression.Code code, List<Argument> arguments) {
            super(name, new ArrayList<>(), AccessIdentifier.NONE, arguments,
                    0, returnType, new ArrayList<>());

            children.add(new ReturnExpression(new CodeExecExpression(code), 0));
        }
    }


    @Deprecated
    @Override public void addVariable(String name, Object value) {}

    @Override
    public void addFunction(LibFunction function) {
        functions.add(function);
    }

    public static class LibNamedFunction extends NamedExpressionFunctionContainer {
        public LibNamedFunction(String name, Runnable runnable) {
            super(name, new ArrayList<>(), AccessIdentifier.NONE, 0);

            children.add(new ReturnExpression(new CodeExecExpression(
                    (mainContext, context) -> {
                        runnable.run(mainContext, context,
                                ((NamedExpression) context.getVariable(name)).expression);
                        return null;
                    }), 0));
        }

        public interface Runnable{
            void run(MainContext mainContext, Context context, Expression expression);
        }
    }

    public static class LibObject extends ObjectContainer implements Lib {

        private final Context context = new Context(this, null,
                false);

        public LibObject(ObjectBind binder) throws Exception {
            super(new ArrayList<>(), AccessIdentifier.NONE, 0);

            if(binder!=null)
                binder.bind(this);

            data.put("ctx", context);
        }

        public void addFunction(LibFunction function){
            functions.add(function);
        }

        public void addVariable(String name, Object value){
            context.addVariable(name, value);
        }

        public interface ObjectBind {
            void bind(LibObject object);
        }
    }

    public static class LibConstructor extends ConstructorContainer{

        public LibConstructor(CodeExecExpression.Code code, Argument... args) {
            super(Arrays.asList(args),
                    Collections.singletonList(new CodeExecExpression(code)),
                    AccessIdentifier.NONE, 0);
        }
    }

    public static class LibClass extends ClassContainer implements Lib {

        public LibClass(String name, ClassBind binder) throws Exception {
            super(name, null, new ArrayList<>(), new ArrayList<>(),
                    AccessIdentifier.NONE, 0);

            if(binder!=null)
                binder.bind(this);
        }

        public void addConstructor(LibConstructor constructor){
            constructors.add(constructor);
        }

        public void setCompanion(LibObject object){
            companion = object;
        }

        public void addFunction(LibFunction function){
            functions.add(function);
        }

        public void addVariable(String name, Object value){
            children.add(new VariableExpression(name, new ValueExpression(value),
                    AccessIdentifier.PUBLIC, 0));
        }

        @SuppressWarnings("unchecked")
        public <T> T getVariable(Context ctx, String name){
            if(ctx.parent instanceof ClassContainer)
                return (T) ctx.getVariable(name);

            return (T) ctx.parentContext.getVariable(name);
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
            Object execute(MainContext mainContext, Context context) throws Exception ;
        }
    }
}
