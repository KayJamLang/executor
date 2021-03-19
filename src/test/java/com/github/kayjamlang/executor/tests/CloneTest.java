package com.github.kayjamlang.executor.tests;

import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.opcodes.AccessIdentifier;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

public class CloneTest {

    @Test
    public void test() throws Exception {
        ClassContainer classContainer = new ClassContainer("test", null,
                Collections.emptyList(), Collections.emptyList(), AccessIdentifier.NONE, 0);
        classContainer.data.put("data", 123);
        ClassContainer clone = classContainer.clone();
        BeanUtils.copyProperties(classContainer, clone);
        assertNotEquals(classContainer, clone);
        assertNotSame(classContainer.data, clone.data);

        ClassContainer clone2 = classContainer.clone();
        assertNotEquals(clone, clone2);
    }
}
