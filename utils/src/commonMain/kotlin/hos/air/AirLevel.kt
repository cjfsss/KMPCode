package hos.air

import hos.utils.ColorUtils

/**
 * <p>Title: AirLevel </p>
 * <p>Description: 空气质量等级枚举类，定义不同等级及其描述和颜色 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 14:01
 * @version : 1.0
 */
enum class AirLevel(val level: Int, val levelDesc: String, val color: String) {
    LEVEL1(1, "优", "#00E401"),
    LEVEL2(2, "良", "#FFFF00"),
    LEVEL3(3, "轻度污染", "#FF7E00"),
    LEVEL4(4, "中度污染", "#FF0000"),
    LEVEL5(5, "重度污染", "#99004C"),
    LEVEL6(6, "严重污染", "#7E0023"),
    LEVEL0(0, "--", "#00E401");

    /**
     * 将颜色字符串转换为 Int 格式
     *
     * @return Android 兼容的颜色 Int 值
     */
    fun getColorInt(): Int = ColorUtils.toColorInt(color)
}