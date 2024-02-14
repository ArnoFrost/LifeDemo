package com.arno.demo.life.other.proxy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arno.demo.life.R
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

class ProxyLearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proxy_learn)
        invokeTest()

    }

    private fun invokeTest() {
        val myInterfaceImpl = MyInterfaceImpl()
        val handler = MyInvocationHandler(myInterfaceImpl)

        val proxy = Proxy.newProxyInstance(
            MyInterface::class.java.classLoader,
            arrayOf(MyInterface::class.java),
            handler
        ) as MyInterface

        proxy.myMethod()
    }

    interface MyInterface {
        fun myMethod()
    }

    class MyInterfaceImpl : MyInterface {
        override fun myMethod() {
            println("MyInterfaceImpl myMethod")
        }
    }

    class MyInvocationHandler(private val target: Any) : InvocationHandler {
        override fun invoke(
            proxy: Any?,
            method: java.lang.reflect.Method?,
            args: Array<out Any>?
        ): Any? {
            println("MyInvocationHandler invoke")
            return if (args == null) {
                method?.invoke(target)
            } else {
                method?.invoke(target, *args)
            }
        }
    }
}