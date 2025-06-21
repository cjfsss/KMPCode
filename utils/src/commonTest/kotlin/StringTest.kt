import hos.ext.*
import kotlin.test.Test

/**
 * <p>Title: StringTest </p>
 * <p>Description: 用于测试 String 的扩展函数 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 14:00
 * @version : 1.0
 */
class StringTest {

    @Test
    fun main() {
        // 初始化一些测试字符串
        val str1 = "hello"
        val str2: String? = null
        val str3 = ""
        val str4 = "null"
        val str5 = "123"
        val str6 = "123.45"
        val str7 = "true"
        val str8 = "是"
        val str9 = "错误"
        val str10 = "2023-01-01T12:00:00+08:00"
        val str11 = "中文字符"

        // empty()
        println("empty (str1): ${str1.empty()}")
        println("empty (str2): ${str2.empty("default")}")
        println("empty (str3): ${str3.empty("default")}")

        // like()
        println("like (str1): ${str1.like()}")
        println("like (str3): ${str3.like("custom")}")

        // isInt() / isDouble() / isBool()
        println("isInt (str5): ${str5.isInt()}")
        println("isInt (str6): ${str6.isInt()}")
        println("isDouble (str6): ${str6.isDouble()}")
        println("isBool (str7): ${str7.isBool()}")
        println("isBool (str9): ${str9.isBool()}")

        // asBool()
        println("asBool (str7): ${str7.asBool()}")
        println("asBool (str9): ${str9.asBool()}")
        println("asBool (str1): ${str1.asBool(true)}")
        println("asBool (str3): ${str3.asBool(true)}")

        // asInt()
        println("asInt (str5): ${str5.asInt(0)}")
        println("asInt (str7): ${str7.asInt(0)}")
        println("asInt (str3): ${str3.asInt(100)}")

        // asDouble()
        println("asDouble (str6): ${str6.asDouble(0.0)}")
        println("asDouble (str3): ${str3.asDouble(5.5)}")

        // asNum()
        println("asNum (str5): ${str5.asNum(0)}")
        println("asNum (str6): ${str6.asNum(0.0)}")
        println("asNum (str8): ${str8.asNum(0)}")
        println("asNum (str9): ${str9.asNum(0)}")

        // includes()
        println("includes (str1 contains 'ell'): ${str1.includes("ell")}")
        println("includes (str1 contains 'ELL' ignore case): ${str1.includes("ELL", true)}")
        println("includes (str3 contains 'abc'): ${str3.includes("abc")}")

        // subStr()
        println("subStr (str1, start=1, count=3): ${str1.subStr(1, 3)}")
        println("subStr (str3, start=0, count=2): ${str3.subStr(0, 2)}")

        // left(), right()
        println("left (str1, len=3): ${str1.left(3)}")
        println("right (str1, len=3): ${str1.right(3)}")
        println("left (str3, len=2): ${str3.left(2)}")
        println("right (str3, len=2): ${str3.right(2)}")

        // fontLength()
        println("fontLength (str11): ${str11.fontLength()}")
        println("fontLength (str1): ${str1.fontLength()}")
        println("fontLength (str3): ${str3.fontLength()}")

        // time()
        println("time (str10): ${str10.time()}")
        println("time (str3): ${str3.time(str10)}")
    }
}
