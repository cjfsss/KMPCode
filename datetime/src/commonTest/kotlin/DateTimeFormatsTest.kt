import hos.datetime.DateTimeFormats
import kotlin.test.Test

/**
 * <p>Title: DateTimeFormatsTest </p>
 * <p>Description: 用于测试日期时间格式解析和转换逻辑 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-14 10:00
 * @version : 1.0
 */
class DateTimeFormatsTest {

    @Test
    fun main() {
        // 测试格式常量
        println("=== 测试 DateTimeFormats 常量 ===")
        testFormatConstants()

        // 测试 getFormatString
        println("\n=== 测试 getFormatString ===")
        testGetFormatString()

        // 测试 format
        println("\n=== 测试 format ===")
        testFormat()

        // 测试 parseDateTimeComponents
        println("\n=== 测试 parseDateTimeComponents ===")
        testParseDateTimeComponents()

        // 测试 parseLocalDateTime
        println("\n=== 测试 parseLocalDateTime ===")
        testParseLocalDateTime()

        // 测试 parse2Millis
        println("\n=== 测试 parse2Millis ===")
        testParse2Millis()

        // 测试 parseConvert
        println("\n=== 测试 parseConvert ===")
        testParseConvert()
    }

    private fun testFormatConstants() {
        println("FULL_S.pattern: ${DateTimeFormats.FULL_S.pattern} (期望: yyyyMMddHHmmss)")
        println("FULL_S.full: ${DateTimeFormats.FULL_S.full} (期望: 空字符串)")

        println("ZH_FULL.pattern: ${DateTimeFormats.ZH_FULL.pattern} (期望: yyyy年MM月dd日 HH时mm分ss秒)")
        println("IOS_FULL.pattern: ${DateTimeFormats.IOS_FULL.pattern} (期望: yyyy/MM/dd HH:mm:ss)")
    }

    private fun testGetFormatString() {
        // 正常情况测试
        testGetFormatStringCase("2023-06-15 12:30:45", DateTimeFormats.FULL)
        testGetFormatStringCase("2023/06/15 12:30:45", DateTimeFormats.IOS_FULL)
        testGetFormatStringCase("2023年06月15日 12时30分45秒", DateTimeFormats.ZH_FULL)
        testGetFormatStringCase("2023-06-15 12:30", DateTimeFormats.Y_MO_D_H_M)
        testGetFormatStringCase("2023/06/15 12:30", DateTimeFormats.IOS_Y_MO_D_H_M)
        testGetFormatStringCase("2023年06月15日 12时30分", DateTimeFormats.ZH_Y_MO_D_H_M)
        testGetFormatStringCase("2023-06-15 12", DateTimeFormats.Y_MO_D_H)
        testGetFormatStringCase("2023/06/15 12", DateTimeFormats.IOS_Y_MO_D_H)
        testGetFormatStringCase("2023年06月15日 12时", DateTimeFormats.ZH_Y_MO_D_H)
        testGetFormatStringCase("2023-06-15", DateTimeFormats.Y_MO_D)
        testGetFormatStringCase("2023/06/15", DateTimeFormats.IOS_Y_MO_D)
        testGetFormatStringCase("2023年06月15日", DateTimeFormats.ZH_Y_MO_D)
        testGetFormatStringCase("2023-06", DateTimeFormats.Y_MO)
        testGetFormatStringCase("2023/06", DateTimeFormats.IOS_Y_MO)
        testGetFormatStringCase("2023年06月", DateTimeFormats.ZH_Y_MO)
        testGetFormatStringCase("2023", DateTimeFormats.Y)

        // 特殊情况测试
        testGetFormatStringCase("2023年", DateTimeFormats.ZH_Y)
        testGetFormatStringCase("20230615123045", DateTimeFormats.FULL_S)
        testGetFormatStringCase("2023-06-15T12:30:45+08:00", DateTimeFormats.FULL)
        testGetFormatStringCase("2023-06-15T12:30:45.123", DateTimeFormats.FULL)

        // 错误情况测试
        testGetFormatStringCase("", null)
        testGetFormatStringCase("null", null)
        testGetFormatStringCase("invalid_date", null)
    }

    private fun testGetFormatStringCase(input: String, expected: DateTimeFormats?) {
        val result = DateTimeFormats.getFormatString(input)
        val expectedName = expected?.name ?: "null"
        println("getFormatString(\"$input\"): ${result?.name ?: "null"} (期望: $expectedName)")
    }

    private fun testFormat() {
        // 正常情况测试
        testFormatCase("2023-06-15 12:30:45", "2023-06-15 12:30:45")
        testFormatCase("2023/06/15 12:30:45", "2023/06/15 12:30:45")
        testFormatCase("2023年06月15日 12时30分45秒", "2023年06月15日 12时30分45秒")
        testFormatCase("2023-06-15T12:30:45+08:00", "2023-06-15 12:30:45")
        testFormatCase("2023-06-15T12:30:45.123", "2023-06-15 12:30:45")

        // 空值测试
        testFormatCase(null, "")
        testFormatCase("", "")
        testFormatCase("null", "")

        // 特殊字符处理
        testFormatCase("2023-06-15+12:30:45", "2023-06-15")
        testFormatCase("2023/06/15+12:30:45", "2023/06/15")
        testFormatCase("2023年06月15日+12时30分45秒", "2023年06月15日")
    }

