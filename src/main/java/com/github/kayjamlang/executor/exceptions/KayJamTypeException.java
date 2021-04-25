package com.github.kayjamlang.executor.exceptions;

import com.github.kayjamlang.core.exceptions.TypeException;

public class KayJamTypeException extends TypeException {
    public KayJamTypeException(KayJamRuntimeException exception){
        initCause(exception);
    }
}
