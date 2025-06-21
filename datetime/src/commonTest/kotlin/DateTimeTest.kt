import hos.datetime.DateTime
import hos.datetime.DateTimeFormats
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlin.test.Test

/**
 * <p>Title: DateTimeTest </p>
 * <p>Description: 用于测试日期时间处理类DateTime的各个功能 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-14 10:00
 * @version : 1.0
 */
@OptIn(FormatStringsInDatetimeFormats::class)
class DateTimeTest {

    @Test
    fun main() {
        // 测试构造函数
        println("=== 测试构造函数 ===")
        testConstructors()

        // 测试基本设置操作
        println("\n=== 测试基本设置操作 ===")
        testSetOperations()

        // 测试时间增减操作
        println("\n=== 测试时间增减操作 ===")
        testAddSubtract()

        // 测试时间转换为毫秒/秒
        println("\n=== 测试时间转换为毫秒/秒 ===")
        testToEpoch()

        // 测试获取年月日时分秒
        println("\n=== 测试获取年月日时分秒 ===")
        testGetComponents()

        // 测试星期相关操作
        println("\n=== 测试星期相关操作 ===")
        testDayOfWeek()

        // 测试月份相关操作
        println("\n=== 测试月份相关操作 ===")
        testMonthOperations()

        // 测试周边界操作
        println("\n=== 测试周边界操作 ===")
        testWeekBoundaries()

        // 测试月边界操作
        println("\n=== 测试月边界操作 ===")
        testMonthBoundaries()

        // 测试特殊日期获取
        println("\n=== 测试特殊日期获取 ===")
        testSpecialDates()

        // 测试比较操作
        println("\n=== 测试比较操作 ===")
        testComparison()

        // 测试格式化
        println("\n=== 测试格式化 ===")
        testFormat()

        // 测试解析
        println("\n=== 测试解析 ===")
        testParse()

        // 测试生成当前时间字符串
        println("\n=== 测试生成当前时间字符串 ===")
        testGenDatetime()
    }

    private fun testConstructors() {
        // 测试默认构造函数
        val now = DateTime.now()
        println("DateTime.now() 创建的时间: ${now.format()}")

        // 测试带参数的构造函数
        val specificDate = DateTime(2023, Month.JUNE, 15, 12, 30, 45)
        println("DateTime(2023, Month.JUNE, 15, 12, 30, 45) 创建的时间: ${specificDate.format()}")

        // 测试从毫秒创建
        val fromMillis = DateTime(now.toEpochMilliseconds())
        println("DateTime(millis) 创建的时间: ${fromMillis.format()}")
        println("两个时间是否相同: ${now.isSame(fromMillis)}")
    }

    private fun testSetOperations() {
        val dt = DateTime.now()

        // 测试 setYearMonthDay
        val newDate = dt.copyDateTime().setYearMonthDay(2025, 12, 31)
        println("设置年月日: ${newDate.format()}")
        println("期望年份: 2025, 实际年份: ${newDate.year()}")
        println("期望月份: 12, 实际月份: ${newDate.monthNumber()}")
        println("期望日期: 31, 实际日期: ${newDate.day()}")

        // 测试 setHourMinuteSecond
        val newTime = dt.copyDateTime().setHourMinuteSecond(23, 59, 59)
        println("设置时分秒: ${newTime.format()}")
        println("期望小时: 23, 实际小时: ${newTime.hour()}")
        println("期望分钟: 59, 实际分钟: ${newTime.minute()}")
        println("期望秒: 59, 实际秒: ${newTime.second()}")

        // 测试 set 方法
        val newY = dt.copyDateTime().set(2024, DateTimeUnit.YEAR)
        println("设置年份: ${newY.format()}")
        println("期望年份: 2024, 实际年份: ${newY.year()}")
    }

    private fun testAddSubtract() {
        val dt = DateTime.now()

        // 测试 add
        val tomorrow = dt.copyDateTime().add(1, DateTimeUnit.DAY)
        println("明天: ${tomorrow.format()}")

        val nextHour = dt.copyDateTime().add(1, DateTimeUnit.HOUR)
        println("下一小时: ${nextHour.format()}")

        // 测试 subtract
        val yesterday = dt.copyDateTime().subtract(1, DateTimeUnit.DAY)
        println("昨天: ${yesterday.format()}")

        val lastHour = dt.copyDateTime().subtract(1, DateTimeUnit.HOUR)
        println("上一小时: ${lastHour.format()}")
    }

    private fun testToEpoch() {
        val dt = DateTime.now()

        val millis = dt.toEpochMilliseconds()
        println("当前时间的毫秒数: $millis")

        val seconds = dt.toEpochSeconds()
        println("当前时间的秒数: $seconds")

        // 验证从毫秒创建的时间是否相同
        val fromMillis = DateTime(millis)
        println("从毫秒创建的时间是否相同: ${dt.isSame(fromMillis)}")
    }

    private fun testGetComponents() {
        val dt = DateTime.now()

        println("当前年份: ${dt.year()}")
        println("当前月份编号: ${dt.monthNumber()}")
        println("当前月份: ${dt.month()}")
        println("当前日期: ${dt.day()}")
        println("当前小时: ${dt.hour()}")
        println("当前分钟: ${dt.minute()}")
        println("当前秒: ${dt.second()}")
    }

