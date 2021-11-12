package com.arno.demo.life

import org.junit.Test

/**
 * <pre>
 *     author: xuxin
 *     time  : 2021/10/26
 *     desc  :
 * </pre>
 */
class KotlinProgrammerTest {
    interface SingleInterface {
        fun doAction(name: String)
    }

    interface MultiInterface {
        fun doAction1(name: String)
        fun doAction2(name: String)
    }

    class TestInvoke {
        fun testSingle(i: SingleInterface) {
            println(i)
        }

        fun testMulti(i: MultiInterface) {
            print(i)
        }

        fun hello(name: String) {
        }
    }

    @Test
    fun testInvoke() {
        val testInvoke = TestInvoke()

        testInvoke.testSingle(object : SingleInterface {
            override fun doAction(name: String) {
                testInvoke.hello(name)
            }
        })
    }
}
