package com.arno.demo.life.other.annotation;

import com.arno.demo.life.other.annotation.ann.ClassPolicy;
import com.arno.demo.life.other.annotation.ann.RuntimePolicy;
import com.arno.demo.life.other.annotation.ann.SourcePolicy;

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