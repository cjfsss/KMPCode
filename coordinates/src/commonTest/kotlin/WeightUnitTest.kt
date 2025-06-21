import hos.coordinates.unit.WeightUnit
import kotlin.test.Test

/**
 * <p>Title: WeightUnitTest </p>
 * <p>Description: 测试 WeightUnit 中的重量单位转换与格式化功能 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-18 18:00
 * @version : 1.0
 */
class WeightUnitTest {

    @Test
    fun testAllFunctions() {
        println("——— 开始测试 WeightUnit 功能 ———")

        // 原始数值定义
        val grams = 1500.0       // 1.5 千克
        val kilograms = 5.0      // 5 千克
        val ounces = 16.0        // 16 盎司 ≈ 1 磅
        val pounds = 2.0         // 2 磅
        val tons = 1.0           // 1 吨
        val qian = 10.0          // 10 钱 = 50 克
        val liang = 1.0          // 1 两 = 50 克
        val jin = 1.0            // 1 斤 = 500 克
        val kgJin = 1.0          // 1 公斤 = 1 千克 = 2 斤

        // 1. convert - 单位转换测试
        println("[convert] 单位转换测试:")
        println("  - $grams 克 -> 千克: ${WeightUnit.GRAMS.convert(grams, WeightUnit.KILOGRAMS)}")
        println("  - $kilograms 千克 -> 克: ${WeightUnit.KILOGRAMS.convert(kilograms, WeightUnit.GRAMS)}")
        println("  - $ounces 盎司 -> 磅: ${WeightUnit.OUNCES.convert(ounces, WeightUnit.POUNDS)}")
        println("  - $pounds 磅 -> 盎司: ${WeightUnit.POUNDS.convert(pounds, WeightUnit.OUNCES)}")
        println("  - $tons 吨 -> 千克: ${WeightUnit.TONS.convert(tons, WeightUnit.KILOGRAMS)}")
        println("  - $qian 钱 -> 克: ${WeightUnit.QIAN.convert(qian, WeightUnit.GRAMS)}")
        println("  - $liang 两 -> 钱: ${WeightUnit.LIANG.convert(liang, WeightUnit.QIAN)}")
        println("  - $jin 斤 -> 千克: ${WeightUnit.JIN.convert(jin, WeightUnit.KILOGRAMS)}")
        println("  - $kgJin 千克 -> 公斤: ${WeightUnit.KILOGRAMS.convert(kgJin, WeightUnit.KILOGRAMS_JIN)}")

        // 2. format / formatEn - 格式化输出（中文/英文）
        println("\n[format] 中文格式化输出测试:")
        println("  - $grams 克 -> 千克: " + WeightUnit.GRAMS.format(grams, WeightUnit.KILOGRAMS))
        println("  - $pounds 磅 -> 盎司: " + WeightUnit.POUNDS.format(pounds, WeightUnit.OUNCES))
        println("  - $liang 两 -> 克: " + WeightUnit.LIANG.format(liang, WeightUnit.GRAMS))
        println("  - $jin 斤 -> 千克: " + WeightUnit.JIN.format(jin, WeightUnit.KILOGRAMS))

        println("\n[formatEn] 英文格式化输出测试:")
        println("  - $kilograms 千克 -> 克: " + WeightUnit.KILOGRAMS.formatEn(kilograms, WeightUnit.GRAMS))
        println("  - $pounds 磅 -> 盎司: " + WeightUnit.POUNDS.formatEn(pounds, WeightUnit.OUNCES))
        println("  - $qian 钱 -> 克: " + WeightUnit.QIAN.formatEn(qian, WeightUnit.GRAMS))
        println("  - $tons 吨 -> 千克: " + WeightUnit.TONS.formatEn(tons, WeightUnit.KILOGRAMS))

        // 3. 静态方法测试：companion object
        println("\n[companion object] 静态方法测试:")
        println("  - $ounces 盎司 -> 千克 (静态 convert): " +
                "${WeightUnit.convert(ounces, WeightUnit.OUNCES, WeightUnit.KILOGRAMS)}")
        println("  - $qian 钱 -> 盎司 (静态 format): " +
                WeightUnit.format(qian, WeightUnit.QIAN, WeightUnit.OUNCES))
        println("  - $jin 斤 -> 磅 (静态 formatEn): " +
                WeightUnit.formatEn(jin, WeightUnit.JIN, WeightUnit.POUNDS))

        // 4. 小数位控制测试
        println("\n[decimals] 小数位控制测试:")
        println("  - $grams 克 -> 千克 (保留 3 位小数): " +
                WeightUnit.GRAMS.format(grams, WeightUnit.KILOGRAMS, decimals = 3))
        println("  - $pounds 磅 -> 盎司 (保留 1 位小数): " +
                WeightUnit.POUNDS.format(pounds, WeightUnit.OUNCES, decimals = 1))
        println("  - $jin 斤 -> 磅 (保留 4 位小数): " +
                WeightUnit.JIN.format(jin, WeightUnit.POUNDS, decimals = 4))

        println("——— WeightUnit 测试完成 ———")
    }
}
