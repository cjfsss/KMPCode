package hos.coordinates.unit

import kotlin.math.pow

/**
 * <p>Title: WeightUnit </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 10:55
 * @version : 1.0
 */
// 定义一个枚举类 WeightUnit，用于表示不同的重量单位
enum class WeightUnit(
    val factorToGrams: Double, // 每个单位对应的克数
    val symbol: String, // 单位的国际符号
    val chineseSymbol: String // 单位的中文符号
) {
    // 定义各种重量单位及其与克的换算因子和符号
    GRAMS(1.0, "g", "克"),
    KILOGRAMS(1000.0, "kg", "千克"),
    OUNCES(28.3495, "oz", "盎司"),
    POUNDS(453.592, "lb", "磅"),
    TONS(1000000.0, "t", "吨"),
    // 新增：中国传统单位
    QIAN(5.0, "qian", "钱"),   // 1 钱 = 5 克
    LIANG(50.0, "liang", "两"), // 1 两 = 50 克 = 10 钱
    JIN(500.0, "jin", "斤"),
    KILOGRAMS_JIN(1000.0, "kg", "公斤"),
    ;

    // 将当前单位的值转换为其他单位的值
    fun convert(value: Double, to: WeightUnit): Double {
        return value * factorToGrams / to.factorToGrams
    }

    /**
     * 格式化输出（保留两位小数）
     */
    fun format(value: Double, targetUnit: WeightUnit, decimals: Int = 2): String {
        val converted = convert(value, targetUnit)
        val rounded = converted.round(decimals)
        return "$rounded ${targetUnit.chineseSymbol}"
    }

    /**
     * 格式化输出（保留两位小数）
     */
    fun formatEn(value: Double, targetUnit: WeightUnit, decimals: Int = 2): String {
        val converted = convert(value, targetUnit)
        val rounded = converted.round(decimals)
        return "$rounded ${targetUnit.symbol}"
    }

    /**
     * 辅助函数：保留指定小数位
     */
    private fun Double.round(decimals: Int): Double {
        val factor = 10.0.pow(decimals.toDouble())
        return (this * factor).toInt().toDouble() / factor
    }

    // 伴生对象，提供静态方法
    companion object {
        /**
         * 静态方法：转换重量单位
         *
         * @param value 数值
         * @param fromUnit 原始单位
         * @param toUnit 目标单位
         * @return 转换后的数值
         */
        fun convert(
            value: Double,
            fromUnit: WeightUnit,
            toUnit: WeightUnit
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
            fromUnit: WeightUnit,
            toUnit: WeightUnit,
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
            fromUnit: WeightUnit,
            toUnit: WeightUnit,
            decimals: Int = 1
        ): String {
            return fromUnit.formatEn(value, toUnit, decimals)
        }
    }
}
