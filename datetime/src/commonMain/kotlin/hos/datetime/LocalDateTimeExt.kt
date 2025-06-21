package hos.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

/**
 * <p>Title: LocalDateTimePlus </p>
 * <p>Description: LocalDateTime扩展 </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-05-27 21:21
 * @version : 1.0
 */


/**
 * 获取一个 LocalDateTime 实例。
 *
 * 此函数允许通过指定时间毫秒数和时区来获取一个 LocalDateTime 实例。
 * 如果未指定时间毫秒数，则默认为当前系统时间。如果未指定时区，则使用系统默认时区。
 *
 * @param millis 时间毫秒数，自1970年1月1日0时0分0秒（UTC）以来的毫秒数。默认为0，表示当前系统时间。
 * @param timeZone 时区对象，用于确定 LocalDateTime 的时区。默认为系统当前时区。
 * @return 返回一个 LocalDateTime 实例，表示在指定时区的日期和时间。
 */
fun LocalDateTime.Companion.now(
    millis: Long = 0,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDateTime {
    // 如果时间毫秒数为默认值0，则返回当前系统时间对应的 LocalDateTime 实例
    if (millis == 0L) {
        return Clock.System.now().toLocalDateTime(timeZone)
    }
    // 否则，将指定的时间毫秒数转换为 Instant 对象，然后转换为 LocalDateTime 实例
    return Instant.fromEpochMilliseconds(millis).toLocalDateTime(timeZone)
}


/**
 * 从Unix时间戳（自1970年1月1日0时0分0秒（UTC）以来的秒数）创建一个LocalDateTime对象。
 *
 * 此函数提供了一种将Unix时间戳转换为特定时区内日期和时间的方法。
 *
 * @param epochSeconds Unix时间戳的秒数部分。
 * @param nanosecondAdjustment 对秒数的纳秒级调整，默认为0，用于精确调整时间。
 * @param timeZone 指定的时间区，用于将UTC时间转换为本地时间，默认为系统当前时区。
 * @return 对应的LocalDateTime对象，表示在指定时区内的日期和时间。
 */
fun LocalDateTime.Companion.fromEpochSeconds(
    epochSeconds: Long,
    nanosecondAdjustment: Long = 0, timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    if (epochSeconds == 0L) {
        return Clock.System.now().toLocalDateTime(timeZone)
    }

    // 使用给定的Unix时间戳和纳秒调整值创建一个Instant对象，然后将其转换为LocalDateTime对象。
    return Instant.fromEpochSeconds(epochSeconds, nanosecondAdjustment)
        .toLocalDateTime(timeZone)
}


/**
 * 从Unix时间戳（自1970年1月1日0时0分0秒（UTC）以来的秒数）创建一个LocalDateTime对象。
 * 此函数允许通过指定秒数和纳秒级调整值来精确地从Unix时间戳转换为特定时区的日期和时间。
 *
 * @param epochSeconds Unix时间戳，表示自1970年1月1日0时0分0秒（UTC）以来的秒数。
 * @param nanosecondAdjustment 对Unix时间戳的纳秒级调整值，用于精确调整时间。
 * @param timeZone 指定的时区，用于将UTC时间转换为本地时间。默认为系统当前时区。
 * @return 返回一个LocalDateTime对象，表示在指定时区中的日期和时间。
 */
fun LocalDateTime.Companion.fromEpochSeconds(
    epochSeconds: Long,
    nanosecondAdjustment: Int, timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    // 使用给定的Unix时间戳和纳秒级调整值创建Instant对象，然后将其转换为指定时区的LocalDateTime对象。
    return Instant.fromEpochSeconds(epochSeconds, nanosecondAdjustment)
        .toLocalDateTime(timeZone)
}


/**
 * 获取当前时间的毫秒时间戳
 *
 * @param timeZone 用于计算时间戳的时区，默认为系统当前时区
 * @return 当前时间的毫秒时间戳
 */
fun LocalDateTime.Companion.getNowMills(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Long {
    return LocalDateTime.now(timeZone = timeZone).toEpochMilliseconds(timeZone)
}

/**
 * 使用指定的模式、毫秒值和时区格式化当前日期时间字符串。
 *
 * @param millis 相对于1970年1月1日00:00:00 GMT的毫秒值，默认为0。
 * @param pattern 日期时间的显示模式，默认为TimeFormat.FULL。
 * @param timeZone 用于计算日期时间的时区，默认为系统当前时区。
 * @return 格式化后的日期时间字符串。
 */
@FormatStringsInDatetimeFormats
fun LocalDateTime.Companion.formatNowString(
    millis: Long = 0L,
    pattern: DateTimeFormats = DateTimeFormats.FULL,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): String {
    return LocalDateTime.now(millis, timeZone).format(pattern)
}

/**
 * 使用指定的模式、毫秒值和时区格式化当前日期时间字符串，时间设置为当天开始时刻。
 *
 * @param millis 相对于1970年1月1日00:00:00 GMT的毫秒值，默认为0。
 * @param pattern 日期时间的显示模式，默认为TimeFormat.FULL。
 * @param timeZone 用于计算日期时间的时区，默认为系统当前时区。
 * @return 格式化后的日期时间字符串，时间设置为当天开始时刻。
 */
@FormatStringsInDatetimeFormats
fun LocalDateTime.Companion.formatNowStringStart(
    millis: Long = 0L,
    pattern: DateTimeFormats = DateTimeFormats.FULL,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): String {
    return LocalDateTime.now(millis, timeZone).startDay().format(pattern)
}

/**
 * 使用指定的模式、毫秒值和时区格式化当前日期时间字符串，时间设置为当天结束时刻。
 *
 * @param millis 相对于1970年1月1日00:00:00 GMT的毫秒值，默认为0。
 * @param pattern 日期时间的显示模式，默认为TimeFormat.FULL。
 * @param timeZone 用于计算日期时间的时区，默认为系统当前时区。
 * @return 格式化后的日期时间字符串，时间设置为当天结束时刻。
 */
@FormatStringsInDatetimeFormats
fun LocalDateTime.Companion.formatNowStringEnd(
    millis: Long = 0L,
    pattern: DateTimeFormats = DateTimeFormats.FULL,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): String {
    return LocalDateTime.now(millis, timeZone).endDay().format(pattern)
}

fun LocalDateTime.Companion.parse(
    input: String,
    pattern: DateTimeFormats? = DateTimeFormats.getFormatString(input)
): LocalDateTime {
    if (pattern == null) {
        throw IllegalArgumentException("pattern is not null")
    }
    return parse(DateTimeFormats.format(input + pattern.full), pattern.localDateTime)
}

/**
 * 将当前[LocalDateTime]对象转换为[Instant]对象。
 * 此方法主要用于将本地日期时间转换为一个与时间区无关的时间点。
 *
 * @param timeZone 时间区，用于定义本地日期时间与UTC时间的转换规则。
 *                 默认使用系统当前的默认时间区。
 * @return 转换后的[Instant]对象，表示一个与时间区无关的时间点。
 */
fun LocalDateTime.asInstant(timeZone: TimeZone = TimeZone.currentSystemDefault()): Instant {
    return toInstant(timeZone)
}


/**
 * 使用指定的模式格式化当前的LocalDateTime对象。
 *
 * 此函数通过创建一个与给定模式相匹配的DateTimeFormat对象来格式化时间日期，
 * 并返回格式化后的字符串表示形式。如果未提供模式参数，则使用默认的FULL模式。
 *
 * @param pattern 可选参数，指定用于格式化LocalDateTime的模式字符串，默认为TimeFormat.FULL。
 * @return 格式化后的LocalDateTime字符串。
 */
@FormatStringsInDatetimeFormats
fun LocalDateTime.format(pattern: DateTimeFormats = DateTimeFormats.FULL): String {
    // 创建一个DateTimeFormat对象，根据Unicode时间格式语言定义。
    val dateTimeFormat = LocalDateTime.Format {
        byUnicodePattern(pattern.pattern)
    }
    // 使用创建的DateTimeFormat对象格式化当前的LocalDateTime。
    return format(dateTimeFormat)
}

/**
 * 获取指定时间点的内容
 *
 * 该函数用于在给定的时间点上执行一个操作，并返回结果它首先根据提供的毫秒时间戳和时区获取一个LocalDateTime实例，
 * 然后在这个实例上调用提供给它的lambda函数以获取内容这种方式使得能够在特定的时间上下文中获取信息
 *
 * @param <T> 返回类型，由lambda函数决定
 * @param millis 时间戳，自1970年1月1日00:00:00 GMT以来的毫秒数默认为0
 * @param timeZone 用于计算LocalDateTime的时区默认为系统当前时区
 * @param content 一个在特定时间点执行的操作，以获取内容
 * @return 由lambda函数计算得到的结果
 */
fun <T> LocalDateTime.Companion.getContent(
    millis: Long = 0,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
    content: LocalDateTime.() -> T
): T {
    return content.invoke(now(millis, timeZone))
}


/**
 * 设置日期时间的特定部分为指定值
 *
 * 此函数允许通过指定时间单位来修改日期时间对象的特定部分，如年、月、日、小时、分钟、秒、毫秒、微秒或纳秒
 * 如果指定的时间单位不被支持，则会抛出IllegalArgumentException异常
 *
 * @param value 要设置的新值
 * @param unit 指定要修改的时间单位
 * @return 修改后的日期时间对象
 * @throws IllegalArgumentException 如果指定的时间单位不被支持
 */
fun LocalDateTime.set(value: Int, unit: DateTimeUnit): LocalDateTime {
    // 根据不同的时间单位修改日期时间对象的相应部分
    if (unit == DateTimeUnit.YEAR) {
        // 修改年份
        return LocalDateTime(
            LocalDate(value, monthNumber, dayOfMonth),
            LocalTime(hour, minute, second)
        )
    }
    if (unit == DateTimeUnit.MONTH) {
        // 修改月份
        return LocalDateTime(
            LocalDate(year, value, dayOfMonth),
            LocalTime(hour, minute, second)
        )
    }
    if (unit == DateTimeUnit.DAY) {
        // 修改日期
        return LocalDateTime(
            LocalDate(year, monthNumber, value),
            LocalTime(hour, minute, second)
        )
    }
    if (unit == DateTimeUnit.HOUR) {
        // 修改小时
        return LocalDateTime(
            LocalDate(year, monthNumber, dayOfMonth),
            LocalTime(value, minute, second)
        )
    }
    if (unit == DateTimeUnit.MINUTE) {
        // 修改分钟
        return LocalDateTime(
            LocalDate(year, monthNumber, dayOfMonth),
            LocalTime(hour, value, second)
        )
    }
    if (unit == DateTimeUnit.SECOND) {
        // 修改秒
        return LocalDateTime(
            LocalDate(year, monthNumber, dayOfMonth),
            LocalTime(hour, minute, value)
        )
    }
    if (unit == DateTimeUnit.MILLISECOND) {
        // 修改毫秒
        return LocalDateTime(
            LocalDate(year, monthNumber, dayOfMonth),
            LocalTime(hour, minute, second, value * 1000000)
        )
    }
    if (unit == DateTimeUnit.MICROSECOND) {
        // 修改微秒
        return LocalDateTime(
            LocalDate(year, monthNumber, dayOfMonth),
            LocalTime(hour, minute, second, value * 1000)
        )
    }
    if (unit == DateTimeUnit.NANOSECOND) {
        // 修改纳秒
        return LocalDateTime(
            LocalDate(year, monthNumber, dayOfMonth),
            LocalTime(hour, minute, second, value)
        )
    }
    // 如果时间单位不被支持，则抛出异常
    throw IllegalArgumentException("不支持的参数$unit")
}


/**
 * 扩展函数，用于在当前LocalDateTime对象上添加指定的时间单位和数值
 *
 * 此函数通过接收一个整数值和一个DateTimeUnit枚举来确定如何增加当前时间
 * 它覆盖了从年到纳秒的时间单位，对于每个单位，它都会创建一个新的LocalDateTime对象，
 * 其中指定的时间单位被增加了指定的数值
 *
 * @param value 要增加的数值
 * @param unit 时间单位，决定了数值将被应用到哪个时间单位上
 * @return 一个新的LocalDateTime对象，其指定的时间单位被增加了指定的数值
 * @throws IllegalArgumentException 如果提供的unit参数不被支持
 */
fun LocalDateTime.add(
    value: Int,
    unit: DateTimeUnit,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {

    // 根据不同的时间单位，执行相应的操作
    if (unit == DateTimeUnit.YEAR) {
        return LocalDateTime(
            date.add(value, DateTimeUnit.YEAR),
            LocalTime(hour, minute, second, nanosecond)
        )
    }
    if (unit == DateTimeUnit.MONTH) {
        // 修改月份
        return LocalDateTime(
            date.add(value, DateTimeUnit.MONTH),
            LocalTime(hour, minute, second, nanosecond)
        )
    }
    if (unit == DateTimeUnit.DAY) {
        // 修改日期
        return LocalDateTime(
            date.add(value, DateTimeUnit.DAY),
            LocalTime(hour, minute, second, nanosecond)
        )
    }
    if (unit == DateTimeUnit.HOUR) {
        // 修改小时
        return toInstant(timeZone)
            .plus(value, DateTimeUnit.HOUR)
            .toLocalDateTime(timeZone)
    }
    if (unit == DateTimeUnit.MINUTE) {
        // 修改分钟
        return toInstant(timeZone)
            .plus(value, DateTimeUnit.MINUTE)
            .toLocalDateTime(timeZone)
    }
    if (unit == DateTimeUnit.SECOND) {
        // 修改秒
        return toInstant(timeZone)
            .plus(value, DateTimeUnit.SECOND)
            .toLocalDateTime(timeZone)
    }
    if (unit == DateTimeUnit.MILLISECOND) {
        // 修改毫秒
        return toInstant(timeZone)
            .plus(value, DateTimeUnit.MILLISECOND)
            .toLocalDateTime(timeZone)
    }
    if (unit == DateTimeUnit.MICROSECOND) {
        // 修改微秒
        return toInstant(timeZone)
            .plus(value, DateTimeUnit.MICROSECOND)
            .toLocalDateTime(timeZone)
    }
    if (unit == DateTimeUnit.NANOSECOND) {
        // 修改纳秒
        return toInstant(timeZone)
            .plus(value, DateTimeUnit.NANOSECOND)
            .toLocalDateTime(timeZone)
    }
    // 如果提供的unit参数不被支持，抛出异常
    throw IllegalArgumentException("不支持的参数")
}


/**
 * 从当前[LocalDateTime]对象中减去指定的时间单位和数量
 *
 * 此函数通过调用[add]函数，以负数值的方式实现时间的减法操作
 * 它提供了一种直观的方式来对日期和时间进行减法运算
 *
 * @param value 要减去的时间数量，必须为非负整数
 * @param unit 时间单位，指定了减去的时间类型，例如天、小时等
 * @return 返回减去指定时间单位和数量后的[LocalDateTime]对象
 */
fun LocalDateTime.subtract(
    value: Int,
    unit: DateTimeUnit,
): LocalDateTime {
    return add(-value, unit)
}


/**
 * 将LocalDateTime转换为自1970年1月1日0时0分0秒（UTC）以来的毫秒数
 * 此方法用于获取当前时间的Unix时间戳（毫秒精度）
 *
 * @return 自1970年1月1日0时0分0秒（UTC）以来的毫秒数
 */
fun LocalDateTime.toEpochMilliseconds(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    // 将LocalDateTime转换为Instant，并使用当前系统默认时区
    // 然后将其转换为自1970年1月1日0时0分0秒（UTC）以来的毫秒数
    return this.toInstant(timeZone).toEpochMilliseconds()
}

fun LocalDateTime.toNanosecondsOfSecond(timeZone: TimeZone = TimeZone.currentSystemDefault()): Int {
    return this.toInstant(timeZone).nanosecondsOfSecond
}

fun LocalDateTime.toEpochSeconds(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    return this.toInstant(timeZone).epochSeconds
}


/**
 * 获取当前日期时间对象的天（日期）
 *
 * 此方法扩展了LocalDateTime类，使其能够方便地获取当前日期时间对象的天（日期）
 * 它返回一个整数，表示在当前月份中的天数
 *
 * @return 当前日期时间对象在月份中的天数
 */
fun LocalDateTime.day(): Int {
    return this.dayOfMonth
}


/**
 * 将当前日期时间对象设置为当天的开始时间（即00:00:00）
 *
 * 此方法通过保持当前对象的日期部分不变，将时间部分设置为午夜（00:00:00），
 * 从而创建一个新的LocalDateTime对象，代表当前日期的开始时刻
 *
 * @return 返回一个新的LocalDateTime对象，表示当前日期的开始时间
 */
fun LocalDateTime.startDay(): LocalDateTime {
    return LocalDateTime(date, LocalTime(0, 0, 0))
}


/**
 * 获取当前日期的结束时间（即当天的23:59:59）
 *
 * 这个函数的目的是为了提供一个简便的方式来获取一个日期的最后时刻，
 * 这对于时间范围的计算和比较非常有用
 *
 * @return 返回一个LocalDateTime对象，代表当前日期的23:59:59时刻
 */
fun LocalDateTime.endDay(): LocalDateTime {
    return LocalDateTime(date, LocalTime(23, 59, 59))
}


/**
 * 获取当前日期时间对象的年初时刻
 * 此函数将当前日期时间对象的日期设置为当年的1月1日，时间设置为00:00:00，
 * 从而返回一个代表当年年初的日期时间对象这在需要计算或比较年度起始点时非常有用
 *
 * @return 返回一个代表当年年初（1月1日 00:00:00）的LocalDateTime对象
 */
fun LocalDateTime.startYear(): LocalDateTime {
    return LocalDateTime(LocalDate(year, 1, 1), LocalTime(0, 0, 0))
}


/**
 * 获取当前日期时间对象所在年的最后时刻
 *
 * 该函数将当前日期时间对象转换为同年12月31日的23:59:59
 * 这用于获取当前年份的最后时刻，以便在时间计算和比较中使用
 *
 * @return 返回一个表示当年最后时刻的LocalDateTime对象，即12月31日的23:59:59
 */
fun LocalDateTime.endYear(): LocalDateTime {
    return LocalDateTime(LocalDate(year, 12, 31), LocalTime(23, 59, 59))
}


/**
 * 将当前日期时间对象设置为当月的第一秒。
 * 此方法重置日期时间为当前月份的第一天的零点零分零秒，年份和月份保持不变。
 * 主要用途是用于日期时间范围的计算，比如统计某月的数据时将日期时间设置为月份的起始点。
 *
 * @return 返回一个新的LocalDateTime对象，表示当前月份的第一秒。
 */
fun LocalDateTime.startMonth(): LocalDateTime {
    return LocalDateTime(LocalDate(year, monthNumber, 1), LocalTime(0, 0, 0))
}


/**
 * 获取当前日期时间所在月份的最后一天的日期时间
 *
 * 该函数通过将当前日期时间增加一个月，然后设置为该月的第一天，再减去一天，最后设置为当天的最后时刻
 * 来计算当前月份的最后一天的日期时间这种方法确保了无论当前日期时间是哪一天，都能正确地计算出
 * 当月的最后一天，不受月份天数差异的影响
 *
 * @return 当前月份最后一天的日期时间，时间部分与当前时间相同
 */
fun LocalDateTime.endMonth(): LocalDateTime {
    // 设置为下个月第一天
    return add(1, DateTimeUnit.MONTH)
        .startMonth()
        .subtract(1, DateTimeUnit.DAY)
        .endDay()
}


/**
 * 获取当前日期所在周的起始时间（周一）
 *
 * 此方法通过计算当前日期距离周一还有几天，然后减去相应的天数，
 * 再调用[startDay]方法将时间设置为当天的00:00:00，从而得到本周的开始时间
 *
 * @return 返回一个新的LocalDateTime对象，表示当前周的起始时间（周一 00:00:00）
 */
fun LocalDateTime.startWeek(): LocalDateTime {
    // 获取当前日期的星期几
    val dayOfWeek = dayOfWeek
    // 计算当前星期几距离周一的天数
    val offset = when (dayOfWeek.isoDayNumber) {
        1 -> 0 // 当前是周一，无需偏移
        2 -> 1 // 当前是周二，需要回退1天
        3 -> 2 // 当前是周三，需要回退2天
        4 -> 3 // 当前是周四，需要回退3天
        5 -> 4 // 当前是周五，需要回退4天
        6 -> 5 // 当前是周六，需要回退5天
        0 -> 6 // 当前是周日，需要回退6天
        else -> 0 // 默认情况，不进行偏移
    }
    // 减去对应的天数，并获取该天的起始时间
    return subtract(offset, DateTimeUnit.DAY)
        .startDay()
}


/**
 * 获取当前日期时间所在周的最后时刻
 *
 * 该函数通过将当前日期时间调整到所在周的开始时刻（[startWeek]函数实现），
 * 然后增加6天，并调用[endDay]函数来获取该周最后一天的最后时刻
 *
 * @return 当前日期时间所在周的最后时刻
 */
fun LocalDateTime.endWeek(): LocalDateTime {
    return startWeek()
        .add(6, DateTimeUnit.DAY)
        .endDay()
}


/**
 * 获取当前时间的前一天时间
 *
 * 此方法用于获取当前时间的前一天时间，即昨天的时间
 * 它通过从当前时间减去一天来计算昨天的时间
 *
 * @return 昨天的时间，类型为LocalDateTime
 */
fun LocalDateTime.yesterday(): LocalDateTime {
    return subtract(1, DateTimeUnit.DAY)
}


/**
 * 获取当前时间的前一天同一时间点
 *
 * 此方法用于获取当前时间的前一天的同一时间点，例如，如果当前时间是2023-10-05T14:30:00，
 * 那么返回的时间将是2023-10-04T14:30:00
 *
 * @return 当前时间的前一天同一时间点
 */
fun LocalDateTime.yesterdayBefore(): LocalDateTime {
    return subtract(2, DateTimeUnit.DAY)
}


/**
 * 获取当前日期时间的明天同一时间点
 *
 * 此方法通过添加一个时间单位（天）到当前日期时间，计算出明天的日期时间
 * 它主要用于获取未来一天的同一时刻，适用于需要进行时间运算或预测的场景
 *
 * @return 返回代表明天同一时间点的LocalDateTime对象
 */
fun LocalDateTime.tomorrow(): LocalDateTime {
    return add(1, DateTimeUnit.DAY)
}


/**
 * 获取当前时间的后天同一时间点
 *
 * 此方法用于获取当前时间的后天同一时间点，例如，如果当前时间是2023年10月5日15:00，
 * 那么调用此方法将返回2023年10月6日15:00
 *
 * @return 返回后天同一时间点的LocalDateTime对象
 */
fun LocalDateTime.tomorrowAfter(): LocalDateTime {
    return add(2, DateTimeUnit.DAY)
}


/**
 * 从当前日期时间中减去指定的小时数
 *
 * @return 减去1小时后的日期时间对象
 */
fun LocalDateTime.subtractHour1(): LocalDateTime {
    return subtract(1, DateTimeUnit.HOUR)
}


/**
 * 从当前时间减去30分钟
 *
 * 此方法用于获取当前时间之前30分钟的时间点主要用于时间范围查询、调度和时间计算等场景
 * 选择30分钟作为一个特定的时间间隔，可能是因为某些业务逻辑需要基于半小时的时间窗口进行处理
 *
 * @return 返回一个新的LocalDateTime对象，该对象比当前时间早30分钟
 */
fun LocalDateTime.subtractMinute30(): LocalDateTime {
    return subtract(30, DateTimeUnit.MINUTE)
}

/**
 * 从当前日期时间减去30天
 *
 * 此方法用于获取当前日期时间的30天前的日期时间对象它使用[DateTimeUnit.DAY]作为时间单位，
 * 并从当前日期时间对象中减去指定数量的时间单位
 *
 * @return 30天前的日期时间对象
 */
fun LocalDateTime.subtractDay30(): LocalDateTime {
    return subtract(30, DateTimeUnit.DAY)
}


/**
 * 从当前日期时间减去7天
 *
 * 此函数用于获取当前日期时间的7天前的日期时间对象它通过调用`subtract`函数，
 * 并传入7作为天数以及[DateTimeUnit.DAY]作为单位来实现
 *
 * @return 减去7天后的[LocalDateTime]对象
 */
fun LocalDateTime.subtractDay7(): LocalDateTime {
    return subtract(7, DateTimeUnit.DAY)
}

/**
 * 获取当前日期所在月份的天数
 *
 * 通过计算当前月份的最后一天来确定该月的天数
 *
 * @return 当前月份的天数
 */
fun LocalDateTime.monthSize(): Int {
    return endMonth().day()
}


/**
 * 计算当前月份中星期一的天数
 *
 * 此函数通过检查当前月份的每一天是否为星期一来计算星期一的总数
 * 它首先获取当前月份的天数，然后遍历每一天，检查是否为星期一
 * 如果是星期一，则增加计数器最后返回计数器的值
 *
 * @return 当前月份中星期一的天数
 */
fun LocalDateTime.countMondaysInMonth(): Int {
    // 获取当前月份的天数
    val monthSize = monthSize()
    // 初始化星期一的计数器
    var count = 0
    // 遍历当前月份的每一天
    for (day in 1..monthSize) {
        // 获取当前天的星期几
        val dayOfWeek = set(day, DateTimeUnit.DAY).dayOfWeek
        // 如果当前天是星期一，则计数器增加
        if (dayOfWeek == DayOfWeek.MONDAY) {
            count++
        }
    }
    // 返回星期一的总数
    return count
}


/**
 * 获取当前月份所有的周一日期列表
 *
 * 该函数会遍历当前月份的每一天，并检查是否为周一如果是，则将其添加到结果列表中
 * 这个函数有助于获取特定月份中每个星期的第一天，以便进行日历相关的操作或分析
 *
 * @return 一个包含当前月份所有周一日期的可变列表
 */
fun LocalDateTime.getWeekFirstDayInMonth(): MutableList<LocalDateTime> {
    // 将时间定位到当月第一天
    var startMonth = startMonth()
    // 获取当前月份的编号，用于后续判断是否超出当前月份
    val currentMonth = startMonth.monthNumber
    // 创建一个可变列表来存储所有的周一日期
    val mondays = mutableListOf<LocalDateTime>()
    // 遍历整个月份的每一天
    while (startMonth.monthNumber == currentMonth) {
        // 如果当前天是周一，则将其添加到列表中
        if (startMonth.dayOfWeek == DayOfWeek.MONDAY) {
            mondays.add(LocalDateTime.now(startMonth.toEpochMilliseconds()))
        }
        // 将日期移动到下一天，继续检查
        startMonth = startMonth.add(1, DateTimeUnit.DAY)
    }
    // 返回包含所有周一日期的列表
    return mondays
}


/**
 * 计算当前月份包含的周数
 * 此方法考虑了月份的起始日和结束日的星期，以及月份的总天数，以准确计算周数
 * 周的开始被假设为周一，这是根据大多数日历的表示方式
 *
 * @return 当前月份的周数
 */
fun LocalDateTime.countWeeksInMonth(): Int {
    // 设置为月初第一天并获取其星期几
    val firstDayOfWeek = startMonth().dayOfWeek
    // 获取月末最后一天并获取其星期几
    val lastDayOfWeek = endMonth().dayOfWeek
    // 月份总天数
    val totalDays = endMonth().day()
    // 假设周从周一开始（DAY_OF_WEEK 中 Monday=1）
    val adjustedFirst =
        if (firstDayOfWeek == DayOfWeek.MONDAY) 7 else firstDayOfWeek.isoDayNumber // Sunday -> 7
    val adjustedLast = if (lastDayOfWeek == DayOfWeek.MONDAY) 7 else lastDayOfWeek.isoDayNumber
    // 计算完整周数：(首日偏移 + 总天数 + 末日偏移 - 首日偏移) / 7 向上取整
    return ((adjustedFirst + totalDays + (7 - adjustedLast)) + 6) / 7
}


/**
 * 获取某月的日历周数
 *
 * 此函数计算给定月份在日历中显示的周数通常，一个月会跨越多周，即使该月的第一天不是周一，
 * 也会从包含第一天的那一周开始计算，直到月中的最后一个周日（或根据地区设置的周的最后一天）
 *
 * @return 返回给定月份的日历周数
 */
fun LocalDateTime.countWeeksMondayInMonth(): Int {
    // 获取月份第一天的星期
    val firstDayOfWeek = startMonth().dayOfWeek
    // 月份总天数
    val totalDays = endMonth().day()
    // 调整第一周的起始日，如果第一天是周日，则设为7，否则为第一天的isoDayNumber减1
    val adjustedFirstDay =
        if (firstDayOfWeek == DayOfWeek.SUNDAY) 7 else firstDayOfWeek.isoDayNumber - 1
    // 计算周数，考虑调整后的起始日
    return (totalDays + adjustedFirstDay - 1) / 7
}

