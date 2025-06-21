package hos.coordinates.ext

import hos.coordinates.CoordinateType
import hos.coordinates.geometry.LatLng
import kotlin.math.abs
import kotlin.math.pow

/**
 * <p>Title: CoordinatesExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 15:41
 * @version : 1.0
 */
// Double 扩展：保留 n 位小数（四舍五入）
fun Double.round(decimals: Int): Double {
    require(decimals >= 0)
    val factor = 10.0.pow(decimals.toDouble())
    return kotlin.math.round(this * factor) / factor
}

/**
 * 将 Double 转换为保留 6 位小数的字符串（精确四舍五入，补零）
 */
fun Double.to6DecimalPlaces(): String {
    val value = this.round(6) // 先四舍五入到 6 位小数
    val integerPart = value.toLong()
    val decimalPart = abs(value - integerPart)

    // 使用乘法和四舍五入获取六位小数部分
    val decimalStr = (decimalPart * 1_000_000 + 0.5).toLong().toString().padStart(6, '0').take(6)
    return "$integerPart.$decimalStr"
}

/**
 * 将 Double 类型的数值转换为度分秒（DMS）格式的字符串
 * 用于地理位置的纬度表示
 */
fun Double.toDMS(): String {
    val degrees = this.toInt()
    var remaining = (this - degrees) * 60
    val minutes = remaining.toInt()
    remaining = (remaining - minutes) * 60
    val seconds = remaining.toInt()
    return "$degrees°$minutes'$seconds\""
}


// 自定义 sign 函数，兼容所有 Kotlin 多平台（JVM / JS / Native）
fun Double.sign(): Double = when {
    this < 0 -> -1.0
    this > 0 -> 1.0
    else -> 0.0
}

/**
 * 将 DMS 格式的字符串转换为纬度值
 * 支持多种分隔符和可选的负号
 */
fun String.toLatitude(): Double? {
    val parts = this.split(Regex("[^\\d\\-\\.]+"))
        .filter { it.isNotBlank() }
    if (parts.size < 3) return null
    val deg = parts[0].toDoubleOrNull() ?: return null
    val min = parts[1].toDoubleOrNull() ?: return null
    val sec = parts.getOrElse(2) { "0" }.toDoubleOrNull() ?: return null
    return deg.sign() * (abs(deg) + min / 60.0 + sec / 3600.0)
}

/**
 * 将 DMS 格式的字符串转换为经度值
 * 与纬度转换类似，但适用于经度表示
 */
fun String.toLongitude(): Double? {
    val parts = this.split(Regex("[^\\d\\-\\.]+"))
        .filter { it.isNotBlank() }
    if (parts.size < 3) return null
    val deg = parts[0].toDoubleOrNull() ?: return null
    val min = parts[1].toDoubleOrNull() ?: return null
    val sec = parts.getOrElse(2) { "0" }.toDoubleOrNull() ?: return null
    return deg.sign() * (abs(deg) + min / 60.0 + sec / 3600.0)
}


/**
 * 将 DMS 格式的字符串转换为 LatLng 对象
 * 支持格式如："39°54'15\" 116°24'26\"", "39d54m15s 116d24m26s"
 */
fun String.toLatLng(type: CoordinateType): LatLng? {
    val parts = this.split(Regex("\\s+"))
    if (parts.size < 2) return null

    val latStr = parts[0]
    val lonStr = parts[1]

    val latitude = latStr.toLatitude() ?: return null
    val longitude = lonStr.toLongitude() ?: return null

    return LatLng(latitude, longitude, type)
}

/**
 * 将带有方向（N/S）的纬度字符串转换为 Double 类型的纬度值
 * 支持格式如："N 39°54'15\"", "S 39d54m15s"
 */
fun String.toLatitudeWithDirection(): Double? {
    val pattern = Regex("""([NS])?\s*(\d+)\D+(\d+)\D+(\d*\.?\d*)\D*""", RegexOption.IGNORE_CASE)
    val match = pattern.matchEntire(this) ?: return null
    val (dir, deg, min, sec) = match.destructured
    val degrees = deg.toDouble() + min.toDouble() / 60 + sec.toDouble() / 3600
    return when (dir.uppercase()) {
        "S" -> -degrees
        else -> degrees
    }
}
