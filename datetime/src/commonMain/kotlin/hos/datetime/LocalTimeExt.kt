package hos.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

/**
 * <p>Title: LocalTimePlus </p>
 * <p>Description: LocalTime扩展 </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-05-27 21:21
 * @version : 1.0
 */


/**
 * 创建LocalTime对象的伴生对象实例
 *
 * @param nanosecondOfDay 一天中的纳秒数，用于设置时间
 * @return LocalTime对象
 */
fun LocalTime.Companion.now(nanosecondOfDay: Long = 0L): LocalTime {
    if (nanosecondOfDay == 0L) {
        return fromNanosecondOfDay(Clock.System.now().toEpochMilliseconds())
    }
    return fromNanosecondOfDay(nanosecondOfDay)
}


/**
 * 格式化LocalTime对象为字符串
 *
 * @param pattern 时间格式模式
 * @return 格式化后的时间字符串
 */
@FormatStringsInDatetimeFormats
fun LocalTime.format(pattern: DateTimeFormats = DateTimeFormats.FULL): String {
    val dateTimeFormat = LocalTime.Format {
        byUnicodePattern(pattern.pattern)
    }
    return format(dateTimeFormat)
}

/**
 * 从指定毫秒值的LocalTime对象中获取内容
 *
 * 此函数通过lambda表达式从LocalTime对象中获取某些信息或执行某些操作，并返回结果
 * 它通过 Companion 对象的 getInstance 方法获取一个 LocalTime 实例，并在该实例上调用 lambda 表达式
 *
 * @param <T> lambda表达式返回值的类型
 * @param millis 指定的时间毫秒值，默认为0
 * @param content 一个lambda表达式，定义了从指定LocalTime对象中获取内容的操作
 * @return lambda表达式的返回值，类型为T
 */
fun <T> LocalTime.Companion.getContent(
    millis: Long = 0,
    content: LocalTime.() -> T
): T {
    return content.invoke(now(millis))
}


/**
 * 设置LocalTime对象的特定时间单位值
 *
 * @param value 要设置的时间单位值
 * @param unit  时间单位，决定哪个时间部分会被更新
 * @return 更新后的LocalTime对象
 * @throws IllegalArgumentException 如果提供的单位不支持，则抛出此异常
 */
fun LocalTime.set(value: Int, unit: DateTimeUnit.TimeBased): LocalTime {
    if (unit == DateTimeUnit.HOUR) {
        return LocalTime(value, minute, second)
    }
    if (unit == DateTimeUnit.MINUTE) {
        return LocalTime(hour, value, second)
    }
    if (unit == DateTimeUnit.SECOND) {
        return LocalTime(hour, minute, value)
    }
    if (unit == DateTimeUnit.MILLISECOND) {
        return LocalTime(hour, minute, second, value * 1000000)
    }
    if (unit == DateTimeUnit.MICROSECOND) {
        return LocalTime(hour, minute, second, value * 1000)
    }
    if (unit == DateTimeUnit.NANOSECOND) {
        return LocalTime(hour, minute, second, value)
    }
    throw IllegalArgumentException("不支持的参数")
}


/**
 * 返回一天的开始时间，即00:00:00
 *
 * @return 一天的开始时间
 */
fun LocalTime.startDay(): LocalTime {
    return LocalTime(0, 0, 0)
}

/**
 * 返回一天的结束时间，即23:59:59
 *
 * @return 一天的结束时间
 */
fun LocalTime.endDay(): LocalTime {
    return LocalTime(23, 59, 59)
}

