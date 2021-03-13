package com.github.kayjamlang.executor.libs.main;

import com.github.kayjamlang.executor.LibraryUtils;
import com.github.kayjamlang.executor.libs.Library;

public class MathClass extends Library.LibClass {

    public MathClass() throws Exception {
        super("Math", null);
        setCompanion(new Companion());
    }

    private static class Companion extends Library.LibObject{
        public Companion() {
            super(null);
            LibraryUtils.importFunctionsFormClass(this, Math.class);
        }
    }
}
