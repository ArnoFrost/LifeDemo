package com.arno.demo.life

import org.junit.Test
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

/**
 * <pre>
 *     author: xuxin
 *     time  : 2021/10/26
 *     desc  :
 * </pre>
 */
class KotlinReflectTest {

    companion object {
        private const val TAG = "KotlinReflectTest"
    }

    @ExperimentalStdlibApi
    @Test
    fun reflectHello() {
        val stringCls: KClass<String> = String::class
        val mapCls = Map::class

//        println("$TAG kotlin class $stringCls,java class ${stringCls.java}")
//        println("$TAG kotlin class $mapCls,java class ${mapCls.java}")

//        val stringProperty = stringCls.declaredMemberProperties.firstOrNull()
//        println("$TAG stringProperty $stringProperty")

        // 给定泛型参数
        val mapType = typeOf<Map<String, Int>>()
        stringCls.members.forEach {
            println(it.name)
        }
        mapType.arguments.forEach {
            println(it)
        }
    }

    interface Api {
        fun getUsers(): List<User>
    }

    abstract class SuperType<T> {
        val typeParameter by lazy {
            // 最近一个继承
            this::class.supertypes.first()
                // 第一个泛型参数的类型
                .arguments.first().type!!
        }

        val typeParameterJava by lazy {
            // 最近一个继承
            this.javaClass.genericInterfaces.safeAs<ParameterizedType>()!!
                .actualTypeArguments.first()
        }
    }

    class SubType : SuperType<String>()

    /**
     * 获取泛型实参
     */
    @Test
    fun getTypeParams() {
//        // 1 传统方式
//        Api::class.declaredMemberFunctions.first { it.name == "getUsers" }
//            .returnType.arguments.forEach {
//                println(it)
//            }
//        // 2 直接拿到方法
//        Api::getUsers.returnType.arguments.forEach {
//            println(it)
//        }
//
//        // 3 java风格
//        (
//            Api::class.java.getDeclaredMethod("getUsers")
//                .genericReturnType as ParameterizedType
//            )
//            .actualTypeArguments.forEach {
//                println(it)
//            }
        // 4 缩写方式
        Api::class.java.getDeclaredMethodType("getUsers")
            .actualTypeArguments.forEach {
                println(it)
            }
    }

    /**
     * 在父类中获取子类泛型实参
     */
    @Test
    fun testClassType() {
        val subType = SubType()
        subType.typeParameter.let(::println)
        subType.typeParameterJava.let(::println)
    }
}

@SuppressWarnings("UNCHECKED_CAST")
fun <T> Any.safeAs(): T? {
    return this as? T
}

/**
 *
 * @receiver Class<*>
 * @param name String
 * @return ParameterizedType
 */
fun <T> Class<T>.getDeclaredMethodType(name: String): ParameterizedType {
    return this.getDeclaredMethod(name).genericReturnType as ParameterizedType
}
