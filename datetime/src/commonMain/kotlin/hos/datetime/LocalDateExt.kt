package hos.datetime

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus

/**
 * <p>Title: LocalDatePlus </p>
 * <p>Description: LocalDate 扩展 </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-05-27 21:21
 * @version : 1.0
 */


/**
 * 获取一个特定时间毫秒值和时区的LocalDate实例
 *
 * @param timeMillis 时间毫秒值，默认为0，表示1970年1月1日0时0分0秒
 * @param timeZone 时区，默认为系统当前时区
 * @return 对应的LocalDate实例
 */
fun LocalDate.Companion.now(
    timeMillis: Long = 0,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDate {
    return LocalDateTime.now(timeMillis, timeZone).date
}

/**
 * 从Epoch时间秒值和纳秒调整值转换为LocalDate实例
 *
 * @param epochSeconds Epoch时间秒值
 * @param nanosecondAdjustment 纳秒调整值，默认为0
 * @param timeZone 时区，默认为系统当前时区
 * @return 对应的LocalDate实例
 */
fun LocalDate.Companion.fromEpochSeconds(
    epochSeconds: Long,
    nanosecondAdjustment: Long = 0,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDate {
    return LocalDateTime.fromEpochSeconds(epochSeconds, nanosecondAdjustment, timeZone).date
}

/**
 * 从Epoch时间秒值和纳秒调整值（Int类型）转换为LocalDate实例
 *
 * @param epochSeconds Epoch时间秒值
 * @param nanosecondAdjustment 纳秒调整值
 * @param timeZone 时区，默认为系统当前时区
 * @return 对应的LocalDate实例
 */
fun LocalDate.Companion.fromEpochSeconds(
    epochSeconds: Long,
    nanosecondAdjustment: Int,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDate {
    return LocalDateTime.fromEpochSeconds(epochSeconds, nanosecondAdjustment, timeZone).date
}


/**
 * 使用指定的模式格式化日期。
 *
 * 此函数允许[LocalDate]对象根据提供的模式字符串进行格式化，默认为[DateTimeFormats.Format.FULL]。
 * 它创建了一个日期格式对象，并使用该对象来格式化当前日期。
 *
 * @param pattern 指定的模式字符串，默认为[DateTimeFormats.Format.FULL]。
 * @return 格式化后的日期字符串。
 */
@FormatStringsInDatetimeFormats
fun LocalDate.format(pattern: DateTimeFormats = DateTimeFormats.FULL): String {
    // 创建一个日期格式对象，根据提供的模式进行配置
    val dateTimeFormat = LocalDate.Format {
        byUnicodePattern(pattern.pattern)
    }
    // 使用创建的日期格式对象格式化当前日期并返回结果
    return format(dateTimeFormat)
}

/**
 * 获取指定日期的内容
 *
 * 该函数用于在特定日期上执行给定的操作或属性获取，并返回结果
 * 它使用伴生对象LocalDate.Companion来实现日期的创建和操作
 *
 * @param millis 需要获取内容的日期相对于1970年1月1日00:00:00 GMT的毫秒数，默认为0
 * @param timeZone 用于解释毫秒数的时区，默认为系统当前时区
 * @param content 一个在指定日期上执行以获取结果的代码块
 * @return 从content参数代码块执行中返回的值
 */
fun <T> LocalDate.Companion.getContent(
    millis: Long = 0,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
    content: LocalDate.() -> T
): T {
    return content.invoke(now(millis, timeZone))
}

/**
 * 设置日期的特定部分（年、月、日）
 *
 * 此函数允许通过指定要设置的日期部分（年、月、日）和对应的值来修改LocalDate对象
 * 它根据提供的单位参数选择要设置的日期部分，并返回一个具有新值的LocalDate对象
 *
 * @param value 要设置的新值
 * @param unit 日期时间单位，指定要修改的日期部分（年、月、日）
 * @return 返回一个具有新设置日期部分的LocalDate对象
 * @throws IllegalArgumentException 如果提供的单位不是支持的日期时间单位，则抛出此异常
 */
fun LocalDate.set(value: Int, unit: DateTimeUnit): LocalDate {
    // 当设置年份时，使用提供的年份值替换当前日期的年份，保持月份和日期不变
    if (unit == DateTimeUnit.YEAR) {
        return LocalDate(value, monthNumber, dayOfMonth)
    }
    // 当设置月份时，使用提供的月份值替换当前日期的月份，保持年份和日期不变
    if (unit == DateTimeUnit.MONTH) {
        return LocalDate(year, value, dayOfMonth)
    }
    // 当设置日期时，使用提供的日期值替换当前日期的日期，保持年份和月份不变
    if (unit == DateTimeUnit.DAY) {
        return LocalDate(year, monthNumber, value)
    }
    // 如果提供的单位不是支持的日期时间单位，抛出异常
    throw IllegalArgumentException("不支持的参数")
}

/**
 * 为当前日期添加指定的时间单位数量
 *
 * 此函数提供了一种直观的方式来增加日期对象的值它使用整数和日期时间单位作为参数，
 * 允许开发者轻松地计算未来或过去的一个时间点通过调用此函数，可以得到一个新的日期，
 * 而不会修改原始日期对象
 *
 * @param value 要添加到当前日期的数值可以是正数（代表未来的时间）或负数（代表过去的时间）
 * @param unit 日期时间单位，指定了数值所对应的时间跨度例如，年、月、日等
 * @return 返回一个新的LocalDate对象，表示在当前日期上加上指定时间单位数量后的日期
 */
fun LocalDate.add(value: Int, unit: DateTimeUnit.DateBased): LocalDate {
    return plus(value, unit)
}


/**
 * 从当前日期中减去指定的时间单位和数量
 *
 * 此函数允许通过指定要减去的值和时间单位来计算新的日期它利用了`minus`函数，
 * 提供了一种更直观的方式来执行日期减法操作
 *
 * @param value 要减去的时间单位的数量，必须为非负数
 * @param unit  要减去的时间单位，基于日期的时间单位
 * @return 一个新的LocalDate对象，表示减去指定时间单位和数量后的日期
 */
fun LocalDate.subtract(value: Int, unit: DateTimeUnit.DateBased): LocalDate {
    return minus(value, unit)
}

/**
 * 将LocalDate对象转换为自1970年1月1日以来的毫秒数。
 * 此函数的目的是为了提供一个简单的方法来将日期转换为可以在不同平台和编程语言之间互操作的时间格式。
 * 通过将日期转换为毫秒数，可以更容易地在不同的时间单位和时间区之间进行转换和计算。
 *
 * @return 自1970年1月1日以来的毫秒数。
 */
fun LocalDate.toEpochMilliseconds(): Long {
    // 通过将LocalDate与LocalTime结合，创建一个表示一天开始时间的LocalDateTime对象。
    // 这是因为我们需要一个包含时区信息的时间对象，以便将其转换为自1970年以来的毫秒数。
    // LocalTime(0, 0, 0)表示一天的开始时间，即00:00:00。
    return LocalDateTime(this, LocalTime(0, 0, 0)).toEpochMilliseconds()
}


/**
 * 返回当前日期对象在月份中的天数
 *
 * 此方法提供了一种简洁的方式来获取[LocalDate]对象的天数，而无需直接调用[dayOfMonth]方法
 * 它简化了代码可读性，使得获取天数更加直观
 *
 * @return 月份中的天数，范围为1到31
 */
fun LocalDate.day(): Int {
    return this.dayOfMonth
}


/**
 * 获取当前日期所在年的年初日期
 *
 * 此方法用于获取一个表示当前日期所在年份年初（即1月1日）的[LocalDate]对象
 * 它通过传入当前日期的年份，月份设置为1月，日期设置为1日来创建新的[LocalDate]对象
 *
 * @return 表示当前日期所在年年初的[LocalDate]对象
 */
fun LocalDate.startYear(): LocalDate {
    return LocalDate(year, 1, 1)
}


/**
 * 获取当前年份的最后一天
 *
 * 此方法用于返回一个日期对象，该对象代表当前年份的12月31日
 * 它可以用于需要年末日期的各种场景，如日期计算、报表生成等
 *
 * @return LocalDate 一个代表当前年份最后一天的LocalDate对象
 */
fun LocalDate.endYear(): LocalDate {
    return LocalDate(year, 12, 31)
}


/**
 * 获取当前日期所在月份的第一天日期
 *
 * 此方法用于将当前日期的天数重置为该月的第一天，常用于日期范围计算或日期初始化
 * 它保留了当前日期的年份和月份信息，仅将天数设置为1
 *
 * @return 返回一个新的LocalDate对象，表示当前日期所在月份的第一天
 */
fun LocalDate.startMonth(): LocalDate {
    return LocalDate(year, monthNumber, 1)
}


/**
 * 获取当前日期所在月份的最后一天
 *
 * 通过将日期增加到下个月的第一天，然后再减去一天，来获取当前月份的最后一天
 * 这种方法可以处理不同年份和月份的末尾日期计算问题
 *
 * @return 当前日期所在月份的最后一天的日期
 */
fun LocalDate.endMonth(): LocalDate {
    // 设置为下个月第一天
    return add(1, DateTimeUnit.MONTH)
        // 再设置为该月的第一天
        .startMonth()
        // 从下个月的第一天减去一天，得到当前月份的最后一天
        .subtract(1, DateTimeUnit.DAY)
}


/**
 * 获取当前日期所在周的起始日期
 *
 * 此函数旨在计算并返回给定日期所在周的起始日期，即周的开始是周一
 * 它通过计算当前日期与本周周一之间的天数差，然后从当前日期中减去这个天数差来找到起始日期
 *
 * @return LocalDate 返回当前日期所在周的起始日期
 */
fun LocalDate.startWeek(): LocalDate {
    // 获取当前日期是周几
    val dayOfWeek = dayOfWeek
    // 计算当前日期与本周周一之间的天数差
    val offset = when (dayOfWeek.isoDayNumber) {
        1 -> 0 // 如果今天是周一，不需要调整
        2 -> 1 // 如果今天是周二，减去1天
        3 -> 2 // 如果今天是周三，减去2天
        4 -> 3 // 如果今天是周四，减去3天
        5 -> 4 // 如果今天是周五，减去4天
        6 -> 5 // 如果今天是周六，减去5天
        0 -> 6 // 如果今天是周日，减去6天
        else -> 0 // 默认情况，应不会到达
    }
    // 从当前日期减去计算出的天数差，得到本周的起始日期
    return subtract(offset, DateTimeUnit.DAY)
}

/**
 * 获取当前日期所在周的最后一天
 *
 * 通过将当前日期设置为所在周的第一天，然后增加6天来获取最后一天的日期
 *
 * @return 当前日期所在周的最后一天的日期
 */
fun LocalDate.endWeek(): LocalDate {
    return startWeek()
        .add(6, DateTimeUnit.DAY)
}

/**
 * 获取当前日期的前一天
 *
 * 通过从当前日期减去一天来获取前一天的日期
 *
 * @return 当前日期的前一天的日期
 */
fun LocalDate.yesterday(): LocalDate {
    return subtract(1, DateTimeUnit.DAY)
}

/**
 * 获取当前日期的前两天
 *
 * 通过从当前日期减去两天来获取前两天的日期
 *
 * @return 当前日期的前两天的日期
 */
fun LocalDate.yesterdayBefore(): LocalDate {
    return subtract(2, DateTimeUnit.DAY)
}

/**
 * 获取当前日期的后一天
 *
 * 通过向当前日期增加一天来获取后一天的日期
 *
 * @return 当前日期的后一天的日期
 */
fun LocalDate.tomorrow(): LocalDate {
    return add(1, DateTimeUnit.DAY)
}

/**
 * 获取当前日期的后两天
 *
 * 通过向当前日期增加两天来获取后两天的日期
 *
 * @return 当前日期的后两天的日期
 */
fun LocalDate.tomorrowAfter(): LocalDate {
    return add(2, DateTimeUnit.DAY)
}

/**
 * 获取当前月份的天数
 *
 * 通过将日期设置为当前月的最后一天，然后返回该天的日期来计算当前月的天数
 *
 * @return 当前月份的天数
 */
fun LocalDate.monthSize(): Int {
    // 设置为这个月最后一天
    // 获取最后一天的日期
    return endMonth().day()
}

/**
 * 计算当前月份中星期一的天数
 *
 * 此方法通过遍历当前月份的每一天，并检查每一天是否为星期一来计算星期一的总数
 * 它利用了ICalendar类中的方法来获取月份的大小，并设置和获取日期字段
 *
 * @return 当前月份中星期一的天数
 */
fun LocalDate.countMondaysInMonth(): Int {
    // 获取当前月份的天数总数
    val monthSize = monthSize()
    // 初始化星期一的计数器
    var count = 0

    // 遍历当前月份的每一天
    for (day in 1..monthSize) {
        // 设置日历的日期字段为当前迭代的天
        val dayOfWeek = set(day, DateTimeUnit.DAY).dayOfWeek
        // 检查当前天是否为星期一，如果是，则增加计数器
        if (dayOfWeek == DayOfWeek.MONDAY) {
            count++
        }
    }
    // 返回当前月份中星期一的总天数
    return count
}

/**
 * 获取当前月份所有星期一的日期列表
 *
 * 此方法通过遍历当前月份的每一天，检查是否为星期一，并将所有星期一的日期添加到列表中
 * 它首先定位到当前月份的第一天，然后逐天检查直到月份的最后一天
 *
 * @return 当前月份所有星期一的日期列表
 */
fun LocalDate.getWeekFirstDayInMonth(): MutableList<LocalDate> {
    var startMonth = startMonth() // 定位到当月第一天
    val currentMonth = startMonth.monthNumber
    val mondays = mutableListOf<LocalDate>()
    // 遍历整个月份的每一天
    while (startMonth.monthNumber == currentMonth) {
        if (startMonth.dayOfWeek == DayOfWeek.MONDAY) {
            mondays.add(LocalDate.now(startMonth.toEpochMilliseconds()))
        }
        startMonth = startMonth.add(1, DateTimeUnit.DAY) // 移动到下一天
    }
    return mondays
}

/**
 * 计算当前月份包含的周数
 * 此方法考虑了月份的起始日和结束日的星期，以及月份的总天数，以准确计算周数
 * 周的开始被假设为周一，这是根据大多数日历的表示方式
 *
 * @return 当前月份的周数
 */
fun LocalDate.countWeeksInMonth(): Int {
    // 设置为月初第一天并获取其星期几
    val firstDayOfWeek = startMonth().dayOfWeek
    // 获取月末最后一天并获取其星期几
    val lastDayOfWeek = endMonth().dayOfWeek
    // 月份总天数
    val totalDays = day()
    // 假设周从周一开始（DAY_OF_WEEK 中 Monday=1）
    val adjustedFirst =
        if (firstDayOfWeek == DayOfWeek.MONDAY) 7 else firstDayOfWeek.isoDayNumber // Sunday -> 7
    val adjustedLast = if (lastDayOfWeek == DayOfWeek.MONDAY) 7 else lastDayOfWeek.isoDayNumber
    // 计算完整周数：(首日偏移 + 总天数 + 末日偏移 - 首日偏移) / 7 向上取整
    return ((adjustedFirst + totalDays + (7 - adjustedLast)) + 6) / 7
}

