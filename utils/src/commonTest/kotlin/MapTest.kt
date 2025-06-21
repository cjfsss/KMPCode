import hos.ext.*
import kotlin.test.Test

/**
 * <p>Title: MapExtTest </p>
 * <p>Description: 用于测试 Map 的扩展函数 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-14 17:00
 * @version : 1.0
 */
class MapTest {

    @Test
    fun main() {
        // 初始化一个包含不同值的Map
        val map = mapOf(
            "key1" to "value1",
            "key2" to null,
            "key3" to 123,
            "key4" to "null",
            "key5" to listOf("a", "b", null)
        )

        println("=== 测试 Map 是否为 null 或空 ===")
        println("map.isNull(): ${map.isNull()} (期望: false)")
        println("空Map.isNull(): ${emptyMap<String, Any?>().isNull()} (期望: true)")

        println("\n=== 测试 Map 中键是否为 null ===")
        testIsNull(map, "key1", false)
        testIsNull(map, "key2", true)
        testIsNull(map, "key4", true)
        testIsNull(map, "key6", true)

        println("\n=== 测试 Map 中键是否非 null ===")
        testIsNotNull(map, "key1", true)
        testIsNotNull(map, "key2", false)
        testIsNotNull(map, "key4", false)
        testIsNotNull(map, "key6", false)

        println("\n=== 测试 getOrDefaultValue ===")
        testGetOrDefaultValue(map, "key1", "default_str", "value1")
        testGetOrDefaultValue(map, "key2", "default_str", "default_str")
        testGetOrDefaultValue(map, "key3", "default_str", 123)
        testGetOrDefaultValue(map, "key6", "default_str", "default_str")

        println("\n=== 测试 empty ===")
        testEmpty(map, "key1", "custom_default", "value1")
        testEmpty(map, "key2", "custom_default", "custom_default")
        testEmpty(map, "key4", "custom_default", "custom_default")
        testEmpty(map, "key6", "custom_default", "custom_default")

        println("\n=== 测试 convert ===")
        testConvert(map, "key1") { this?.toString() ?: "converted_default" }
        testConvert(map, "key2") { this?.toString() ?: "converted_default" }
        testConvert(map, "key3") { this?.toString() ?: "converted_default" }
        testConvert(map, "key6") { this?.toString() ?: "converted_default" }

        println("\n=== 测试 includes ===")
        testIncludes(map, "value1", true)
        testIncludes(map, null, true)
        testIncludes(map, 123, true)
        testIncludes(map, "not_exists", false)

        println("\n=== 测试 forEachValue ===")
        println("遍历 map 中的所有值：")
        map.forEachValue {
            println("值: $this")
        }

        println("\n=== 测试 where ===")
        val filteredValues = map.where { value -> value is String && value.contains("ue") }
        println("筛选出包含 'ue' 的字符串值: $filteredValues (期望: [value1])")
    }

    private fun testIsNull(map: Map<String, Any?>?, key: String, expected: Boolean) {
        val result = map.isNull(key)
        println("map.isNull(\"$key\"): $result (期望: $expected)")
    }

    private fun testIsNotNull(map: Map<String, Any?>?, key: String, expected: Boolean) {
        val result = map.isNotNull(key)
        println("map.isNotNull(\"$key\"): $result (期望: $expected)")
    }

    private fun testGetOrDefaultValue(
        map: Map<String, Any?>?,
        key: String,
        default: Any?,
        expected: Any?
    ) {
        val result = map.getOrDefaultValue(key, default)
        println("map.getOrDefaultValue(\"$key\", $default): $result (期望: $expected)")
    }

    private fun testEmpty(
        map: Map<String, Any?>?,
        key: String,
        default: Any?,
        expected: Any?
    ) {
        val result = map.empty(key, default)
        println("map.empty(\"$key\", $default): $result (期望: $expected)")
    }

    private fun testConvert(
        map: Map<String, Any?>?,
        key: String,
        convert: (Any?.() -> String)
    ) {
        val result = map.convert(key, convert)
        println("map.convert(\"$key\") -> $result")
    }

    private fun testIncludes(map: Map<String, Any?>?, value: Any?, expected: Boolean) {
        val result = map.includes(value)
        println("map.includes($value): $result (期望: $expected)")
    }
}
