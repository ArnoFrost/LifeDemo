package com.arno.demo.life.other.annotation.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//描述注解的作用范围 如方法
@Target(ElementType.METHOD)
//描述注解作用保留时间范围 如源文件 编译期 运行期
@Retention(RetentionPolicy.RUNTIME)
public @interface RunAnnotation {
}
