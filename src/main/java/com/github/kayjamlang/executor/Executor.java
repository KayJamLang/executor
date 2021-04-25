package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.containers.*;
import com.github.kayjamlang.core.expressions.*;
import com.github.kayjamlang.core.expressions.loops.ForExpression;
import com.github.kayjamlang.core.expressions.loops.WhileExpression;
import com.github.kayjamlang.core.provider.MainExpressionProvider;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.executors.*;
import com.github.kayjamlang.executor.libs.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Executor extends MainExpressionProvider<Object, Context, MainContext> {

    private final List<Library> libraries = new ArrayList<>();
    private UseGetFile useGetFileListener;

    public Executor() {
        super(Void.INSTANCE);

        //Containers
        addProvider(Container.class, new ContainerExecutor());
        addProvider(ObjectContainer.class, new ObjectExecutor());
        addProvider(ClassContainer.class, new ClassContainerExecutor());

        //Variables
        addProvider(VariableExpression.class, new VariableExpressionExecutor());
        addProvider(VariableLinkExpression.class, new VariableLinkExpressionExecutor());
        addProvider(VariableSetExpression.class, new VariableSetExpressionExecutor());

        //Execute libraries functions
        addProvider(Library.CodeExecExpression.class, new CodeExecExpressionExecutor());

        //Other
        addProvider(AccessExpression.class, new AccessExpressionExecutor());
        addProvider(GetExpression.class, new GetExpressionExecutor());
        addProvider(FunctionRefExpression.class, new FunctionRefExpressionExecutor());
        addProvider(NamedExpression.class, new NamedExpressionExecutor());
        addProvider(CompanionAccessExpression.class, new CompanionAccessExpressionExecutor());
        addProvider(ValueExpression.class, new ValueExpressionExecutor());
        addProvider(ArrayExpression.class, new ArrayExpressionExecutor());
        addProvider(ReturnExpression.class, new ReturnExpressionExecutor());
        addProvider(IfExpression.class, new IfExpressionExecutor());
        addProvider(CallOrCreateExpression.class, new CallCreateExpressionExecutor());
        addProvider(OperationExpression.class, new OperationExpressionExecutor());
        addProvider(WhileExpression.class, new WhileExpressionExecutor());
        addProvider(ForExpression.class, new ForExpressionExecutor());
        addProvider(AssertNullExpression.class, new AssertNullExpressionExecutor());
        addProvider(CastExpression.class, new CastExpressionExecutor());
        addProvider(IsExpression.class, new IsExpressionExecutor());
    }

    public void addLibrary(Library library){
        libraries.add(library);
    }

    public Object execute(String code) throws Exception {
        if(!code.startsWith("{")||!code.endsWith("}"))
            code = "{"+code+"}";

        KayJamParser parser = new KayJamParser(new KayJamLexer(code));
        return execute((Container) parser.readExpression());
    }

    public Object executeWithOldContext(String code) throws Exception {
        if(!code.startsWith("{")||!code.endsWith("}"))
            code = "{"+code+"}";

        KayJamParser parser = new KayJamParser(new KayJamLexer(code));
        return executeWithOldContext((Container) parser.readExpression());
    }

    public Object execute(Container container) throws Exception {
        mainContext = new MainContext(this, container, null);
        for(Library library: libraries){
            mainContext.classes.putAll(library.classes);
            container.functions.addAll(library.functions);
        }


        return executeWithOldContext(container);
    }

    public Object executeWithOldContext(Container container) throws Exception {
        boolean useEnded = false;
        for(Expression expression: container.children) {
            if(expression instanceof UseExpression){
                if(!useEnded) {
                    if (useGetFileListener == null)
                        throw new KayJamRuntimeException(expression,
                                "Use expression unsupported");

                    UseExpression use = (UseExpression) expression;
                    Container container1 = handleUse(use.expression);
                    if(container1==null)
                        throw new KayJamRuntimeException(expression,
                                "Unknown error");

                    mainContext.parent.functions.addAll(container1.functions);
                    executeWithOldContext(container1);
                }else throw new KayJamRuntimeException(expression,
                        "The use statements must be at the beginning of the file");
            }else if(!useEnded)
                useEnded = true;

            if (expression instanceof ClassContainer) {
                ClassContainer classContainer = (ClassContainer) expression;
                if (mainContext.classes.containsKey(classContainer.name))
                    throw new KayJamRuntimeException(classContainer,
                            "A class with the same name has already been declared");

                for(Expression classExpression: classContainer.children)
                    if(!classExpression.getClass().equals(VariableExpression.class)&&
                            !(classExpression instanceof ConstructorContainer))
                        throw new KayJamRuntimeException(
                                classExpression, "The class can only contain variables and functions");

                mainContext.classes.put(classContainer.name, classContainer);
            }
        }

        for(Map.Entry<String, ClassContainer> entry: mainContext.classes.entrySet()) {
            ClassContainer classContainer = entry.getValue();

            for(String string: classContainer.implementsClass) {
                if (!mainContext.classes.containsKey(string))
                    throw new KayJamNotFoundException(classContainer,
                            "class interface", string);

                ClassContainer implementsClass = mainContext.classes.get(string);
                for(FunctionContainer function: implementsClass.functions){
                    FunctionContainer fun = findFunction(classContainer, function);
                    if(!fun.returnType.name.equals(function.returnType.name))
                        throw new KayJamRuntimeException(fun, "Invalid return type");
                }

                classContainer.children.addAll(implementsClass.children);
            }

            if(classContainer.extendsClass!=null){
                if(!mainContext.classes.containsKey(classContainer.extendsClass))
                    throw new KayJamNotFoundException(classContainer,
                            "class", classContainer.extendsClass);

                classContainer.data.put("extends", mainContext.classes.get(classContainer.extendsClass));
            }

            if(classContainer.companion!=null){
                Context ctx = new Context(classContainer.companion, mainContext, false);
                for(Expression child: classContainer.companion.children){
                    if(child.getClass()==VariableExpression.class)
                        provide(child, ctx, ctx);
                }

                classContainer.companion.data.put("ctx", ctx);
            }
        }


        return provide(container,
                mainContext, mainContext);
    }

    private Container handleUse(Expression expression) throws KayJamRuntimeException {
        if(expression instanceof ArrayExpression){
            ArrayExpression array = (ArrayExpression) expression;
            for(Expression value: array.values)
                return handleUse(value);
        }else if(expression instanceof ValueExpression){
            return useGetFileListener.getFile(((ValueExpression) expression).value.toString());
        }

        throw new KayJamRuntimeException(expression, "Expected array or string value");
    }

    private FunctionContainer findFunction(ClassContainer classContainer, FunctionContainer function) throws KayJamRuntimeException {
        for(FunctionContainer fun: classContainer.functions){
            if(fun.arguments.size()==function.arguments.size()&&
                fun.name.equals(function.name)){

                boolean args = true;
                for (int i = 0; i < fun.arguments.size(); i++) {
                    if (!fun.arguments.get(i)
                            .type.name.equals(function.arguments.get(i).type.name)) {
                        args = false;
                        break;
                    }
                }

                if(args) {
                    return fun;
                }
            }
        }

        throw new KayJamRuntimeException(classContainer,
                "Function "+function.name+" not found from implements class");
    }

    public Executor setUseGetFileListener(UseGetFile useGetFileListener) {
        this.useGetFileListener = useGetFileListener;
        return this;
    }

    public interface UseGetFile{
        Container getFile(String path);
    }
}
