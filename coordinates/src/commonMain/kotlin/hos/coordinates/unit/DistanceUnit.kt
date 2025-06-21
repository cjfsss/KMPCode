package hos.coordinates.unit

import kotlin.math.pow


/**
 * <p>Title: DistanceUnit </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 10:15
 * @version : 1.0
 */
/**
 * 枚举类：距离单位
 *
 * 用于定义不同距离单位及其与米的换算因子和符号表示
 */
enum class DistanceUnit(
    val factorToMeters: Double,
    val symbol: String, val chineseSymbol: String,
) {
    // 定义距离单位：米，及其换算因子和符号
    METERS(1.0, "m", "米"),
    // 定义距离单位：千米，及其换算因子和符号
    KILOMETERS(1000.0, "km", "千米"),
    // 定义距离单位：英里，及其换算因子和符号
    MILES(1609.344, "mi", "英里");

    /**
     * 辅助扩展函数：保留指定小数位
     */
    private fun Double.round(decimals: Int): Double {
        val factor = 10.0.pow(decimals.toDouble())
        return (this * factor).toInt().toDouble() / factor
    }

    /**
     * 将当前单位的值转换为另一个单位
     *
     * @param value 待转换的数值
     * @param to 目标单位
     * @return 转换后的数值
     */
    fun convert(value: Double, to: DistanceUnit): Double {
        val meters = value * factorToMeters
        return meters / to.factorToMeters
    }

    /**
     * 格式化输出带单位的字符串（自动保留小数）
     *
     * @param value 数值
     * @param toUnit 目标单位
     * @param decimals 小数位数
     * @return 格式化后的字符串，如 "12.3 米"
     */
    fun format(value: Double, toUnit: DistanceUnit, decimals: Int = 1): String {
        val converted = convert(value, toUnit)
        val rounded = converted.round(decimals)
        return "$rounded ${toUnit.chineseSymbol}"
    }

    /**
     * 格式化输出带单位的字符串（自动保留小数）
     *
     * @param value 数值
     * @param toUnit 目标单位
     * @param decimals 小数位数
     * @return 格式化后的字符串，如 "12.3 m"
     */
    fun formatEn(value: Double, toUnit: DistanceUnit, decimals: Int = 1): String {
        val converted = convert(value, toUnit)
        val rounded = converted.round(decimals)
        return "$rounded ${toUnit.symbol}"
    }

    companion object {

        /**
         * 静态方法：转换距离单位
         *
         * @param value 数值
         * @param fromUnit 原始单位
         * @param toUnit 目标单位
         * @return 转换后的数值
         */
        fun convert(
            value: Double,
            fromUnit: DistanceUnit,
            toUnit: DistanceUnit
        ): Double {
            return fromUnit.convert(value, toUnit)
        }

        /**
         * 静态方法：格式化输出带单位的字符串（自动保留小数）
         *
         * @param value 数值
         * @param fromUnit 原始单位
         * @param toUnit 目标单位
         * @param decimals 小数位数
         * @return 格式化后的字符串，如 "12.3 km" 或 "12.3 米"
         */
        fun format(
            value: Double,
            fromUnit: DistanceUnit,
            toUnit: DistanceUnit,
            decimals: Int = 1
        ): String {
            return fromUnit.format(value, toUnit, decimals)
        }

        /**
         * 静态方法：格式化输出带单位的字符串（自动保留小数）
         *
         * @param value 数值
         * @param fromUnit 原始单位
         * @param toUnit 目标单位
         * @param decimals 小数位数
         * @return 格式化后的字符串，如 "12.3 km" 或 "12.3 米"
         */
        fun formatEn(
            value: Double,
            fromUnit: DistanceUnit,
            toUnit: DistanceUnit,
            decimals: Int = 1
        ): String {
            return fromUnit.formatEn(value, toUnit, decimals)
        }
    }
}