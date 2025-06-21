package hos.coordinates.converter

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * <p>Title: GCJ02OffsetAlgorithm </p>
 * <p>Description: WGS84 -> GCJ02 火星坐标偏移算法  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 11:21
 * @version : 1.0
 */
object GCJ02OffsetAlgorithm {
    private const val X_PI = PI * 3000.0 / 180.0
    private const val A = 6378245.0 // 长半轴
    private const val EE = 0.006693421622965943 // 扁率平方

    private fun Double.toRadians(): Double = this * PI / 180.0

    fun offset(lat: Double, lon: Double): Pair<Double, Double> {
        if (!isInChina(lat, lon)) return lat to lon

        val dLat = transformLat(lon - 105.0, lat - 35.0)
        val dLon = transformLon(lon - 105.0, lat - 35.0)

        val radLat = lat * PI / 180.0
        val magic = sin(radLat)
        val mh = sqrt(A * A * (1 - EE) / (1 - EE * magic * magic))
        val mgg = mh * (1 - EE) / (1 - EE * magic * magic)

        val gcjLat = lat + dLat * 180.0 / (mgg * PI)
        val gcjLon = lon + dLon * 180.0 / (mh * cos(radLat) * PI)

        return Pair(gcjLat, gcjLon)
    }

    fun reverse(lat: Double, lon: Double): Pair<Double, Double> {
        val dLat = transformLat(lon, lat)
        val dLon = transformLon(lon, lat)
        return Pair(lat - dLat, lon - dLon)
    }

    private fun isInChina(lat: Double, lon: Double): Boolean {
        return lon >= 73.66 && lon <= 135.04 && lat >= 3.86 && lat <= 53.55
    }

    private fun transformLat(x: Double, y: Double): Double {
        var ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * sqrt(abs(x))
        ret += (20.0 * sin(6.0 * x * PI) + 20.0 * sin(2.0 * x * PI)) * 2.0 / 3.0
        ret += (20.0 * sin(y * PI) + 40.0 * sin(y / 3.0 * PI)) * 2.0 / 3.0
        ret += (160.0 * sin(y / 12.0 * PI) + 320 * sin(y * PI / 30.0)) * 2.0 / 3.0
        return ret
    }

    private fun transformLon(x: Double, y: Double): Double {
        var ret = 300.0 + x + 2.0 * y + 0.1 * y * y + 0.1 * x * y + 0.1 * sqrt(abs(x))
        ret += (20.0 * sin(6.0 * x * PI) + 20.0 * sin(2.0 * x * PI)) * 2.0 / 3.0
        ret += (20.0 * sin(x * PI) + 40.0 * sin(x / 3.0 * PI)) * 2.0 / 3.0
        ret += (150.0 * sin(x / 12.0 * PI) + 300.0 * sin(x / 30.0 * PI)) * 2.0 / 3.0
        return ret
    }
}