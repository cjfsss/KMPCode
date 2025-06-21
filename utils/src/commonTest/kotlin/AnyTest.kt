import hos.ext.*
import kotlin.test.Test

/**
 * <p>Title: AnyExtTest </p>
 * <p>Description: 用于测试通用对象扩展函数 isNull 和 isNotNull </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-14 17:30
 * @version : 1.0
 */
class AnyTest {

    @Test
    fun main() {
        println("=== 测试 null 对象 ===")
        testIsNull(null, true)
        testIsNotNull(null, false)

        println("\n=== 测试 CharSequence / String 类型 ===")
        testIsNull("", true)
        testIsNull(" ", true)
        testIsNull("null", true)
        testIsNull("NULL", true)
        testIsNull("Null", true)
        testIsNull("abc", false)
        testIsNull("  abc  ", false)

        println("\n=== 测试 Array 类型 ===")
        testIsNull(emptyArray<String>(), true)
        testIsNull(arrayOf("a", "b"), false)

        println("\n=== 测试 Collection 类型 ===")
        testIsNull(emptyList<String>(), true)
        testIsNull(listOf("a", "b"), false)
        testIsNull(emptySet<String>(), true)
        testIsNull(setOf("a", "b"), false)

        println("\n=== 测试 Map 类型 ===")
        testIsNull(emptyMap<String, String>(), true)
        testIsNull(mapOf("key" to "value"), false)

        println("\n=== 测试 isNotNull ===")
        testIsNotNull("非空字符串", true)
        testIsNotNull("", false)
        testIsNotNull(null, false)
        testIsNotNull(listOf("a"), true)
        testIsNotNull(emptyList<String>(), false)
        testIsNotNull(mapOf("key" to "value"), true)
        testIsNotNull(emptyMap<String, String>(), false)
    }

    private fun testIsNull(value: Any?, expected: Boolean) {
        val result = value.isNull()
        println("isNull($value): $result (期望: $expected)")
    }

    private fun testIsNotNull(value: Any?, expected: Boolean) {
        val result = value.isNotNull()
        println("isNotNull($value): $result (期望: $expected)")
    }
}
