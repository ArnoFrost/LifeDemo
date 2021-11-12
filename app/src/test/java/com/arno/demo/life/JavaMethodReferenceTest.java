package com.arno.demo.life;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author: xuxin
 *     time  : 2021/10/26
 *     desc  : Java 方法引用测试
 * </pre>
 */
public class JavaMethodReferenceTest {

    interface Single {
        void doAction(String name);
    }

    interface Multi {
        void doAction1(String name);

        void doAction2(String name);
    }

    static class TestReference {
        void testSingle(Single i) {
            i.doAction("testSingle");
        }

        void testMulti(Multi i) {
            i.doAction1("testMulti1");
            i.doAction2("testMulti2");
        }

        String testInvoke(String name) {
            return "hello";
        }
    }

    @Test
    public void testReference() {
        TestReference reference = new TestReference();
        reference.testSingle(reference::testInvoke);
    }

    @Test
    public void testStaticMethodReference() {
        List<String> list = Arrays.asList("aaaa", "bbbb", "cccc");

        //静态方法语法	ClassName::methodName
        list.forEach(JavaMethodReferenceTest::printByStatic);
        //对象实例语法	instanceRef::methodName
        JavaMethodReferenceTest test = new JavaMethodReferenceTest();
        list.forEach(test::print);
        //对象的超类方法语法： super::methodName
        list.forEach(super::equals);

        //类构造器语法
//        Single single = Single::new;


    }

    public void print(String content) {
        System.out.println(content);
    }

    public static void printByStatic(String content) {
        System.out.println(content);
    }
}