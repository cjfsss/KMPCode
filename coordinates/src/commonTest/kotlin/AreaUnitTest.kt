import hos.coordinates.unit.AreaUnit
import kotlin.test.Test

/**
 * <p>Title: AreaUnitTest </p>
 * <p>Description: 测试面积单位转换和格式化功能 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-18 15:00
 * @version : 1.0
 */
class AreaUnitTest {

    @Test
    fun testAllFunctions() {
        println("——— 开始测试 AreaUnit 功能 ———")

        // 原始值定义
        val squareMeters = 1_000_000.0     // 1 km²
        val squareKilometers = 5.0          // 5 km²

        // 1. 单位转换测试
        println("[convert] 单位转换测试:")
        println("  - $squareMeters 平方米 -> 平方千米: ${AreaUnit.SQUARE_METERS.convert(squareMeters, AreaUnit.SQUARE_KILOMETERS)}")
        println("  - $squareKilometers 平方千米 -> 平方米: ${AreaUnit.SQUARE_KILOMETERS.convert(squareKilometers, AreaUnit.SQUARE_METERS)}")

        // 2. 格式化中文输出测试
        println("\n[format] 中文格式化输出测试:")
        println("  - $squareMeters 平方米 -> 平方千米: " +
                AreaUnit.SQUARE_METERS.format(squareMeters, AreaUnit.SQUARE_KILOMETERS))

        // 3. 格式化英文输出测试
        println("\n[formatEn] 英文格式化输出测试:")
        println("  - $squareKilometers 平方千米 -> 平方米: " +
                AreaUnit.SQUARE_KILOMETERS.formatEn(squareKilometers, AreaUnit.SQUARE_METERS))

        // 4. 静态方法测试：companion object

        // 5. 小数位控制测试
        println("\n[decimals] 小数位控制测试:")
        println("  - $squareMeters 平方米 -> 平方千米 (保留 4 位小数): " +
                AreaUnit.SQUARE_METERS.format(squareMeters, AreaUnit.SQUARE_KILOMETERS, decimals = 4))

        println("——— AreaUnit 测试完成 ———")
    }
}
