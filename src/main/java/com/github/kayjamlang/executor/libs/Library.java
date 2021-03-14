package com.github.kayjamlang.executor.libs;

import com.github.kayjamlang.core.Argument;
import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.*;
import com.github.kayjamlang.core.expressions.Const;
import com.github.kayjamlang.core.expressions.Return;
import com.github.kayjamlang.core.expressions.Variable;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.MainContext;

import java.util.*;

public class Library implements Lib {

    public final List<Function> functions = new ArrayList<>();
    public final Map<String, ClassContainer> classes = new HashMap<>();

    public static class LibFunction extends Function {

        public LibFunction(String name, CodeExecExpression.Code code, Argument... arguments) {
            this(name, code, Arrays.asList(arguments));

            children.add(new Return(new CodeExecExpression(code), 0));
        }

        public LibFunction(String name, CodeExecExpression.Code code, List<Argument> arguments) {
            super(name, new ArrayList<>(), AccessIdentifier.NONE, arguments,
                    0, Type.ANY, new ArrayList<>());

            children.add(new Return(new CodeExecExpression(code), 0));
        }
    }


    @Deprecated
    @Override public void addVariable(String name, Object value) {}

    @Override
    public void addFunction(LibFunction function) {
        functions.add(function);
    }

    public static class LibNamedFunction extends NamedExpressionFunction {
        public LibNamedFunction(String name, Runnable runnable) {
            super(name, new ArrayList<>(), AccessIdentifier.NONE, 0);

            children.add(new Return(new CodeExecExpression(
                    (mainContext, context) -> {
                        runnable.run(mainContext, context,
                                ((NamedExpression) context.variables.get(name)).expression);
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

        public LibObject(ObjectBind binder) {
            super(new ArrayList<>(), AccessIdentifier.NONE, 0);

            if(binder!=null)
                binder.bind(this);

            data.put("ctx", context);
        }

        public void addFunction(LibFunction function){
            functions.add(function);
        }

        public void addVariable(String name, Object value){
            context.variables.put(name, value);
        }

        public interface ObjectBind {
            void bind(LibObject object);
        }
    }

    public static class LibClass extends ClassContainer implements Lib {

        public LibClass(String name, ClassBind binder) throws Exception {
            super(name, null, new ArrayList<>(), new ArrayList<>(),
                    AccessIdentifier.NONE, 0);

            if(binder!=null)
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
            Object execute(MainContext mainContext, Context context) throws Exception;
        }
    }
}
