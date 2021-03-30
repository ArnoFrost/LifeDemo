package com.arno.demo.life.utils

import com.arno.demo.life.dinner.bean.CardItemParams
import com.arno.demo.life.dinner.bean.CardParams
import java.text.SimpleDateFormat
import java.util.*

class DinnerDataHelper {

    companion object {
        const val LUNCH = 0

        fun getDefaultCard(isActive: Boolean): CardParams {
            return CardParams(
                getCurrentName(),
                getCurrentDateStr(),
                getCurrentTimeStr(),
                LUNCH,
                isActive
            )
        }

        /**
         * 获取下面展示列表
         */
        fun getDefaultCardList(): MutableList<CardItemParams> {
            val list = mutableListOf<CardItemParams>()
            //添加今天
            list.add(getDefaultCardItemParams(true))
            val random = Random()
            repeat(random.nextInt(7)) {
                list.add(getDefaultCardItemParams(random.nextBoolean()))
            }
            return list
        }

        fun getDefaultCardItemParams(dinner: Boolean): CardItemParams {

            return CardItemParams(
                "午餐",
                if (dinner) {
                    "晚餐"
                } else {
                    ""
                },
                getCurrentDateStr(),
                getCurrentTimeStr(),
                getCurrentTimeStr()
            )
        }


        private fun getCurrentName(): String {
            val morning = getLevelDate(9, 0, 0)
            val noon = getLevelDate(11, 15, 0)
            val noonEnd = getLevelDate(13, 30, 0)
//            val afternoon = getLevelDate(17, 45, 0)
            val currentData = Date()
            val name: String

            if (currentData >= morning && currentData < noon) {
                name = "早餐"
            } else if (currentData >= noon && currentData < noonEnd) {
                name = "午餐"
            } else {
                name = "晚餐"
            }
            return name
        }

        /**
         * 获取合适时机填充字段
         */
        private fun getCurrentTimeStr(): String {
            val level1 = getLevelDate(11, 15, 0)
            val level2 = getLevelDate(11, 45, 0)
            val level3 = getLevelDate(12, 15, 0)
            val level4 = getLevelDate(12, 20, 0)
            val level5 = getLevelDate(12, 50, 0)
            val level6 = getLevelDate(13, 30, 0)

            val level7 = getLevelDate(17, 45, 0)
            val level8 = getLevelDate(18, 30, 0)
            val level9 = getLevelDate(19, 15, 0)


            val currentData = Date()
            val timeStr: String
            if (currentData >= level1 && currentData < level2) {
                timeStr = " ${getCardTimeStr(level1)} - ${getCardTimeStr(level2)}"

            } else if (currentData >= level2 && currentData < level3) {
                timeStr = " ${getCardTimeStr(level2)} - ${getCardTimeStr(level3)}"

            } else if (currentData >= level3 && currentData < level4) {
                timeStr = " ${getCardTimeStr(level3)} - ${getCardTimeStr(level4)}"

            } else if (currentData >= level4 && currentData < level5) {
                timeStr = " ${getCardTimeStr(level4)} - ${getCardTimeStr(level5)}"
            } else if (currentData >= level5 && currentData < level6) {
                timeStr = " ${getCardTimeStr(level5)} - ${getCardTimeStr(level6)}"
            } else if (currentData >= level7 && currentData < level8) {
                timeStr = " ${getCardTimeStr(level7)} - ${getCardTimeStr(level8)}"

            } else if (currentData >= level8 && currentData < level9) {
                timeStr = " ${getCardTimeStr(level8)} - ${getCardTimeStr(level9)}"
            } else {
                timeStr =
                    " ${getCardTimeStr(currentData)} - ${getCardTimeStr(Date(System.currentTimeMillis() + 1000 * 60 * 30))}"
            }

            return timeStr
        }


        /**
         * 获取指定时间的Date
         */
        private fun getLevelDate(hour: Int, minute: Int, sec: Int): Date {
            return Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, sec)
            }.time
        }

        /**
         * 获取日期字符串
         */
        private fun getCurrentDateStr(): String {
            val sdf = SimpleDateFormat("yyyy.MM.dd")
            return sdf.format(Date())
        }

        /**
         * 获取时间段字符串
         */
        private fun getCardTimeStr(date: Date): String {
            val sdf = SimpleDateFormat("HH:mm")
            return sdf.format(date)
        }

    }
}