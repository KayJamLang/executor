package com.github.kayjam.executor.exceptions;

import com.github.kayjamlang.core.Expression;
import com.github.kayjamlang.core.exceptions.runtime.RuntimeException;

public class KayJamNotFoundException extends RuntimeException {

    public KayJamNotFoundException(Expression expression, String type, String name) {
        super(expression, "Not found \""+name+"\" "+type);
    }

    public KayJamNotFoundException(Expression expression, String message) {
        super(expression, "Not found "+message);
    }
}
