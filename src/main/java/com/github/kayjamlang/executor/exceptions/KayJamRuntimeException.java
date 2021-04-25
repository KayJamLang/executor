package com.github.kayjamlang.executor.exceptions;


import com.github.kayjamlang.core.expressions.Expression;

public class KayJamRuntimeException extends Exception {
    public KayJamRuntimeException(Expression expression, String message){
        super(message+" in "+expression.line+" line");
    }
}
