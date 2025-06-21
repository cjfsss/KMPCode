package hos.datetime

import kotlinx.datetime.Month

/**
 * <p>Title: MonthPlus </p>
 * <p>Description:  </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-03 8:51
 * @version : 1.0
 */

/**
 * 获取指定年份中本月的天数
 *
 * 此函数根据月份和是否为闰年计算该月的天数
 * 闰年规则仅适用于二月，其他月份的天数是固定的
 *
 * @param year 指定的年份
 * @return 月份的天数
 */
fun Month.daysInMonth(year: Int): Int {
    return when (this) {
        Month.JANUARY -> 31
        Month.FEBRUARY -> if (year.isLeapYear()) 29 else 28
        Month.MARCH -> 31
        Month.APRIL -> 30
        Month.MAY -> 31
        Month.JUNE -> 30
        Month.JULY -> 31
        Month.AUGUST -> 31
        Month.SEPTEMBER -> 30
        Month.OCTOBER -> 31
        Month.NOVEMBER -> 30
        Month.DECEMBER -> 31
        else -> 31
    }
}

/**
 * 计算指定年份中本月的毫秒数
 *
 * 此函数将本月的天数转换为毫秒数，用于时间计算或转换
 *
 * @param year 指定的年份
 * @return 月份的毫秒数
 */
fun Month.milliseconds(year: Int): Long {
    return daysInMonth(year) * 24 * 60 * 60 * 1000L
}

/**
 * 计算指定年份中本月的微秒数
 *
 * 此函数将本月的天数转换为微秒数，用于更精确的时间计算或转换
 *
 * @param year 指定的年份
 * @return 月份的微秒数
 */
fun Month.microsecond(year: Int): Long {
    return daysInMonth(year) * 24 * 60 * 60 * 1000L * 1000L
}

/**
 * 计算指定年份中本月的纳秒数
 *
 * 此函数将本月的天数转换为纳秒数，用于极高精度的时间计算或转换
 *
 * @param year 指定的年份
 * @return 月份的纳秒数
 */
fun Month.nanosecond(year: Int): Long {
    return daysInMonth(year) * 24 * 60 * 60 * 1000L * 1000L * 1000L
}


/**
 * 判断指定的年份是否为闰年
 *
 * @return 如果是闰年返回true，否则返回false
 */
fun Int.isLeapYear(): Boolean {
    return this % 4 == 0 && (this % 100 != 0 || this % 400 == 0)
}