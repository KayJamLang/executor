package com.github.kayjamlang.executor.libs;

public interface Lib {
    void addFunction(Library.LibFunction function);
    void addVariable(String name, Object value);
}
