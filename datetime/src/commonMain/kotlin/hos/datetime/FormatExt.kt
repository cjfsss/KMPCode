package hos.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.DateTimeComponents

/**
 * <p>Title: DateTimeComponentsExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-19 14:24
 * @version : 1.0
 */
/**
 * 生成一个包含年、月、日、时、分、秒的格式化对象，无任何分隔符。
 */
fun DateTimeComponents.Companion.fullS() = Format {
    year();monthNumber();dayOfMonth();
    hour();minute();second();
}

/**
 * 生成一个包含年、月、日、时、分、秒的格式化对象，使用连字符和空格作为分隔符。
 */
fun DateTimeComponents.Companion.full() = Format {
    year();chars("-");monthNumber();chars("-");dayOfMonth();
    chars(" ");
    hour();chars(":");minute();chars(":");second();
}

/**
 * 生成一个包含年、月、日、时、分、秒的格式化对象，使用斜杠和空格作为分隔符，适用于iOS风格。
 */
fun DateTimeComponents.Companion.iosFull() = Format {
    year();chars("/");monthNumber();chars("/");dayOfMonth();
    chars(" ");
    hour();chars(":");minute();chars(":");second();
}

/**
 * 生成一个包含年、月、日、时、分、秒的格式化对象，使用中文字符作为分隔符，适用于中文环境。
 */
fun DateTimeComponents.Companion.zhFull() = Format {
    year();chars("年");monthNumber();chars("月");dayOfMonth();chars("日 ");
    hour();chars("时");minute();chars("分");second();chars("秒");
}

/**
 * 生成一个包含年、月、日、时、分、秒的格式化对象，无任何分隔符。
 */
fun LocalDateTime.Companion.fullS() = Format {
    year();monthNumber();dayOfMonth();
    hour();minute();second();
}

/**
 * 生成一个包含年、月、日、时、分、秒的格式化对象，使用连字符和空格作为分隔符。
 */
fun LocalDateTime.Companion.full() = Format {
    year();chars("-");monthNumber();chars("-");dayOfMonth();
    chars(" ");
    hour();chars(":");minute();chars(":");second();
}

/**
 * 生成一个包含年、月、日、时、分、秒的格式化对象，使用斜杠和空格作为分隔符，适用于iOS风格。
 */
fun LocalDateTime.Companion.iosFull() = Format {
    year();chars("/");monthNumber();chars("/");dayOfMonth();
    chars(" ");
    hour();chars(":");minute();chars(":");second();
}

/**
 * 生成一个包含年、月、日、时、分、秒的格式化对象，使用中文字符作为分隔符，适用于中文环境。
 */
fun LocalDateTime.Companion.zhFull() = Format {
    year();chars("年");monthNumber();chars("月");dayOfMonth();chars("日 ");
    hour();chars("时");minute();chars("分");second();chars("秒");
}

/**
 * 生成一个包含年、月、日的格式化对象，无任何分隔符。
 */
fun LocalDate.Companion.fullS() = Format {
    year();monthNumber();dayOfMonth();
}

/**
 * 生成一个包含年、月、日的格式化对象，使用连字符作为分隔符。
 */
fun LocalDate.Companion.full() = Format {
    year();chars("-");monthNumber();chars("-");dayOfMonth();
}

/**
 * 生成一个包含年、月、日的格式化对象，使用斜杠作为分隔符，适用于iOS风格。
 */
fun LocalDate.Companion.iosFull() = Format {
    year();chars("/");monthNumber();chars("/");dayOfMonth();
}

/**
 * 生成一个包含年、月、日的格式化对象，使用中文字符作为分隔符，适用于中文环境。
 */
fun LocalDate.Companion.zhFull() = Format {
    year();chars("年");monthNumber();chars("月");dayOfMonth();chars("日");
}

/**
 * 生成一个包含时、分、秒的格式化对象，无任何分隔符。
 */
fun LocalTime.Companion.fullS() = Format {
    hour();minute();second();
}

/**
 * 生成一个包含时、分、秒的格式化对象，使用冒号作为分隔符。
 */
fun LocalTime.Companion.full() = Format {
    hour();chars(":");minute();chars(":");second();
}

/**
 * 生成一个包含时、分、秒的格式化对象，使用冒号作为分隔符，适用于iOS风格。
 */
fun LocalTime.Companion.iosFull() = Format {
    hour();chars(":");minute();chars(":");second();
}

/**
 * 生成一个包含时、分、秒的格式化对象，使用中文字符作为分隔符，适用于中文环境。
 */
fun LocalTime.Companion.zhFull() = Format {
    hour();chars("时");minute();chars("分");second();chars("秒");
}
