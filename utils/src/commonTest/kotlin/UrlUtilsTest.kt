import hos.utils.*
import kotlin.test.Test

/**
 * <p>Title: UrlUtilsTest </p>
 * <p>Description: 用于测试URL相关工具类逻辑 </p>
 * <p>Company: www.hos.com</p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-15 17:30
 * @version : 1.0
 */
class UrlUtilsTest {

    @Test
    fun main() {
        println("=== 测试 url(target, value) ===")
        testUrl("https://www.baidu.com", "default", "https://www.baidu.com")
        testUrl("https://www.baidu.com?param=1", "default", "https://www.baidu.com")
        testUrl("", "default", "default")
        testUrl(null, "default", "default")
        testUrl("   ", "default", "default")
        testUrl("mapuni.com", "default", "mapuni.com")

        println("\n=== 测试 splitHttp(target, isHttp) ===")
        testSplitHttp("www.baidu.com", true, "http://www.baidu.com")
        testSplitHttp("www.baidu.com", false, "https://www.baidu.com")
        testSplitHttp("http://www.baidu.com", true, "http://www.baidu.com")
        testSplitHttp("https://www.baidu.com", false, "https://www.baidu.com")
        testSplitHttp("", true, "")
        testSplitHttp(null, false, "")
        testSplitHttp("   ", false, "")

        println("\n=== 测试 params(target) ===")
        testParams("key1=value1&key2=value2", mapOf("key1" to "value1", "key2" to "value2"))
        testParams("key1=value1", mapOf("key1" to "value1"))
        testParams("key1=&key2=value2", mapOf("key1" to "", "key2" to "value2"))
        testParams("key1", mapOf("key1" to ""))
        testParams("key1=value1=value2", mapOf("key1" to "value1=value2"))
        testParams("", emptyMap())
        testParams(null, emptyMap())
        testParams("   ", emptyMap())

        println("\n=== 测试带参数的URL解析 ===")
        testParams("https://www.baidu.com?key1=value1&key2=value2", mapOf("key1" to "value1", "key2" to "value2"))
        testParams("https://www.baidu.com?singleKey", mapOf("singleKey" to ""))
        testParams("https://www.baidu.com?k1=v1&k2&k3=v3", mapOf("k1" to "v1", "k2" to "", "k3" to "v3"))
    }

    private fun testUrl(target: String?, value: String, expected: String) {
        val result = UrlUtils.url(target, value)
        println("url(\"$target\", \"$value\"): $result (期望: $expected)")
    }

    private fun testSplitHttp(target: String?, isHttp: Boolean, expected: String) {
        val result = UrlUtils.splitHttp(target, isHttp)
        println("splitHttp(\"$target\", $isHttp): $result (期望: $expected)")
    }

    private fun testParams(target: String?, expected: Map<String, String>) {
        val result = UrlUtils.params(target)
        println("params(\"$target\"): $result (期望: $expected)")
    }
}