    private fun testFormatCase(input: String?, expected: String) {
        val result = DateTimeFormats.format(input)
        println("format(\"$input\"): \"$result\" (期望: \"$expected\")")
    }

    private fun testParseDateTimeComponents() {
        // 正常情况测试
        testParseDateTimeComponentsCase("2023-06-15 12:30:45", DateTimeFormats.FULL, "2023-06-15T12:30:45")
        testParseDateTimeComponentsCase("2023/06/15 12:30:45", DateTimeFormats.IOS_FULL, "2023-06-15T12:30:45")
        testParseDateTimeComponentsCase("2023年06月15日 12时30分45秒", DateTimeFormats.ZH_FULL, "2023-06-15T12:30:45")
        testParseDateTimeComponentsCase("2023-06-15 12:30", DateTimeFormats.Y_MO_D_H_M, "2023-06-15T12:30:00")
        testParseDateTimeComponentsCase("2023/06/15 12:30", DateTimeFormats.IOS_Y_MO_D_H_M, "2023-06-15T12:30:00")
        testParseDateTimeComponentsCase("2023年06月15日 12时30分", DateTimeFormats.ZH_Y_MO_D_H_M, "2023-06-15T12:30:00")
        testParseDateTimeComponentsCase("2023-06-15", DateTimeFormats.Y_MO_D, "2023-06-15T00:00:00")
        testParseDateTimeComponentsCase("2023/06/15", DateTimeFormats.IOS_Y_MO_D, "2023-06-15T00:00:00")
        testParseDateTimeComponentsCase("2023年06月15日", DateTimeFormats.ZH_Y_MO_D, "2023-06-15T00:00:00")
        testParseDateTimeComponentsCase("2023-06", DateTimeFormats.Y_MO, "2023-06-01T00:00:00")
        testParseDateTimeComponentsCase("2023/06", DateTimeFormats.IOS_Y_MO, "2023-06-01T00:00:00")
        testParseDateTimeComponentsCase("2023年06月", DateTimeFormats.ZH_Y_MO, "2023-06-01T00:00:00")
        testParseDateTimeComponentsCase("2023", DateTimeFormats.Y, "2023-01-01T00:00:00")

        // 错误情况测试
        testParseDateTimeComponentsErrorCase("invalid_date", null)
        testParseDateTimeComponentsErrorCase("", null)
        testParseDateTimeComponentsErrorCase("null", null)
    }

    private fun testParseDateTimeComponentsCase(input: String, format: DateTimeFormats, expected: String) {
        try {
            val components = DateTimeFormats.parseDateTimeComponents(input, format)
            val result = components.toLocalDateTime().toString()
            println("parseDateTimeComponents(\"$input\", ${format.name}): \"$result\" (期望: \"$expected\")")
        } catch (e: Exception) {
            e.printStackTrace()
            println("parseDateTimeComponents(\"$input\", ${format.name}) 抛出异常 (期望: 成功)")
        }
    }

    private fun testParseDateTimeComponentsErrorCase(input: String, format: DateTimeFormats? = null) {
        try {
            val result = DateTimeFormats.parseDateTimeComponents(input, format)
            println("parseDateTimeComponents(\"$input\") 返回了结果 (期望: 抛出异常)")
        } catch (e: Exception) {
            println("parseDateTimeComponents(\"$input\") 抛出异常: \"${e.message}\" (期望: pattern is not null 或其他解析错误)")
        }
    }

    private fun testParseLocalDateTime() {
        // 正常情况测试
        testParseLocalDateTimeCase("2023-06-15 12:30:45", DateTimeFormats.FULL, "2023-06-15T12:30:45")
        testParseLocalDateTimeCase("2023/06/15 12:30:45", DateTimeFormats.IOS_FULL, "2023-06-15T12:30:45")
        testParseLocalDateTimeCase("2023年06月15日 12时30分45秒", DateTimeFormats.ZH_FULL, "2023-06-15T12:30:45")
        testParseLocalDateTimeCase("2023-06-15 12:30", DateTimeFormats.Y_MO_D_H_M, "2023-06-15T12:30:00")
        testParseLocalDateTimeCase("2023/06/15 12:30", DateTimeFormats.IOS_Y_MO_D_H_M, "2023-06-15T12:30:00")
        testParseLocalDateTimeCase("2023年06月15日 12时30分", DateTimeFormats.ZH_Y_MO_D_H_M, "2023-06-15T12:30:00")
        testParseLocalDateTimeCase("2023-06-15", DateTimeFormats.Y_MO_D, "2023-06-15T00:00:00")
        testParseLocalDateTimeCase("2023/06/15", DateTimeFormats.IOS_Y_MO_D, "2023-06-15T00:00:00")
        testParseLocalDateTimeCase("2023年06月15日", DateTimeFormats.ZH_Y_MO_D, "2023-06-15T00:00:00")
        testParseLocalDateTimeCase("2023-06", DateTimeFormats.Y_MO, "2023-06-01T00:00:00")
        testParseLocalDateTimeCase("2023/06", DateTimeFormats.IOS_Y_MO, "2023-06-01T00:00:00")
        testParseLocalDateTimeCase("2023年06月", DateTimeFormats.ZH_Y_MO, "2023-06-01T00:00:00")
        testParseLocalDateTimeCase("2023", DateTimeFormats.Y, "2023-01-01T00:00:00")
    }

