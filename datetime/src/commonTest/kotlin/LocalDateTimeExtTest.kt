import hos.datetime.DateTimeFormats
import hos.datetime.add
import hos.datetime.countMondaysInMonth
import hos.datetime.countWeeksInMonth
import hos.datetime.countWeeksMondayInMonth
import hos.datetime.day
import hos.datetime.endDay
import hos.datetime.endMonth
import hos.datetime.endWeek
import hos.datetime.endYear
import hos.datetime.format
import hos.datetime.getWeekFirstDayInMonth
import hos.datetime.monthSize
import hos.datetime.now
import hos.datetime.set
import hos.datetime.startDay
import hos.datetime.startMonth
import hos.datetime.startWeek
import hos.datetime.startYear
import hos.datetime.subtract
import hos.datetime.subtractDay30
import hos.datetime.subtractDay7
import hos.datetime.subtractHour1
import hos.datetime.subtractMinute30
import hos.datetime.toEpochMilliseconds
import hos.datetime.tomorrow
import hos.datetime.yesterday
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * <p>Title: LocalDateTimeExtTest </p>
 * <p>Description: 用于测试LocalDateTime扩展函数的各类日期操作</p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-14 10:00
 * @version : 1.0
 */
@OptIn(FormatStringsInDatetimeFormats::class)
class LocalDateTimeExtTest {

    // 固定基准时间以确保测试可重复（2023-06-15 12:30:45）
    private val baseTime = LocalDateTime(2023, Month.JUNE, 15, 12, 30, 45)

    @Test
    fun main() {
        println("=== 开始测试 LocalDateTime 扩展函数 ===")
        testNowFunctions()
        testSetAndGetters()
        testAddSubtract()
        testTimeBoundaries()
        testSpecialDates()
        testMonthStatistics()
        testFormat()
        testComparison()
        println("✅ 所有测试通过！")
    }

    private fun testNowFunctions() {
        println("\n=== 测试 now 函数 ===")
        val now = LocalDateTime.now()
        val nowFromMillis = LocalDateTime.now(now.toEpochMilliseconds())
        assertEquals(now.year, nowFromMillis.year)
        assertEquals(now.month, nowFromMillis.month)
        println("now(): $now")
        println("now(millis): $nowFromMillis")
    }

    private fun testSetAndGetters() {
        println("\n=== 测试 set 方法和 get 方法 ===")

        val dtYear = baseTime.set(2025, DateTimeUnit.YEAR)
        assertEquals(2025, dtYear.year)
        println("set year: $dtYear")

        val dtMonth = baseTime.set(12, DateTimeUnit.MONTH)
        assertEquals(Month.DECEMBER, dtMonth.month)
        println("set month: $dtMonth")

        val dtDay = baseTime.set(1, DateTimeUnit.DAY)
        assertEquals(1, dtDay.dayOfMonth)
        println("set day: $dtDay")

        val dtHour = baseTime.set(9, DateTimeUnit.HOUR)
        assertEquals(9, dtHour.hour)
        println("set hour: $dtHour")

        val dtMinute = baseTime.set(15, DateTimeUnit.MINUTE)
        assertEquals(15, dtMinute.minute)
        println("set minute: $dtMinute")

        val dtSecond = baseTime.set(30, DateTimeUnit.SECOND)
        assertEquals(30, dtSecond.second)
        println("set second: $dtSecond")

        assertEquals(baseTime.day(), baseTime.dayOfMonth)
    }

    private fun testAddSubtract() {
        println("\n=== 测试 add / subtract ===")

        val tomorrow = baseTime.add(1, DateTimeUnit.DAY)
        assertEquals(16, tomorrow.dayOfMonth)
        println("add DAY: $tomorrow")

        val nextMonth = baseTime.add(1, DateTimeUnit.MONTH)
        assertEquals(Month.JULY, nextMonth.month)
        println("add MONTH: $nextMonth")
        val yesterday = baseTime.subtract(1, DateTimeUnit.DAY)
        assertEquals(14, yesterday.dayOfMonth)
        println("subtract DAY: $yesterday")

        val lastMonth = baseTime.subtract(1, DateTimeUnit.MONTH)
        assertEquals(Month.MAY, lastMonth.month)
        println("subtract MONTH: $lastMonth")

        val nextHour = baseTime.add(1, DateTimeUnit.HOUR)
        assertEquals(13, nextHour.hour)
        println("add HOUR: $nextHour")

        val lastMin = baseTime.subtract(15, DateTimeUnit.MINUTE)
        assertEquals(15, lastMin.minute)
        println("subtract MINUTE: $lastMin")
    }

