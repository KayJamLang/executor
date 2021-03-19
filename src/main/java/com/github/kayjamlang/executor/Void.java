package com.github.kayjamlang.executor;

public class Void {
    public static final Void INSTANCE = new Void();

    private Void(){}

    @Override
    public String toString() {
        return "void";
    }
}
