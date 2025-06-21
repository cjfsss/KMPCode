import hos.ext.*
import kotlin.test.Test

/**
 * <p>Title: CollectionExtTest </p>
 * <p>Description: 用于测试集合扩展函数 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-14 18:00
 * @version : 1.0
 */
class CollectionTest {

    @Test
    fun main() {
        val list = listOf("apple", null, "banana", "null", "cherry")
        val emptyList: List<String>? = null
        val shortList = listOf("a", "b")

        println("=== 测试 isNull(index) ===")
        testIsNull(list, 0, false)
        testIsNull(list, 1, true)
        testIsNull(list, 3, true)
        testIsNull(list, 5, true)
        testIsNull(emptyList, 0, true)

        println("\n=== 测试 isNotNull(index) ===")
        testIsNotNull(list, 0, true)
        testIsNotNull(list, 1, false)
        testIsNotNull(list, 3, false)
        testIsNotNull(shortList, 1, true)
        testIsNotNull(shortList, 5, false)
        testIsNotNull(emptyList, 0, false)

        println("\n=== 测试 includes ===")
        testIncludes(list, "apple", true)
        testIncludes(list, null, true)
        testIncludes(list, "banana", true)
        testIncludes(list, "grape", false)
        testIncludes(emptyList, "apple", false)

        println("\n=== 测试 getOrDefault ===")
        testGetOrDefault(list, 0, "default", "apple")
        testGetOrDefault(list, 1, "default", "default")
        testGetOrDefault(list, 3, "default", "default")
        testGetOrDefault(list, 5, "default", "default")
        testGetOrDefault(emptyList, 0, "default", "default")

        println("\n=== 测试 empty ===")
        testEmpty(list, 0, "custom_default", "apple")
        testEmpty(list, 1, "custom_default", "custom_default")
        testEmpty(list, 3, "custom_default", "custom_default")
        testEmpty(list, 5, "custom_default", "custom_default")
        testEmpty(emptyList, 0, "custom_default", "custom_default")

        println("\n=== 测试 convert ===")
        testConvert(list, 0) { this?.let { uppercase() } ?: "converted_default" }
        testConvert(list, 1) { this?.let { uppercase() } ?: "converted_default" }
        testConvert(list, 3) { this?.let { uppercase() } ?: "converted_default" }
        testConvert(list, 5) { this?.let { uppercase() } ?: "converted_default" }
        testConvert(emptyList, 0) { this?.let { uppercase() } ?: "converted_default" }
    }

    private fun testIsNull(collection: Collection<*>?, index: Int, expected: Boolean) {
        val result = collection.isNull(index)
        println("collection.isNull($index): $result (期望: $expected)")
    }

    private fun testIsNotNull(collection: Collection<*>?, index: Int, expected: Boolean) {
        val result = collection.isNotNull(index)
        println("collection.isNotNull($index): $result (期望: $expected)")
    }

    private fun testIncludes(collection: Collection<*>?, item: Any?, expected: Boolean) {
        val result = collection?.includes(item)
        println("collection.includes($item): $result (期望: $expected)")
    }

    private fun testGetOrDefault(
        collection: Collection<*>?,
        index: Int,
        default: String,
        expected: Any?
    ) {
        val result = collection.getOrDefault<Any?>(index, default)
        println("collection.getOrDefault($index, $default): $result (期望: $expected)")
    }

    private fun testEmpty(
        collection: Collection<*>?,
        index: Int,
        default: String,
        expected: Any?
    ) {
        val result = collection.empty<Any?>(index, default)
        println("collection.empty($index, $default): $result (期望: $expected)")
    }

    private fun testConvert(
        collection: Collection<String?>?,
        index: Int,
        convert: (String?.() -> String)
    ) {
        val result = collection.convert(index, {
            convert(this)
        })
        println("collection.convert($index): $result")
    }
}
