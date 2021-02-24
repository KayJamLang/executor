package com.github.kayjam.executor.exceptions;

import com.github.kayjamlang.core.Expression;

public class KayJamRuntimeException extends Exception {
    public KayJamRuntimeException(Expression expression, String message){
        super(message+" in "+expression.line+" line");
    }
}
