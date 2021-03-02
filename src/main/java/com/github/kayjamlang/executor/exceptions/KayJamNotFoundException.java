package com.github.kayjamlang.executor.exceptions;

import com.github.kayjamlang.core.Expression;

public class KayJamNotFoundException extends KayJamRuntimeException {

    public KayJamNotFoundException(Expression expression, String type, String name) {
        super(expression, "Not found \""+name+"\" "+type);
    }

    public KayJamNotFoundException(Expression expression, String message) {
        super(expression, "Not found "+message);
    }
}
