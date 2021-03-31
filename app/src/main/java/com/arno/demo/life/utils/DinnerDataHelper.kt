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
                getFixCurrentTimeStr(false),
                getFixCurrentTimeStr(true)
            )
        }


        private fun getCurrentName(): String {
            val morning = getLevelDate(9, 0, 0)
            val noon = getLevelDate(11, 15, 0)
            val noonEnd = getLevelDate(13, 30, 0)
//            val afternoon = getLevelDate(17, 45, 0)
            val currentData = Date()

            return if (currentData >= morning && currentData < noon) {
                "早餐"
            } else if (currentData >= noon && currentData < noonEnd) {
                "午餐"
            } else {
                "晚餐"
            }
        }

        /**
         * 自动获取合适时间
         * @param isDinner 是否是晚餐
         */
        private fun getFixCurrentTimeStr(isDinner: Boolean): String {
            val level = getCurrentLevel()
            if (isDinner) {
                return if (level in 7..8) {
                    //命中晚餐 正常逻辑
                    getCurrentTimeStr()
                } else {
                    //自动填充一个晚餐
                    getTimeStrByLevel(7 + Random().nextInt(2))
                }
            } else {
                return if (level in 1..5) {
                    //命中午餐
                    getCurrentTimeStr()
                } else {
                    //自动填充一个午餐
                    getTimeStrByLevel(Random().nextInt(6))
                }
            }


        }

        private fun getCurrentLevel(): Int {
            val level1 = getLevel(1)
            val level2 = getLevel(2)
            val level3 = getLevel(3)
            val level4 = getLevel(4)
            val level5 = getLevel(5)
            val level6 = getLevel(6)
            val level7 = getLevel(7)
            val level8 = getLevel(8)
            val level9 = getLevel(9)
            val currentData = Date()
            var level = 0
            if (currentData >= level1 && currentData < level2) {
                level = 1
            } else if (currentData >= level2 && currentData < level3) {
                level = 2
            } else if (currentData >= level3 && currentData < level4) {
                level = 3
            } else if (currentData >= level4 && currentData < level5) {
                level = 4
            } else if (currentData >= level5 && currentData < level6) {
                level = 5
            } else if (currentData >= level7 && currentData < level8) {
                level = 7
            } else if (currentData >= level8 && currentData < level9) {
                level = 8
            }
            return level
        }

        /**
         * 获取合适时机填充字段
         */
        private fun getCurrentTimeStr(): String {
            val currentData = Date()
            return when (getCurrentLevel()) {
                1 -> getTimeStrByLevel(1)
                2 -> getTimeStrByLevel(2)
                3 -> getTimeStrByLevel(3)
                4 -> getTimeStrByLevel(4)
                5 -> getTimeStrByLevel(5)
                7 -> getTimeStrByLevel(7)
                8 -> getTimeStrByLevel(8)
                else -> "${getCardTimeStr(currentData)} - ${getCardTimeStr(Date(System.currentTimeMillis() + 1000 * 60 * 30))}"
            }
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

        /**
         * 根据level获取字符串
         */
        fun getTimeStrByLevel(level: Int): String {
            return when (level) {
                1 -> "${getCardTimeStr(getLevel(1))} - ${getCardTimeStr(getLevel(2))}"
                2 -> "${getCardTimeStr(getLevel(2))} - ${getCardTimeStr(getLevel(3))}"
                3 -> "${getCardTimeStr(getLevel(3))} - ${getCardTimeStr(getLevel(4))}"
                4 -> "${getCardTimeStr(getLevel(4))} - ${getCardTimeStr(getLevel(5))}"
                5 -> "${getCardTimeStr(getLevel(5))} - ${getCardTimeStr(getLevel(6))}"
                7 -> "${getCardTimeStr(getLevel(7))} - ${getCardTimeStr(getLevel(8))}"
                8 -> "${getCardTimeStr(getLevel(8))} - ${getCardTimeStr(getLevel(9))}"
                else -> {
                    "${getCardTimeStr(Date())} - ${getCardTimeStr(Date(System.currentTimeMillis() + 1000 * 60 * 30))}"
                }
            }
        }

        fun getLevel(level: Int): Date {
            return when (level) {
                1 -> getLevelDate(11, 15, 0)
                2 -> getLevelDate(11, 45, 0)
                3 -> getLevelDate(12, 15, 0)
                4 -> getLevelDate(12, 20, 0)
                5 -> getLevelDate(12, 50, 0)
                6 -> getLevelDate(13, 30, 0)
                7 -> getLevelDate(17, 45, 0)
                8 -> getLevelDate(18, 30, 0)
                else -> getLevelDate(19, 15, 0)
            }
        }

    }
}