    private fun testDayOfWeek() {
        val dt = DateTime.now()

        println("今天是星期几: ${dt.dayOfWeek()}")
        println("今天是一年中的第几天: ${dt.dayOfYear()}")
    }

    private fun testMonthOperations() {
        val dt = DateTime.now()

        println("当月天数: ${dt.monthSize()}")
        println("当月第一个星期一数量: ${dt.countMondaysInMonth()}")
        println("当月周数: ${dt.countWeeksInMonth()}")
        println("当月以星期一开始的周数: ${dt.countWeeksMondayInMonth()}")

        val firstDays = dt.getWeekFirstDayInMonth()
        println("当月每周的第一个星期日:")
        firstDays.forEach { day ->
            println("  ${day.format()}")
        }
    }

    private fun testWeekBoundaries() {
        val dt = DateTime.now()

        val startOfWeek = dt.copyDateTime().startWeek()
        println("本周开始: ${startOfWeek.format()}")

        val endOfWeek = dt.copyDateTime().endWeek()
        println("本周结束: ${endOfWeek.format()}")
    }

    private fun testMonthBoundaries() {
        val dt = DateTime.now()

        val startOfMonth = dt.copyDateTime().startMonth()
        println("本月开始: ${startOfMonth.format()}")

        val endOfMonth = dt.copyDateTime().endMonth()
        println("本月结束: ${endOfMonth.format()}")
    }

    private fun testSpecialDates() {
        val dt = DateTime.now()

        val tomorrow = dt.tomorrow()
        println("明天: ${tomorrow.format()}")

        val yesterday = dt.yesterday()
        println("昨天: ${yesterday.format()}")

        val yesterdayBefore = dt.yesterdayBefore()
        println("前天: ${yesterdayBefore.format()}")

        val tomorrowAfter = dt.tomorrowAfter()
        println("后天: ${tomorrowAfter.format()}")

        val hourAgo = dt.subtractHour1()
        println("一小时前: ${hourAgo.format()}")

        val minuteAgo = dt.subtractMinute30()
        println("30分钟前: ${minuteAgo.format()}")
    }

    private fun testComparison() {
        val dt1 = DateTime.now()
        val dt2 = dt1.copyDateTime().add(1, DateTimeUnit.MINUTE)

        println("dt1: ${dt1.format()}")
        println("dt2: ${dt2.format()}")

        println("dt1.isSame(dt2): ${dt1.isSame(dt2)} (期望: false)")
        println("dt1.isBefore(dt2): ${dt1.isBefore(dt2)} (期望: true)")
        println("dt2.isAfter(dt1): ${dt2.isAfter(dt1)} (期望: true)")
    }

    private fun testFormat() {
        val dt = DateTime.now()

        println("默认格式: ${dt.format()}")
        println("完整格式: ${dt.format(DateTimeFormats.FULL)}")
        println("中文完整格式: ${dt.format(DateTimeFormats.ZH_FULL)}")
        println("iOS完整格式: ${dt.format(DateTimeFormats.IOS_FULL)}")
    }

    private fun testParse() {
        // 测试不同格式的解析
        val testDates = listOf(
            "2023-06-15 12:30:45" to DateTimeFormats.FULL,
            "2023/06/15 12:30:45" to DateTimeFormats.IOS_FULL,
            "2023年06月15日 12时30分45秒" to DateTimeFormats.ZH_FULL,
            "20230615123045" to DateTimeFormats.FULL_S
        )

        testDates.forEach { (dateStr, format) ->
            try {
                val dt = DateTime.parse(dateStr, format)
                println("解析 \"$dateStr\" 成功: ${dt.format()}")

                // 验证转换回字符串是否一致
                val converted = DateTime.parseConvert(dateStr, format, format)
                println("转换回字符串: $converted")
                println("原始和转换后的字符串是否相同: $dateStr == $converted")
            } catch (e: Exception) {
                e.printStackTrace()
                println("解析 \"$dateStr\" 失败")
            }
        }

        // 测试自动检测格式解析
        val autoDetectDate = "2023-06-15 12:30:45"
        try {
            val dt = DateTime.parse(autoDetectDate)
            println("自动检测格式解析 \"$autoDetectDate\" 成功: ${dt.format()}")
        } catch (e: Exception) {
            e.printStackTrace()
            println("自动检测格式解析 \"$autoDetectDate\" 失败")
        }

        // 测试失败情况
        try {
            val invalidDate = "invalid_date"
            DateTime.parse(invalidDate)
            println("无效日期解析应该失败但未失败")
        } catch (e: Exception) {
            println("解析无效日期成功抛出异常: \"${e.message}\"")
        }
    }

    private fun testGenDatetime() {
        val genDatetime = DateTimeFormats.genDatetime()
        println("生成的日期时间字符串: $genDatetime")
        println("期望格式: yyyyMMddHHmmss")
        println("匹配正则表达式: \\d{14} (14位数字)")
        println("验证结果: ${Regex("\\d{14}").matches(genDatetime)}")
    }
}
