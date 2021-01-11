package com.arno.demo.life.annotation;

import com.arno.demo.life.annotation.ann.ClassPolicy;
import com.arno.demo.life.annotation.ann.RuntimePolicy;
import com.arno.demo.life.annotation.ann.SourcePolicy;

public class RetentionTest {

    @SourcePolicy
    public void sourcePolicy() {
    }

    @ClassPolicy
    public void classPolicy() {
    }

    @RuntimePolicy
    public void runtimePolicy() {
    }
}