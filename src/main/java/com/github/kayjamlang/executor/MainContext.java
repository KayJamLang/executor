package com.github.kayjamlang.executor;

import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.containers.Container;

import java.util.HashMap;
import java.util.Map;

public class MainContext extends Context {

    public Map<String, ClassContainer> classes = new HashMap<>();
    public final Executor executor;

    public MainContext(Executor executor, Container parent, Context parentContext) {
        super(parent, parentContext);
        this.executor = executor;
    }
}
