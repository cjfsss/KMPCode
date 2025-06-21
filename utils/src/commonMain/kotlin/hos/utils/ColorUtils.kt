package hos.utils

/**
 * <p>Title: ColorUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-11 14:35
 * @version : 1.0
 */
object ColorUtils {
    /**
     * 将给定的颜色值与指定的alpha值合并，生成新的颜色值。
     * @param color 原始颜色值，ARGB格式。
     * @param alpha Alpha值，范围0-255，0代表完全透明，255代表完全不透明。
     * @return 新的颜色值，包含了指定的alpha值。
     */
    fun withAlpha(color: Int, alpha: Int): Int {
        // 移除原始颜色的alpha通道，并与新的alpha值合并
        return color and 0x00ffffff or (alpha shl 24)
    }

    /**
     * 将给定的颜色值与指定的不透明度合并，生成新的颜色值。
     * @param color 原始颜色值，ARGB格式。
     * @param opacity 不透明度值，范围0.0f-1.0f，0.0f代表完全透明，1.0f代表完全不透明。
     * @return 新的颜色值，包含了指定的不透明度。
     */
    fun withOpacity(color: Int, opacity: Float): Int {
        // 将不透明度转换为alpha值，并与原始颜色合并
        return color and 0x00ffffff or ((opacity * 255.0f + 0.5f).toInt() shl 24)
    }

    /**
     * 将给定的颜色值转换为RGB格式的字符串。
     * @param colorInt 颜色值，ARGB格式。
     * @return RGB格式的字符串，如"#RRGGBB"。
     */
    @ExperimentalStdlibApi
    fun rgb(colorInt: Int): String {
        // 将颜色值转换为RGB格式，并转换为字符串
        val hex = (colorInt and 0x00ffffff).toHexString()
        return "#${hex.padStart(6, '0')}"
    }

    /**
     * 将给定的颜色值转换为ARGB格式的字符串。
     * @param colorInt 颜色值，ARGB格式。
     * @return ARGB格式的字符串，如"#AARRGGBB"。
     */
    @ExperimentalStdlibApi
    fun argb(colorInt: Int): String {
        // 将颜色值转换为ARGB格式，并转换为字符串
        val hex = (colorInt).toHexString()
        val paddedHex = when {
            hex.length < 6 -> hex.padStart(6, '0')
            else -> hex
        }
        return "#${paddedHex.padStart(8, 'f')}"
    }

    /**
     * 将给定的颜色字符串转换为颜色值。
     * @param colorStr 颜色字符串，支持"#RRGGBB"和"#AARRGGBB"格式。
     * @return 颜色值，ARGB格式。
     * @throws IllegalArgumentException 如果颜色字符串格式不正确。
     */
    fun toColorInt(colorStr: String): Int {
        // 验证颜色字符串的格式
        require(colorStr.startsWith('#')) { "颜色字符串必须以 '#' 开头" }

        val hexPart = colorStr.substring(1)
        val length = hexPart.length

        // 验证颜色字符串的长度
        require(length == 6 || length == 8) { "未知的颜色格式: $colorStr" }

        // 将颜色字符串转换为颜色值
        var color: Long = hexPart.toLong(16)
        if (length == 6) {
            // 如果是RGB格式，添加默认的alpha值
            color = color or 0xff000000L
        }
        return color.toInt()
    }


}