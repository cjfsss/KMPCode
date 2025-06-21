import hos.utils.*
import kotlin.test.Test

/**
 * <p>Title: RegexUtilsTest </p>
 * <p>Description: 用于测试正则工具类逻辑 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-15 16:30
 * @version : 1.0
 */
class RegexUtilsTest {

    @Test
    fun main() {
        println("=== 测试 isValidLatitude ===")
        testIsValidLatitude("39.9042", true, true) // 北京
        testIsValidLatitude("-91", true, false)
        testIsValidLatitude("90.000", true, true)
        testIsValidLatitude("91", true, false)
        testIsValidLatitude("abc", true, false)
        testIsValidLatitude(null, true, false)

        println("\n=== 测试 isValidLongitude ===")
        testIsValidLongitude("116.4074", true, true) // 北京
        testIsValidLongitude("181", true, false)
        testIsValidLongitude("-180.000", true, false)
        testIsValidLongitude("72.004", true, true) // 中国边界
        testIsValidLongitude("135.05", true, true) // 中国边界
        testIsValidLongitude("invalid", true, false)
        testIsValidLongitude(null, true, false)

        println("\n=== 测试 checkPassword ===")
        testCheckPassword("Aa1!testpass", upperCase = true, lowerCase = true, digit = true, special = true, minLength = 8, expected = true)
        testCheckPassword("weakpass", upperCase = true, lowerCase = true, digit = false, special = false, expected = false)
        testCheckPassword("short1!", minLength = 8, expected = false)
        testCheckPassword("ValidPass123!", expected = true)

        println("\n=== 测试 isSocialNumber ===")
        testIsSocialNumber("91370105MA3TGY3Y7R", true)
        testIsSocialNumber("123456789012345678", false)
        testIsSocialNumber("ABCD12345678901234", false)
        testIsSocialNumber(null, false)

        println("\n=== 测试 isIP ===")
        testIsIP("192.168.0.1", true)
        testIsIP("256.0.0.1", false)
        testIsIP("2001:0db8:85a3:0000:0000:8a2e:0370:7334", false) // IPv6 不支持

        println("\n=== 测试 isDate ===")
        testIsDate("2024-02-29", true)
        testIsDate("2023-02-29", false)
        testIsDate("2023-13-01", false)
        testIsDate("2023-12-31", true)

        println("\n=== 测试 isUsername ===")
        testIsUsername("user_123", true)
        testIsUsername("用户名12121", true)
        testIsUsername("u", false)
        testIsUsername("long_username_over20_chars", false)

        println("\n=== 测试 isZh ===")
        testIsZh("中文", true)
        testIsZh("mixed123", false)
        testIsZh("English", false)

        println("\n=== 测试 isURL ===")
        testIsUrl("https://www.hos.com", true)
        testIsUrl("http://example.com/path?query=1", true)
        testIsUrl("not-a-url", false)

        println("\n=== 测试 isEmail ===")
        testIsEmail("test@example.com", true)
        testIsEmail("user.name+tag@sub.domain.co.uk", true)
        testIsEmail("bademail@", false)

        println("\n=== 测试 isIDCard18 ===")
        testIsIDCard18("110101199003072516", true)
        testIsIDCard18("11010119900307251X", true)
        testIsIDCard18("11010119900307251", false)

        println("\n=== 测试 isIDCard15 ===")
        testIsIDCard15("110101900307251", true)
        testIsIDCard15("1101019003072512", false)

        println("\n=== 测试 isTel ===")
        testIsTel("010-12345678", true)
        testIsTel("021 12345678", true)
        testIsTel("1234567890", false)
    }

    private fun testIsValidLatitude(lat: String?, zh: Boolean, expected: Boolean) {
        val result = RegexUtils.isValidLatitude(lat, zh)
        println("isValidLatitude(\"$lat\", $zh): $result (期望: $expected)")
    }

    private fun testIsValidLongitude(lng: String?, zh: Boolean, expected: Boolean) {
        val result = RegexUtils.isValidLongitude(lng, zh)
        println("isValidLongitude(\"$lng\", $zh): $result (期望: $expected)")
    }

    private fun testCheckPassword(
        password: String?,
        upperCase: Boolean = false,
        lowerCase: Boolean = false,
        digit: Boolean = false,
        special: Boolean = false,
        minLength: Int = 8,
        expected: Boolean
    ) {
        val result = RegexUtils.checkPassword(password, upperCase, lowerCase, false, digit, special, minLength, 30)
        println("checkPassword(\"$password\", upperCase=$upperCase, lowerCase=$lowerCase, digit=$digit, special=$special): $result (期望: $expected)")
    }

    private fun testIsSocialNumber(value: String?, expected: Boolean) {
        val result = RegexUtils.isSocialNumber(value)
        println("isSocialNumber(\"$value\"): $result (期望: $expected)")
    }

    private fun testIsIP(input: String?, expected: Boolean) {
        val result = RegexUtils.isIP(input)
        println("isIP(\"$input\"): $result (期望: $expected)")
    }

    private fun testIsDate(input: String?, expected: Boolean) {
        val result = RegexUtils.isDate(input)
        println("isDate(\"$input\"): $result (期望: $expected)")
    }

    private fun testIsUsername(input: String?, expected: Boolean) {
        val result = RegexUtils.isUsername(input)
        println("isUsername(\"$input\"): $result (期望: $expected)")
    }

    private fun testIsZh(input: String?, expected: Boolean) {
        val result = RegexUtils.isZh(input)
        println("isZh(\"$input\"): $result (期望: $expected)")
    }

    private fun testIsUrl(input: String?, expected: Boolean) {
        val result = RegexUtils.isURL(input)
        println("isURL(\"$input\"): $result (期望: $expected)")
    }

    private fun testIsEmail(input: String?, expected: Boolean) {
        val result = RegexUtils.isEmail(input)
        println("isEmail(\"$input\"): $result (期望: $expected)")
    }

    private fun testIsIDCard18(input: String?, expected: Boolean) {
        val result = RegexUtils.isIDCard18(input)
        println("isIDCard18(\"$input\"): $result (期望: $expected)")
    }

    private fun testIsIDCard15(input: String?, expected: Boolean) {
        val result = RegexUtils.isIDCard15(input)
        println("isIDCard15(\"$input\"): $result (期望: $expected)")
    }

    private fun testIsTel(input: String?, expected: Boolean) {
        val result = RegexUtils.isTel(input)
        println("isTel(\"$input\"): $result (期望: $expected)")
    }
}
