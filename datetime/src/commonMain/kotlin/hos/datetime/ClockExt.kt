package hos.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * <p>Title: ClockExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-19 13:20
 * @version : 1.0
 */
/**
 * 将当前时间转换为指定时区的日期时间对象
 *
 * @param timeZone 用于转换的时区，默认为系统当前时区
 * @return 转换后的本地日期时间对象
 */
fun Clock.System.asDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return now().toLocalDateTime(timeZone)
}

/**
 * 获取当前时间的毫秒时间戳
 *
 * @return 当前时间的毫秒时间戳
 */
fun Clock.System.milliseconds(): Long {
    return now().toEpochMilliseconds()
}

/**
 * 在当前时间上下文中执行给定的内容函数
 *
 * @param content 在当前时间上下文中执行的函数，该函数接收一个Instant对象并返回任意类型T
 * @return 内容函数执行的结果
 */
fun <T> Clock.System.getContent(
    content: Instant.() -> T
): T {
    return content.invoke(now())
}
