package hos.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * <p>Title: InstantExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-19 13:50
 * @version : 1.0
 */

/**
 * 将 Instant 实例转换为指定时区的 LocalDateTime 对象
 *
 * @param timeZone 指定时区，默认为系统当前时区
 * @return 转换后的 LocalDateTime 对象
 */
fun Instant.asLocalDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return toLocalDateTime(timeZone)
}

/**
 * 将 Instant 实例转换为指定时区的 LocalDate 对象
 *
 * @param timeZone 指定时区，默认为系统当前时区
 * @return 转换后的 LocalDate 对象
 */
fun Instant.asLocalDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return asLocalDateTime(timeZone).date
}

/**
 * 将 Instant 实例转换为指定时区的 LocalTime 对象
 *
 * @param timeZone 指定时区，默认为系统当前时区
 * @return 转换后的 LocalTime 对象
 */
fun Instant.asLocalTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return asLocalDateTime(timeZone).time
}

/**
 * 解析字符串并将其转换为 Instant 对象
 *
 * @param input 待解析的字符串
 * @param pattern 日期时间格式字符串，如果未提供，将根据输入字符串自动获取
 * @return 解析后的 Instant 对象
 * @throws IllegalArgumentException 如果提供的格式字符串为空
 */
fun Instant.Companion.parse(
    input: String,
    pattern: DateTimeFormats? = DateTimeFormats.getFormatString(input)
): Instant {
    if (pattern == null) {
        throw IllegalArgumentException("pattern is not null")
    }
    return parse(DateTimeFormats.format(input + pattern.full), pattern.components)
}

/**
 * 获取当前时间的内容
 *
 * @param content 一个 lambda 表达式，用于获取 Instant 对象的内容
 * @return lambda 表达式的返回值
 */
fun <T> Instant.Companion.getContent(
    content: Instant.() -> T
): T {
    return content.invoke(Clock.System.now())
}
