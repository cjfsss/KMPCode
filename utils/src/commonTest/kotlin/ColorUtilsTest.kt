import hos.utils.*
import kotlin.test.Test

/**
 * <p>Title: ColorUtilsTest </p>
 * <p>Description: 用于测试颜色相关工具函数逻辑 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-15 14:30
 * @version : 1.0
 */
@ExperimentalStdlibApi
class ColorUtilsTest {

    @Test
    fun main() {
        println("=== 测试 withAlpha ===")
        testWithAlpha(0xFF123456.toInt(), 0x88)
        testWithAlpha(0x00FFFFFF.toInt(), 0xFF)

        println("\n=== 测试 withOpacity ===")
        testWithOpacity(0xFF123456.toInt(), 1.0f)
        testWithOpacity(0xFF123456.toInt(), 0.5f)
        testWithOpacity(0xFF123456.toInt(), 0.0f)

        println("\n=== 测试 rgb ===")
        testRgb(0xFF123456.toInt(), "#123456")
        testRgb(0xFFFF0000.toInt(), "#ff0000")
        testRgb(0xFF00FF00.toInt(), "#00ff00")
        testRgb(0xFF0000FF.toInt(), "#0000ff")

        println("\n=== 测试 argb ===")
        testArgb(0xAABBCCDD.toInt(), "#aabbccdd")
        testArgb(0xFF123456.toInt(), "#ff123456")
        testArgb(0x88000000.toInt(), "#88000000")

        println("\n=== 测试 toColorInt ===")
        testToColorInt("#123456", "0xff123456")
        testToColorInt("#abcdef", "0xffabcdef")
        testToColorInt("#aabbccdd", "0xaabbccdd")
        testToColorInt("#ffffffff", "0xffffffff")
        testToColorInt("#ff0000", "0xffff0000")
        testToColorInt("#00ff00", "0xff00ff00")
        testToColorInt("#0000ff", "0xff0000ff")
    }

    private fun testWithAlpha(color: Int, alpha: Int) {
        val result = ColorUtils.withAlpha(color, alpha)
        println("withAlpha(0x${color.toHexString()}, $alpha): 0x${result.toHexString()} (期望: 0x${(color and 0x00ffffff or (alpha shl 24)).toHexString()})")
    }


    private fun testWithOpacity(color: Int, opacity: Float) {
        val result = ColorUtils.withOpacity(color, opacity)
        val expectedAlpha = ((opacity * 255 + 0.5f).toInt() shl 24)
        val expectedColor = color and 0x00ffffff or expectedAlpha
        println("withOpacity(0x${color.toHexString()}, $opacity): 0x${result.toHexString()} (期望: 0x${expectedColor.toHexString()})")
    }

    private fun testRgb(colorInt: Int, expected: String) {
        val result = ColorUtils.rgb(colorInt)
        println("rgb(0x${colorInt.toHexString()}): $result (期望: $expected)")
    }

    private fun testArgb(colorInt: Int, expected: String) {
        val result = ColorUtils.argb(colorInt)
        println("argb(0x${colorInt.toHexString()}): $result (期望: $expected)")
    }

    private fun testToColorInt(colorStr: String, expectedValue: String) {
        try {
            val result = ColorUtils.toColorInt(colorStr)
            println("toColorInt(\"$colorStr\"): 0x${result.toHexString()} (期望: $expectedValue)")
        } catch (e: Exception) {
            println("toColorInt(\"$colorStr\"): 异常 -> ${e.message}")
        }
    }
}
