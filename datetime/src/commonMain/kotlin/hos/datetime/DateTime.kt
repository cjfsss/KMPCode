package hos.datetime

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats

/**
 * <p>Title: DateTimePlus </p>
 * <p>Description:  </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-05-30 16:56
 * @version : 1.0
 */
class DateTime private constructor(
    localDateTime: LocalDateTime,
    val timeZone: TimeZone = TimeZone.currentSystemDefault()
) {

    var dateTime: LocalDateTime = localDateTime

    public constructor(
        year: Int,
        month: Month,
        dayOfMonth: Int,
        hour: Int,
        minute: Int,
        second: Int = 0,
        nanosecond: Int = 0,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ) : this(
        LocalDateTime(
            year,
            month,
            dayOfMonth,
            hour,
            minute,
            second,
            nanosecond
        ), timeZone
    )

    public constructor(
        millis: Long = 0,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ) : this(
        LocalDateTime.now(millis, timeZone), timeZone
    )

    /**
     * 设置年月日
     * @param year 年份
     * @param month 月份，默认为1月
     * @param dayOfMonth 日期，默认为1日
     * @return DateTime对象，设置了指定的年月日
     */
    fun setYearMonthDay(year: Int, month: Int = 1, dayOfMonth: Int = 1): DateTime {
        return set(year, DateTimeUnit.YEAR).set(month, DateTimeUnit.MONTH)
            .set(dayOfMonth, DateTimeUnit.DAY)
    }

    /**
     * 设置时分秒
     * @param hour 小时
     * @param minute 分钟，默认为0分
     * @param second 秒，默认为0秒
     * @return DateTime对象，设置了指定的时分秒
     */
    fun setHourMinuteSecond(hour: Int, minute: Int = 0, second: Int = 0): DateTime {
        return set(hour, DateTimeUnit.HOUR).set(minute, DateTimeUnit.MINUTE)
            .set(second, DateTimeUnit.SECOND)
    }

    /**
     * 设置指定时间单位的值
     * @param value 时间值
     * @param unit 时间单位
     * @return 当前DateTime对象，用于链式调用
     */
    fun set(value: Int, unit: DateTimeUnit): DateTime {
        dateTime = dateTime.set(value, unit)
        return this
    }

    /**
     * 增加指定时间单位的值
     * @param value 时间值
     * @param unit 时间单位
     * @return 当前DateTime对象，用于链式调用
     */
    fun add(
        value: Int,
        unit: DateTimeUnit,
    ): DateTime {
        dateTime = dateTime.add(value, unit)
        return this
    }

    /**
     * 减少指定时间单位的值
     * @param value 时间值
     * @param unit 时间单位
     * @return 当前DateTime对象，用于链式调用
     */
    fun subtract(
        value: Int,
        unit: DateTimeUnit,
    ): DateTime {
        dateTime = dateTime.subtract(value, unit)
        return this
    }

    /**
     * 转换为Epoch毫秒数
     * @param timeZone 时区，默认使用当前对象的时区
     * @return Epoch毫秒数
     */
    fun toEpochMilliseconds(timeZone: TimeZone = this.timeZone): Long {
        return dateTime.toEpochMilliseconds(timeZone)
    }

    fun toEpochSeconds(timeZone: TimeZone = this.timeZone): Long {
        return dateTime.toEpochSeconds(timeZone)
    }

    fun toNanosecondsOfSecond(timeZone: TimeZone = this.timeZone): Int {
        return dateTime.toNanosecondsOfSecond(timeZone)
    }

    /**
     * 获取年份
     * @return 年份
     */
    fun year(): Int {
        "".isEmpty()
        return dateTime.year
    }

    /**
     * 获取月份编号
     * @return 月份编号
     */
    fun monthNumber(): Int {
        return dateTime.monthNumber
    }

    /**
     * 获取月份枚举
     * @return 月份枚举
     */
    fun month(): Month {
        return dateTime.month
    }

    /**
     * 获取日期
     * @return 日期
     */
    fun day(): Int {
        return dateTime.dayOfMonth
    }

    /**
     * 获取星期几
     * @return 星期几
     */
    fun dayOfWeek(): DayOfWeek {
        return dateTime.dayOfWeek
    }

    /**
     * 获取一年中的第几天
     * @return 一年中的第几天
     */
    fun dayOfYear(): Int {
        return dateTime.dayOfYear
    }

    /**
     * 获取小时
     * @return 小时
     */
    fun hour(): Int {
        return dateTime.hour
    }

    /**
     * 获取分钟
     * @return 分钟
     */
    fun minute(): Int {
        return dateTime.minute
    }

    /**
     * 获取秒
     * @return 秒
     */
    fun second(): Int {
        return dateTime.second
    }

    /**
     * 计算当月的星期一数量
     * @return 星期一数量
     */
    fun countMondaysInMonth(): Int {
        return dateTime.countMondaysInMonth()
    }

    /**
     * 计算当月的周数
     * @return 周数
     */
    fun countWeeksInMonth(): Int {
        return dateTime.countWeeksInMonth()
    }

    /**
     * 计算当月以星期一开始的周数
     * @return 以星期一开始的周数
     */
    fun countWeeksMondayInMonth(): Int {
        return dateTime.countWeeksMondayInMonth()
    }

    /**
     * 获取每个月的第一个星期日
     *
     * 此函数的目的是遍历每个月的第一天，并创建一个包含这些日期的列表
     * 它主要用于需要按月跟踪特定日期的应用程序，例如日历应用程序或提醒系统
     *
     * @return 返回一个[MutableList]类型的[DateTime]对象列表，每个对象代表每个月的第一个星期日
     */
    fun getWeekFirstDayInMonth(): MutableList<DateTime> {
        // 创建一个可变列表以存储每个月的第一个星期日
        val mondays = mutableListOf<DateTime>()
        // 获取当前日期时间对象的每个月的第一个星期日列表
        val weekFirstDayInMonth = dateTime.getWeekFirstDayInMonth()
        // 遍历每个月的第一个星期日
        weekFirstDayInMonth.forEach {
            // 将每个月的第一个星期日添加到列表中
            mondays.add(DateTime(it))
        }
        // 返回包含每个月的第一个星期日的列表
        return mondays
    }

    /**
     * 设置为当年的开始时间
     * @return 当前DateTime对象，用于链式调用
     */
    fun startYear(): DateTime {
        dateTime = dateTime.startYear()
        return this
    }

    /**
     * 设置为当年的结束时间
     * @return 当前DateTime对象，用于链式调用
     */
    fun endYear(): DateTime {
        dateTime = dateTime.endYear()
        return this
    }

    /**
     * 设置为当月的开始时间
     * @return 当前DateTime对象，用于链式调用
     */
    fun startMonth(): DateTime {
        dateTime = dateTime.startMonth()
        return this
    }

    /**
     * 设置为当月的结束时间
     * @return 当前DateTime对象，用于链式调用
     */
    fun endMonth(): DateTime {
        dateTime = dateTime.endMonth()
        return this
    }

    /**
     * 设置为当周的开始时间
     * @return 当前DateTime对象，用于链式调用
     */
    fun startWeek(): DateTime {
        dateTime = dateTime.startWeek()
        return this
    }

    /**
     * 设置为当周的结束时间
     * @return 当前DateTime对象，用于链式调用
     */
    fun endWeek(): DateTime {
        dateTime = dateTime.endWeek()
        return this
    }

    /**
     * 设置为当天的开始时间
     * @return 当前DateTime对象，用于链式调用
     */
    fun startDay(): DateTime {
        dateTime = dateTime.startDay()
        return this
    }

    /**
     * 设置为当天的结束时间
     * @return 当前DateTime对象，用于链式调用
     */
    fun endDay(): DateTime {
        dateTime = dateTime.endDay()
        return this
    }

    /**
     * 获取昨天的时间
     * @return 昨天的时间
     */
    fun yesterday(): DateTime {
        return subtract(1, DateTimeUnit.DAY)
    }

    /**
     * 获取前天的时间
     * @return 前天的时间
     */
    fun yesterdayBefore(): DateTime {
        return subtract(2, DateTimeUnit.DAY)
    }

    /**
     * 获取明天的时间
     * @return 明天的时间
     */
    fun tomorrow(): DateTime {
        return add(1, DateTimeUnit.DAY)
    }

    /**
     * 获取后天的时间
     * @return 后天的时间
     */
    fun tomorrowAfter(): DateTime {
        return add(2, DateTimeUnit.DAY)
    }

    /**
     * 减少1小时
     * @return 减少1小时后的时间
     */
    fun subtractHour1(): DateTime {
        return subtract(1, DateTimeUnit.HOUR)
    }

    /**
     * 减少30分钟
     * @return 减少30分钟后的时间
     */
    fun subtractMinute30(): DateTime {
        return subtract(30, DateTimeUnit.MINUTE)
    }

    /**
     * 减少30天
     * @return 减少30天后的时间
     */
    fun subtractDay30(): DateTime {
        return subtract(30, DateTimeUnit.DAY)
    }

    /**
     * 减少7天
     * @return 减少7天后的时间
     */
    fun subtractDay7(): DateTime {
        return subtract(7, DateTimeUnit.DAY)
    }

    /**
     * 判断两个时间是否相同
     * @param dateTime 另一个时间
     * @return 如果两个时间相同则返回true，否则返回false
     */
    fun isSame(dateTime: DateTime): Boolean {
        return this.toEpochMilliseconds() == dateTime.toEpochMilliseconds()
    }

    /**
     * 判断当前时间是否在指定时间之前
     * @param end 指定时间
     * @return 如果当前时间在指定时间之前则返回true，否则返回false
     */
    fun isBefore(end: DateTime): Boolean {
        // 将当前时间和指定时间转换为毫秒，进行比较
        return this.toEpochMilliseconds() < end.toEpochMilliseconds()
    }

    /**
     * 判断当前时间是否在指定时间之后
     * @param start 指定时间
     * @return 如果当前时间在指定时间之后则返回true，否则返回false
     */
    fun isAfter(start: DateTime): Boolean {
        return this.toEpochMilliseconds() > start.toEpochMilliseconds()
    }

    /**
     * 获取当月的天数
     * @return 当月的天数
     */
    fun monthSize(): Int {
        return endMonth().day()
    }

    /**
     * 格式化时间为字符串
     * @param pattern 时间格式，默认为完整格式
     * @return 格式化后的时间字符串
     */
    @FormatStringsInDatetimeFormats
    fun format(pattern: DateTimeFormats = DateTimeFormats.FULL): String {
        return dateTime.format(pattern)
    }

    fun copyDateTime(): DateTime {
        return DateTime(dateTime.toEpochMilliseconds(timeZone), timeZone)
    }


    companion object {
        /**
         * 使用给定的毫秒数和时区创建一个DateTime对象，并执行提供的内容函数
         *
         * @param millis 指定的毫秒数，默认为0
         * @param timeZone 指定时区，默认为系统默认时区
         * @return 内容函数的返回值
         */
        fun now(
            millis: Long = 0,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ): DateTime {
            return DateTime(millis, timeZone)
        }

        /**
         * 使用给定的日期和时间参数创建一个DateTime对象，并执行提供的内容函数
         *
         * @param year 年份
         * @param month 月份
         * @param dayOfMonth 月份中的天
         * @param hour 小时
         * @param minute 分钟
         * @param second 秒，默认为0
         * @param nanosecond 纳秒，默认为0
         * @param timeZone 指定时区，默认为系统默认时区
         * @return 内容函数的返回值
         */
        fun now(
            year: Int,
            month: Month,
            dayOfMonth: Int,
            hour: Int,
            minute: Int,
            second: Int = 0,
            nanosecond: Int = 0,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ): DateTime {
            return DateTime(
                year, month, dayOfMonth,
                hour, minute, second, nanosecond, timeZone
            )
        }

        /**
         * 使用给定的时间字符串和格式解析一个DateTime对象，并执行提供的内容函数
         *
         * @param time 时间字符串
         * @param pattern 时间格式，如果未提供，将根据时间字符串自动确定
         * @param value 用于解析的附加字符串，默认为空
         * @return 内容函数的返回值
         * @throws IllegalArgumentException 如果提供的格式为null
         */
        fun parse(
            time: String,
            pattern: DateTimeFormats? = DateTimeFormats.getFormatString(time),
            value: String = "",
        ): DateTime {
            if (pattern == null) {
                throw IllegalArgumentException("pattern is not null")
            }
            return DateTime(DateTimeFormats.parseLocalDateTime(time, pattern, value))
        }

        /**
         * 将时间字符串转换为毫秒时间戳。
         * 将给定时间字符串解析为LocalDateTime对象，并将其转换为自1970年1月1日以来的毫秒数。
         * 如果转换过程中发生异常，则返回-1。
         *
         * @param time 代表日期和时间的字符串。
         * @param pattern 用于解析时间字符串的格式模式。
         * @param value 保留参数，目前未使用。
         * @return 自1970年1月1日以来的毫秒数，如果发生异常则为-1。
         */
        fun parse2Millis(
            time: String,
            pattern: DateTimeFormats? = DateTimeFormats.getFormatString(time),
            value: String = ""
        ): Long {
            return parse(time, pattern, value).toEpochMilliseconds()
        }

        /**
         * 时间格式转换。
         * 将给定时间字符串从一种格式转换为另一种格式。
         * 如果未提供源格式，将尝试根据时间字符串自动检测。
         * 目标格式默认为完整的日期和时间格式。
         *
         * @param time 代表日期和时间的字符串。
         * @param from 用于解析时间字符串的源格式模式。
         * @param to 目标格式模式，默认为完整的日期和时间格式。
         * @return 转换后的日期和时间字符串，如果发生异常则为空字符串。
         */
        @OptIn(FormatStringsInDatetimeFormats::class)
        fun parseConvert(
            time: String,
            from: DateTimeFormats? = DateTimeFormats.getFormatString(time),
            to: DateTimeFormats = DateTimeFormats.FULL
        ): String {
            return parse(time, from).format(to)
        }

        /**
         * 获取当前时间的毫秒数
         *
         * @param timeZone 指定时区，默认为系统默认时区
         * @return 当前时间的毫秒数
         */
        fun getNowMills(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
            return now(timeZone = timeZone).toEpochMilliseconds()
        }

        /**
         * 根据指定的时间毫秒数和时区获取当前时间内容
         *
         * @param millis 时间毫秒数，默认为0，表示当前时间
         * @param timeZone 时区，默认为系统当前时区
         * @param content 时间日期的处理逻辑，返回任意类型T
         * @return 返回处理后的结果T
         */
        fun <T> getContent(
            millis: Long = 0,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
            content: DateTime.() -> T
        ): T {
            return content.invoke(now(millis, timeZone))
        }

    }

}
