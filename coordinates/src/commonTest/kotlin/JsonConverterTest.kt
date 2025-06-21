import hos.json.JsonConverter
import kotlin.test.Test

/**
 * <p>Title: JsonConverterTest </p>
 * <p>Description: 用于测试 JsonConverter 工具类的 JSON 转换功能 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-15 23:00
 * @version : 1.0
 */
class JsonConverterTest {

    @Test
    fun main() {
        println("=== 测试 toJson(Map<String, Any?>) ===")
        testMapConversion(
            mapOf(
                "name" to "张三",
                "age" to 28,
                "skills" to listOf("Kotlin", "Android"),
                "address" to mapOf("city" to "北京", "zip" to "100000"),
                "meta" to null
            ),
            "{\"name\":\"张三\",\"age\":28,\"skills\":[\"Kotlin\",\"Android\"],\"address\":{\"city\":\"北京\",\"zip\":\"100000\"},\"meta\":null}"
        )

        println("\n=== 测试 toJson(List<*>) ===")
        testListConversion(
            listOf("苹果", 123, mapOf("name" to "香蕉", "price" to 5.99), null, listOf("a", "b", "c")),
            "[\"苹果\",123,{\"name\":\"香蕉\",\"price\":5.99},null,[\"a\",\"b\",\"c\"]]"
        )

        println("\n=== 测试嵌套结构 ===")
        testNestedStructure()

        println("\n=== 测试空值处理 ===")
        testEmptyValues()
    }

    private fun testMapConversion(input: Map<*, *>, expected: String) {
        val result = JsonConverter.toJson(input)
        println("toJson(Map): $result (期望: $expected)")
        println("匹配: ${if (result == expected) "✅ 成功" else "❌ 失败"}")
    }

    private fun testListConversion(input: List<*>, expected: String) {
        val result = JsonConverter.toJson(input)
        println("toJson(List): $result (期望: $expected)")
        println("匹配: ${if (result == expected) "✅ 成功" else "❌ 失败"}")
    }

    private fun testNestedStructure() {
        val nested = mapOf(
            "user" to mapOf(
                "id" to 1,
                "tags" to listOf("dev", "android", "kotlin"),
                "active" to true
            ),
            "locations" to listOf(
                mapOf("lat" to 39.9042, "lng" to 116.4074),
                mapOf("lat" to 31.2304, "lng" to 121.4737)
            )
        )
        val expected = "{\"user\":{\"id\":1,\"tags\":[\"dev\",\"android\",\"kotlin\"],\"active\":true},\"locations\":[{\"lat\":39.9042,\"lng\":116.4074},{\"lat\":31.2304,\"lng\":121.4737}]}"
        val result = JsonConverter.toJson(nested)
        println("嵌套结构 toJson(): $result")
        println("匹配: ${if (result == expected) "✅ 成功" else "❌ 失败"}")
    }

    private fun testEmptyValues() {
        testMapConversion(emptyMap<String,String>(), "{}")
        testListConversion(emptyList<String>(), "[]")
        testMapConversion(mapOf("nullKey" to null), "{\"nullKey\":null}")
    }
}
