package hos.air

/**
 * <p>Title: AirLevelThreshold </p>
 * <p>Description: 空气质量因子对应的等级阈值定义 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 14:02
 * @version : 1.0
 */

enum class AirLevelThreshold(
    private val threshold1: Double,
    private val threshold2: Double,
    private val threshold3: Double,
    private val threshold4: Double,
    private val threshold5: Double
) {
    AQI(50.0, 100.0, 150.0, 200.0, 300.0),
    PM25(35.0, 75.0, 115.0, 150.0, 250.0),
    PM10(50.0, 150.0, 250.0, 350.0, 420.0),
    SO2_24(50.0, 150.0, 465.0, 800.0, 1600.0),
    NO2_24(40.0, 80.0, 180.0, 280.0, 565.0),
    CO_24(2.0, 4.0, 14.0, 24.0, 36.0),
    O3_8(160.0, 200.0, 300.0, 400.0, 800.0),
    SO2_1(150.0, 500.0, 65.0, 800.0, 1600.0),
    NO2_1(100.0, 200.0, 700.0, 1200.0, 2340.0),
    CO_1(5.0, 10.0, 35.0, 60.0, 90.0),
    O3_1(35.0, 75.0, 115.0, 150.0, 250.0);

    /**
     * 根据输入值获取对应空气质量等级
     *
     * @param value 输入值
     * @return 对应的 [AirLevel]
     */
    fun getLevel(value: Double?): AirLevel {
        if (value == null || value < 0 || value.isNaN() || value.isInfinite()) {
            return AirLevel.LEVEL0
        }
        return when {
            value < threshold1 -> AirLevel.LEVEL1
            value < threshold2 -> AirLevel.LEVEL2
            value < threshold3 -> AirLevel.LEVEL3
            value < threshold4 -> AirLevel.LEVEL4
            value < threshold5 -> AirLevel.LEVEL5
            else -> AirLevel.LEVEL6
        }
    }
}