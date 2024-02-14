package com.arno.demo.life.base.utils

import kotlin.reflect.KClass
import kotlin.reflect.full.memberExtensionProperties
import kotlin.reflect.full.primaryConstructor

/**
 * Created by xuxin on 2021/10/27
 *
 * @author xuxin
 * @since 2021/10/27
 */
//fun <T : Any> T.deepCopy(): T {
//    if (!this::class.isData) {
//        return this
//    }
//    return this::class.primaryConstructor!!.let { constructor ->
//        constructor.parameters.map { parameter ->
//            val value =
//                (this::class as KClass<T>).memberExtensionProperties.first { it.name == parameter.name }
//                    .get(this)
//            if ((parameter.type.classifier as? KClass<*>)?.isData == true) {
//                parameter to value?.deepCopy()
//            } else {
//                parameter to value
//            }
//        }.toMap()
//            .let(constructor::callBy)
//    }
//}
