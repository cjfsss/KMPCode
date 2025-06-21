import hos.ext.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

/**
 * <p>Title: TypeTest </p>
 * <p>Description: 用于测试通用类型的扩展函数 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 17:00
 * @version : 1.0
 */
class TypeTest {

    @Test
    fun main() {
        // 测试变量定义
        val str1: String? = "value"
        val str2: String? = null
        val num1: Int? = 123
        val num2: Int? = null
        val obj1: Any? = Any()
        val obj2: Any? = null

        // convert()
        println("convert (str1): ${str1.convert { this?.let { uppercase() } ?: "default" }}")
        println("convert (str2): ${str2.convert { this?.let { uppercase() } ?: "default" }}")
        println("convert (num1): ${num1.convert { this?.times(2) ?: -1 }}")
        println("convert (num2): ${num2.convert { this?.times(2) ?: -1 }}")

        // getOrDefault()
        println("getOrDefault (str1): ${str1.getOrDefault("default")}")
        println("getOrDefault (str2): ${str2.getOrDefault("default")}")
        println("getOrDefault (num1): ${num1.getOrDefault(-1)}")
        println("getOrDefault (num2): ${num2.getOrDefault(-1)}")

        // requireNonNull()
        try {
            println("requireNonNull (obj1): ${obj1.requireNonNull()}")
        } catch (e: NullPointerException) {
            println("requireNonNull (obj1) exception: ${e.message}")
        }

        try {
            println("requireNonNull (obj2): ${obj2.requireNonNull()}")
        } catch (e: NullPointerException) {
            println("requireNonNull (obj2) exception: ${e.message}")
        }

        // requireNonNull(message)
        try {
            println("requireNonNull (obj2, custom message): ${obj2.requireNonNull("对象不能为 null")}")
        } catch (e: NullPointerException) {
            println("requireNonNull (obj2, message) exception: ${e.message}")
        }
    }
}
