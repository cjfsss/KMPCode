package hos.coordinates.unit

import kotlin.math.pow

/**
 * <p>Title: AreaUnit </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 10:46
 * @version : 1.0
 */
enum class AreaUnit(
    val factorToMeters: Double,
    val symbol: String, val chineseSymbol: String,
) {
    // 定义不同的面积单位及其对应的米平方转换因子、符号和中文符号
    SQUARE_MILLIMETRE(0.000001, "cm²", "平方毫米"),
    SQUARE_CENTIMETERS(0.0001, "cm²", "平方厘米"),
    SQUARE_METERS(1.0, "m²", "平方米"),
    SQUARE_KILOMETERS(1000000.0, "km²", "平方千米");

    /**
     * 辅助扩展函数：保留指定小数位
     */
    private fun Double.round(decimals: Int): Double {
        val factor = 10.0.pow(decimals.toDouble())
        return (this * factor).toInt().toDouble() / factor
    }

    /**
     * 将指定值从当前单位转换为指定的目标单位
     *
     * @param value 原始数值
     * @param toUnit 目标单位
     * @return 转换后的数值
     */
    fun convert(value: Double, toUnit: AreaUnit): Double {
        val squareMeters = value * factorToMeters
        return squareMeters / toUnit.factorToMeters
    }

    /**
     * 格式化输出带单位的字符串（自动保留小数）
     *
     * @param value 数值
     * @param toUnit 目标单位
     * @param decimals 小数位数
     * @return 格式化后的字符串，如 "12.3 平方公里"
     */
    fun format(value: Double, toUnit: AreaUnit, decimals: Int = 2): String {
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
     * @return 格式化后的字符串，如 "12.3 km²"
     */
    fun formatEn(value: Double, toUnit: AreaUnit, decimals: Int = 2): String {
        val converted = convert(value, toUnit)
        val rounded = converted.round(decimals)
        return "$rounded ${toUnit.symbol}"
    }

    companion object {
        /**
         * 将指定值从一个单位转换到另一个单位
         *
         * @param value 原始数值
         * @param fromUnit 原始单位
         * @param toUnit 目标单位
         * @return 转换后的数值
         */
        fun convert(value: Double, fromUnit: AreaUnit, toUnit: AreaUnit): Double {
            return fromUnit.convert(value, toUnit)
        }

        /**
         * 格式化输出带单位的字符串（自动保留小数）
         *
         * @param value 数值
         * @param fromUnit 原始单位
         * @param toUnit 目标单位
         * @param decimals 小数位数
         * @return 格式化后的字符串，如 "12.3 平方公里"
         */
        fun format(value: Double, fromUnit: AreaUnit, toUnit: AreaUnit, decimals: Int = 2): String {
            return fromUnit.format(value, toUnit, decimals)
        }

        /**
         * 格式化输出带单位的字符串（自动保留小数）
         *
         * @param value 数值
         * @param fromUnit 原始单位
         * @param toUnit 目标单位
         * @param decimals 小数位数
         * @return 格式化后的字符串，如 "12.3 km²"
         */
        fun formatEn(
            value: Double,
            fromUnit: AreaUnit,
            toUnit: AreaUnit,
            decimals: Int = 2
        ): String {
            return fromUnit.formatEn(value, toUnit, decimals)
        }
    }
}
