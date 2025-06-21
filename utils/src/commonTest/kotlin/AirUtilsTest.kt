import hos.air.AirUtils
import hos.air.*
import kotlin.test.Test

/**
 * <p>Title: AirUtilsTest </p>
 * <p>Description: 用于测试空气质量相关工具类和枚举逻辑 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-14 10:00
 * @version : 1.0
 */
class AirUtilsTest {

    @Test
    fun main() {
        // 测试空气质量等级描述、颜色、级别
        println("=== 测试 AirLevel ===")
        println("AirLevel.LEVEL1.levelDesc: ${AirLevel.LEVEL1.levelDesc} (期望: 优)")
        println("AirLevel.LEVEL1.color: ${AirLevel.LEVEL1.color} (期望: #00E401)")
        println("AirLevel.LEVEL1.level: ${AirLevel.LEVEL1.level} (期望: 1)")

        println("AirLevel.LEVEL6.levelDesc: ${AirLevel.LEVEL6.levelDesc} (期望: 严重污染)")
        println("AirLevel.LEVEL6.color: ${AirLevel.LEVEL6.color} (期望: #7E0023)")
        println("AirLevel.LEVEL6.level: ${AirLevel.LEVEL6.level} (期望: 6)")

        println("AirLevel.LEVEL0.levelDesc: ${AirLevel.LEVEL0.levelDesc} (期望: --)")
        println("AirLevel.LEVEL0.color: ${AirLevel.LEVEL0.color} (期望: #00E401)")
        println("AirLevel.LEVEL0.level: ${AirLevel.LEVEL0.level} (期望: 0)")

        // 测试单位获取
        println("\n=== 测试 AirUtils 单位 ===")
        println("AirUtils.unitUg(): ${AirUtils.unitUg()} (期望: μg/m³)")
        println("AirUtils.unitMg(): ${AirUtils.unitMg()} (期望: mg/m³)")

        // 测试因子映射
        println("\n=== 测试 AirFactor 映射 ===")
        println("getAirFactor(\"PM25\"): ${AirFactor.getAirFactor("PM25")?.code} (期望: PM25)")
        println("getAirFactor(\"PM₂.₅\"): ${AirFactor.getAirFactor("PM₂.₅")?.code} (期望: PM25)")
        println("getAirFactor(\"pm2.5\"): ${AirFactor.getAirFactor("pm2.5")?.code} (期望: PM25)")
        println("getAirFactor(\"NO₂\"): ${AirFactor.getAirFactor("NO₂")?.code} (期望: NO2)")
        println("getAirFactor(\"未知气体\"): ${AirFactor.getAirFactor("未知气体")} (期望: null)")

        // 测试因子单位
        println("\n=== 测试 AirFactor 单位 ===")
        println("getUnit(AirFactor.PM25): ${AirFactor.getUnit(AirFactor.PM25)} (期望: μg/m³)")
        println("getUnit(AirFactor.NO2): ${AirFactor.getUnit(AirFactor.NO2)} (期望: ppb)")
        println("getUnit(AirFactor.CO): ${AirFactor.getUnit(AirFactor.CO)} (期望: ppm)")
        println("getUnit(AirFactor.AQI): ${AirFactor.getUnit(AirFactor.AQI)} (期望: )")

        // 测试阈值与等级判断
        println("\n=== 测试 AirLevelThreshold 等级判断 ===")
        testGetLevel(AirLevelThreshold.PM25, 30.0, "PM25", AirLevel.LEVEL1)
        testGetLevel(AirLevelThreshold.PM25, 70.0, "PM25", AirLevel.LEVEL2)
        testGetLevel(AirLevelThreshold.PM25, 100.0, "PM25", AirLevel.LEVEL3)
        testGetLevel(AirLevelThreshold.PM25, 180.0, "PM25", AirLevel.LEVEL4)
        testGetLevel(AirLevelThreshold.PM25, 280.0, "PM25", AirLevel.LEVEL5)
        testGetLevel(AirLevelThreshold.PM25, 300.0, "PM25", AirLevel.LEVEL6)
        testGetLevel(AirLevelThreshold.PM25, null, "PM25", AirLevel.LEVEL0)
        testGetLevel(AirLevelThreshold.PM25, Double.NaN, "PM25", AirLevel.LEVEL0)
        testGetLevel(AirLevelThreshold.PM25, Double.POSITIVE_INFINITY, "PM25", AirLevel.LEVEL0)

        // 测试 AirInfo 构造
        println("\n=== 测试 AirInfo 构造 ===")
        val info = AirInfo(AirFactor.PM25, AirLevel.LEVEL2)
        println("AirInfo.factor.symbol: ${info.factor.symbol} (期望: PM₂.₅)")
        println("AirInfo.level.levelDesc: ${info.level?.levelDesc} (期望: 良)")

        // 测试 getAirInfo24
        println("\n=== 测试 getAirInfo24 ===")
        testGetAirInfo24("PM25", 30.0, "PM25(30μg/m³)", AirLevel.LEVEL1)
        testGetAirInfo24("PM25", 120.0, "PM25(120μg/m³)", AirLevel.LEVEL3)
        testGetAirInfo24("PM25", 300.0, "PM25(300μg/m³)", AirLevel.LEVEL6)
        testGetAirInfo24("AQI", 250.0, "AQI(250)", AirLevel.LEVEL5)
        testGetAirInfo24("未知气体", null, "未知气体(null)", null)

        // 测试带单位的输出
        println("\n=== 测试 getValueWithUnit ===")
        println("getValueWithUnit(75.0): ${info.getValueWithUnit(75.0)} (期望: 75μg/m³)")
        println("getValueWithUnit(150.0): ${info.getValueWithUnit(150.0)} (期望: 150μg/m³)")
    }

    private fun testGetLevel(
        threshold: AirLevelThreshold,
        value: Double?,
        factorName: String,
        expectedLevel: AirLevel
    ) {
        val level = threshold.getLevel(value)
        println("getLevel($factorName, $value): ${level?.levelDesc} (期望: ${expectedLevel.levelDesc})")
    }

    private fun testGetAirInfo24(
        factorName: String?,
        value: Double?,
        displayValue: String,
        expectedLevel: AirLevel?
    ) {
        val info = AirInfo.getAirInfo24(factorName, value)
        val resultLevelDesc = info?.level?.levelDesc ?: "null"
        val expectedDesc = expectedLevel?.levelDesc ?: "null"
        println("getAirInfo24($displayValue): $resultLevelDesc (期望: $expectedDesc)")
    }
}
