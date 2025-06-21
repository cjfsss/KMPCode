import hos.coordinates.unit.DistanceUnit
import kotlin.test.Test

/**
 * <p>Title: DistanceUnitTest </p>
 * <p>Description: 测试 DistanceUnit 中的距离单位转换与格式化功能 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-18 17:00
 * @version : 1.0
 */
class DistanceUnitTest {

    @Test
    fun testAllFunctions() {
        println("——— 开始测试 DistanceUnit 功能 ———")

        // 原始数值定义
        val meters = 1500.0       // 1.5 千米
        val kilometers = 5.0      // 5 千米
        val miles = 2.0           // 2 英里

        // 1. convert - 单位转换测试
        println("[convert] 单位转换测试:")
        println("  - $meters 米 -> 千米: ${DistanceUnit.METERS.convert(meters, DistanceUnit.KILOMETERS)}")
        println("  - $kilometers 千米 -> 米: ${DistanceUnit.KILOMETERS.convert(kilometers, DistanceUnit.METERS)}")
        println("  - $miles 英里 -> 米: ${DistanceUnit.MILES.convert(miles, DistanceUnit.METERS)}")
        println("  - $miles 英里 -> 千米: ${DistanceUnit.MILES.convert(miles, DistanceUnit.KILOMETERS)}")
        println("  - $kilometers 千米 -> 英里: ${DistanceUnit.KILOMETERS.convert(kilometers, DistanceUnit.MILES)}")

        // 2. format / formatEn - 格式化输出（中文/英文）
        println("\n[format] 中文格式化输出测试:")
        println("  - $meters 米 -> 千米: " + DistanceUnit.METERS.format(meters, DistanceUnit.KILOMETERS))
        println("  - $miles 英里 -> 米: " + DistanceUnit.MILES.format(miles, DistanceUnit.METERS))
        println("  - $kilometers 千米 -> 英里: " + DistanceUnit.KILOMETERS.format(kilometers, DistanceUnit.MILES))

        println("\n[formatEn] 英文格式化输出测试:")
        println("  - $meters 米 -> 千米: " + DistanceUnit.METERS.formatEn(meters, DistanceUnit.KILOMETERS))
        println("  - $miles 英里 -> 千米: " + DistanceUnit.MILES.formatEn(miles, DistanceUnit.KILOMETERS))
        println("  - $kilometers 千米 -> 英里: " + DistanceUnit.KILOMETERS.formatEn(kilometers, DistanceUnit.MILES))

        // 3. 静态方法测试：companion object
        println("\n[companion object] 静态方法测试:")
        println("  - $miles 英里 -> 千米 (静态 convert): " +
                "${DistanceUnit.convert(miles, DistanceUnit.MILES, DistanceUnit.KILOMETERS)}")
        println("  - $miles 英里 -> 米 (静态 format): " +
                DistanceUnit.format(miles, DistanceUnit.MILES, DistanceUnit.METERS))
        println("  - $kilometers 千米 -> 英里 (静态 formatEn): " +
                DistanceUnit.formatEn(kilometers, DistanceUnit.KILOMETERS, DistanceUnit.MILES))

        // 4. 小数位控制测试
        println("\n[decimals] 小数位控制测试:")
        println("  - $miles 英里 -> 千米 (保留 4 位小数): " +
                DistanceUnit.MILES.format(miles, DistanceUnit.KILOMETERS, decimals = 4))
        println("  - $kilometers 千米 -> 英里 (保留 3 位小数): " +
                DistanceUnit.KILOMETERS.format(kilometers, DistanceUnit.MILES, decimals = 3))
        println("  - $meters 米 -> 英里 (保留 2 位小数): " +
                DistanceUnit.METERS.format(meters, DistanceUnit.MILES, decimals = 2))

        println("——— DistanceUnit 测试完成 ———")
    }
}
