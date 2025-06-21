import hos.datetime.asLocalDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.time.Duration.Companion.hours

/**
 * <p>Title: InstantExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-19 13:32
 * @version : 1.0
 */
class InstantTest {

    @Test
    fun main() {
        val now = Clock.System.now()
        val localDateTime = now.asLocalDateTime()
        println("当前时间: $now")
        println("当前时间: $localDateTime")
        val hour5 =(localDateTime.toInstant(TimeZone.currentSystemDefault()) + 5.hours).toLocalDateTime(TimeZone.currentSystemDefault())
        println("5小时之后时间: $hour5")
        println("5小时之后时间: ${(now + 5.hours).toLocalDateTime(TimeZone.currentSystemDefault())}")
        val instant = Instant.parse("2024-03-08T12:56:23.123456789Z")
        println("解析的时刻: $instant")
        val formattedString: String = instant.toString()
        println("格式化后的字符串: $formattedString")
        val date = LocalDate(2024, Month.MARCH, 8)
    }
}