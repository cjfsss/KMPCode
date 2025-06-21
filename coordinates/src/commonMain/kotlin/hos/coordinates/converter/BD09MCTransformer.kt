package hos.coordinates.converter

import kotlin.math.*

/**
 * <p>Title: BD09MCTransformer </p>
 * <p>Description: GCJ02 <-> BD09MC 坐标互转算法（百度坐标系） </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 11:22
 * @version : 1.0
 */
object BD09MCTransformer {
    private const val X_PI = PI * 3000.0 / 180.0
    private const val A = 6378245.0 // 长半轴
    private const val EE = 0.006693421622965943 // 扁率平方

    /**
     * GCJ02 -> BD09MC（百度墨卡托投影）
     */
    fun gcj02ToBd09mc(lat: Double, lon: Double): Pair<Double, Double> {
        val x = lon
        val y = lat
        val z = sqrt(x * x + y * y) + 0.00002 * sin(y * X_PI)
        val theta = atan2(y, x) + 0.000003 * cos(x * X_PI)
        val bdLon = z * cos(theta) + 0.0065
        val bdLat = z * sin(theta) + 0.006
        return Pair(bdLat, bdLon)
    }

    /**
     * BD09MC -> GCJ02
     */
    fun bd09mcToGcj02(lat: Double, lon: Double): Pair<Double, Double> {
        val x = lon - 0.0065
        val y = lat - 0.006
        val z = sqrt(x * x + y * y) - 0.00002 * sin(y * X_PI)
        val theta = atan2(y, x) - 0.000003 * cos(x * X_PI)
        val gcjLon = z * cos(theta)
        val gcjLat = z * sin(theta)
        return Pair(gcjLat, gcjLon)
    }
}