    private fun testParseLocalDateTimeCase(input: String, format: DateTimeFormats, expected: String) {
        try {
            val dateTime = DateTimeFormats.parseLocalDateTime(input, format)
            val result = dateTime.toString()
            println("parseLocalDateTime(\"$input\", ${format.name}): \"$result\" (期望: \"$expected\")")
        } catch (e: Exception) {
            e.printStackTrace()
            println("parseLocalDateTime(\"$input\", ${format.name}) 抛出异常 (期望: 成功)")
        }
    }

    private fun testParse2Millis() {
        // 正常情况测试
        testParse2MillisCase("2023-06-15 12:30:45", DateTimeFormats.FULL, 1686833445000L)
        testParse2MillisCase("2023/06/15 12:30:45", DateTimeFormats.IOS_FULL, 1686833445000L)
        testParse2MillisCase("2023年06月15日 12时30分45秒", DateTimeFormats.ZH_FULL, 1686833445000L)
        testParse2MillisCase("2023-06-15 12:30", DateTimeFormats.Y_MO_D_H_M, 1686833400000L)
        testParse2MillisCase("2023/06/15 12:30", DateTimeFormats.IOS_Y_MO_D_H_M, 1686833400000L)
        testParse2MillisCase("2023年06月15日 12时30分", DateTimeFormats.ZH_Y_MO_D_H_M, 1686833400000L)
        testParse2MillisCase("2023-06-15", DateTimeFormats.Y_MO_D, 1686748800000L)
        testParse2MillisCase("2023/06/15", DateTimeFormats.IOS_Y_MO_D, 1686748800000L)
        testParse2MillisCase("2023年06月15日", DateTimeFormats.ZH_Y_MO_D, 1686748800000L)

        // 错误情况测试
        testParse2MillisCase("invalid_date", null, -1L)
        testParse2MillisCase("", null, -1L)
        testParse2MillisCase("null", null, -1L)
    }

    private fun testParse2MillisCase(input: String, format: DateTimeFormats? = DateTimeFormats.getFormatString(input), expected: Long) {
        val millis = DateTimeFormats.parse2Millis(input, format)
        println("parse2Millis(\"$input\", ${if (format != null) format.name else "自动检测"}): $millis (期望: $expected)")
    }

    private fun testParseConvert() {
        // 从完整格式转换
        testParseConvertCase("2023-06-15 12:30:45", DateTimeFormats.FULL, DateTimeFormats.ZH_FULL, "2023年06月15日 12时30分45秒")
        testParseConvertCase("2023-06-15 12:30:45", DateTimeFormats.FULL, DateTimeFormats.IOS_FULL, "2023/06/15 12:30:45")
        testParseConvertCase("2023-06-15 12:30:45", DateTimeFormats.FULL, DateTimeFormats.Y_MO_D, "2023-06-15")

        // 从中文格式转换
        testParseConvertCase("2023年06月15日 12时30分45秒", DateTimeFormats.ZH_FULL, DateTimeFormats.FULL, "2023-06-15 12:30:45")
        testParseConvertCase("2023年06月15日 12时30分", DateTimeFormats.ZH_Y_MO_D_H_M, DateTimeFormats.IOS_FULL, "2023/06/15 12:30:00")

        // 从iOS格式转换
        testParseConvertCase("2023/06/15 12:30:45", DateTimeFormats.IOS_FULL, DateTimeFormats.ZH_Y_MO_D, "2023年06月15日")

        // 错误情况测试
        testParseConvertCase("invalid_date", null, DateTimeFormats.FULL, "")
        testParseConvertCase("", null, DateTimeFormats.FULL, "")
    }

    private fun testParseConvertCase(input: String, from: DateTimeFormats? = DateTimeFormats.getFormatString(input), to: DateTimeFormats, expected: String) {
        val result = DateTimeFormats.parseConvert(input, from, to)
        println("parseConvert(\"$input\", ${from?.name ?: "自动检测"}, ${to.name}): \"$result\" (期望: \"$expected\")")
    }
}
