package com.arno.demo.life.utils

import java.text.SimpleDateFormat

object DateUtil {

    /**
     * 获取当前年月日,时分秒
     */
    val nowDateTime: String
        get() {
            val sdf = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss")
            return sdf.format(this)
        }

    /**
     * 获取当前年月日
     */
    val NowDate: String
        get() {
            val sdf = SimpleDateFormat("yyyy.MM.dd")
            return sdf.format(this)
        }

    /**
     * 获取当前时分秒
     */
    val NowTime: String
        get() {
            val sdf = SimpleDateFormat("HH:mm:ss")
            return sdf.format(this)
        }

    /**
     * 获取当前时分秒,精确到毫秒
     */
    val NowTimeDetail: String
        get() {
            val sdf = SimpleDateFormat("HH:mm:ss.SSS")
            return sdf.format(this)
        }
}