    private fun testTimeBoundaries() {
        println("\n=== 测试 start/end 边界方法 ===")

        val startOfDay = baseTime.startDay()
        assertEquals(LocalTime(0, 0, 0), startOfDay.time)
        println("startDay: $startOfDay")

        val endOfDay = baseTime.endDay()
        assertEquals(LocalTime(23, 59, 59), endOfDay.time)
        println("endDay: $endOfDay")

        val startOfMonth = baseTime.startMonth()
        assertEquals(LocalDate(2023, Month.JUNE, 1), startOfMonth.date)
        println("startMonth: $startOfMonth")

        val endOfMonth = baseTime.endMonth()
        assertEquals(LocalDate(2023, Month.JUNE, 30), endOfMonth.date)
        println("endMonth: $endOfMonth")

        val startOfYear = baseTime.startYear()
        assertEquals(LocalDate(2023, Month.JANUARY, 1), startOfYear.date)
        println("startYear: $startOfYear")

        val endOfYear = baseTime.endYear()
        assertEquals(LocalDate(2023, Month.DECEMBER, 31), endOfYear.date)
        println("endYear: $endOfYear")

        val startOfWeek = baseTime.startWeek()
        val expectedStartOfWeek = LocalDate(2023, Month.JUNE, 12) // 2023-06-15 是周四 → 周一为 2023-06-12
        assertEquals(expectedStartOfWeek, startOfWeek.date)
        println("startWeek: $startOfWeek")

        val endOfWeek = baseTime.endWeek()
        val expectedEndOfWeek = LocalDate(2023, Month.JUNE, 18)
        assertEquals(expectedEndOfWeek, endOfWeek.date)
        println("endWeek: $endOfWeek")
    }

    private fun testSpecialDates() {
        println("\n=== 测试特殊日期获取 ===")

        val yesterday = baseTime.yesterday()
        assertEquals(14, yesterday.dayOfMonth)
        println("yesterday: $yesterday")

        val tomorrow = baseTime.tomorrow()
        assertEquals(16, tomorrow.dayOfMonth)
        println("tomorrow: $tomorrow")

        val subHour = baseTime.subtractHour1()
        assertEquals(11, subHour.hour)
        println("subtractHour1: $subHour")

        val subMin30 = baseTime.subtractMinute30()
        assertEquals(0, subMin30.minute)
        println("subtractMinute30: $subMin30")

        val subDay7 = baseTime.subtractDay7()
        assertEquals(8, subDay7.dayOfMonth)
        println("subtractDay7: $subDay7")

        val subDay30 = baseTime.subtractDay30()
        assertEquals(16, subDay30.dayOfMonth) // 15 - 30 = 16 (May)
        println("subtractDay30: $subDay30")
    }

    private fun testMonthStatistics() {
        println("\n=== 测试月份统计函数 ===")

        val mondaysCount = baseTime.countMondaysInMonth()
        println("countMondaysInMonth: $mondaysCount")
        assertTrue(mondaysCount in 4..5)

        val weekFirstDays = baseTime.getWeekFirstDayInMonth()
        println("getWeekFirstDayInMonth 数量: ${weekFirstDays.size}")
        weekFirstDays.forEach { day ->
            assertEquals(DayOfWeek.MONDAY, day.dayOfWeek)
        }

        val weeksInMonth = baseTime.countWeeksInMonth()
        println("countWeeksInMonth: $weeksInMonth")
        assertTrue(weeksInMonth in 4..6)

        val weeksMondayInMonth = baseTime.countWeeksMondayInMonth()
        println("countWeeksMondayInMonth: $weeksMondayInMonth")
        assertTrue(weeksMondayInMonth in 4..6)

        val monthSize = baseTime.monthSize()
        println("monthSize: $monthSize")
        assertEquals(30, monthSize)
    }

    private fun testFormat() {
        println("\n=== 测试格式化 ===")

        val formatted = baseTime.format(DateTimeFormats.FULL)
        println("format(FULL): $formatted")
        assertNotNull(formatted)
    }

    private fun testComparison() {
        println("\n=== 测试比较方法 ===")

        val future = baseTime.add(1, DateTimeUnit.DAY)
        assertTrue(baseTime.isBefore(future))
        println("isBefore: true")

        val past = baseTime.subtract(1, DateTimeUnit.DAY)
        assertTrue(baseTime.isAfter(past))
        println("isAfter: true")

        val same = baseTime.copyDateTime()
        assertTrue(baseTime.isSame(same))
        println("isSame: true")
    }
}

// 为了让测试类运行，需要添加这些辅助函数
private fun LocalDateTime.isBefore(other: LocalDateTime): Boolean {
    return this.toEpochMilliseconds() < other.toEpochMilliseconds()
}

private fun LocalDateTime.isAfter(other: LocalDateTime): Boolean {
    return this.toEpochMilliseconds() > other.toEpochMilliseconds()
}

private fun LocalDateTime.isSame(other: LocalDateTime): Boolean {
    return this.toEpochMilliseconds() == other.toEpochMilliseconds()
}

private fun LocalDateTime.copyDateTime(): LocalDateTime {
    return LocalDateTime.now(this.toEpochMilliseconds())
}

private fun assertTrue(condition: Boolean, message: String = "断言失败") {
    if (!condition) throw AssertionError(message)
}
