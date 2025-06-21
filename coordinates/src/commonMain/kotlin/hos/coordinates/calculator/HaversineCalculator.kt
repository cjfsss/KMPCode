package hos.coordinates.calculator

import hos.coordinates.geometry.LatLng
import kotlin.math.*

/**
 * <p>Title: LatLngCalculator</p>
 * <p>Description: 地理坐标计算工具类，基于 Haversine 公式进行高性能计算</p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 17:10
 * @version : 1.0
 */
object HaversineCalculator : GeoCalculator {

    private const val EARTH_RADIUS_M = 6371000.0 // 地球平均半径（米）
    private const val EARTH_RADIUS_KM = 6371.0   // 地球平均半径（公里）

    // ————————————————————
    // 🌍 基础函数封装
    // ————————————————————

    /**
     * 使用 Haversine 公式计算两点之间距离（单位：米）
     */
   override fun distanceTo(a: LatLng, b: LatLng): Double {
        requireSameCoordinateType(a, b)

        val (lat1, lon1) = a.radians()
        val (lat2, lon2) = b.radians()

        val dLat = lat2 - lat1
        val dLon = lon2 - lon1

        val aVal = sin(dLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(aVal), sqrt(1 - aVal))

        return EARTH_RADIUS_M * c
    }

    // ————————————————————
    // 📐 面积计算（基于 Haversine）
    // ————————————————————

    /**
     * 使用 Haversine 公式计算闭合多边形面积（单位：平方公里）
     */
    override fun calculateArea(points: List<LatLng>): Double {
        if (points.size < 3) return 0.0
        val size = points.size
        var totalAngleSum = 0.0

        for (i in 0 until size) {
            val p1 = points[i]
            val p2 = points[(i + 1) % size]

            val lat1 = p1.latitude.toRadians()
            val lon1 = p1.longitude.toRadians()
            val lat2 = p2.latitude.toRadians()
            val lon2 = p2.longitude.toRadians()

            val y = sin(lon2 - lon1) * cos(lat2)
            val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(lon2 - lon1)
            val angle = atan2(
                sqrt(y.pow(2) + x.pow(2)),
                sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lon2 - lon1)
            )
            totalAngleSum += angle
        }

        return abs((totalAngleSum - (size - 2) * PI) * EARTH_RADIUS_KM.pow(2))
    }


    // ————————————————————
    // 🧭 方位角 & 偏移
    // ————————————————————

    /**
     * 使用 Haversine 公式计算从当前点到目标点的方位角（单位：度）
     * 0° 表示正北，90° 表示正东
     */
    override fun bearingTo(from: LatLng, to: LatLng): Double {
        requireSameCoordinateType(from, to)

        val (lat1, lon1) = from.radians()
        val (lat2, lon2) = to.radians()

        val deltaLon = lon2 - lon1
        val y = sin(deltaLon) * cos(lat2)
        val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(deltaLon)
        return (atan2(y, x).toDegrees() + 360) % 360
    }

    /**
     * 根据当前经纬度点、偏移距离和方向，计算偏移后的新点
     */
    override fun offset(point: LatLng, distance: Double, bearing: Double): LatLng {
        val earthRadius = EARTH_RADIUS_M
        val (lat, lon) = point.radians()
        val angularDistance = distance / earthRadius
        val bearingRad = bearing.toRadians()

        val lat2 = asin(
            sin(lat) * cos(angularDistance) +
                    cos(lat) * sin(angularDistance) * cos(bearingRad)
        )

        val lon2 = lon + atan2(
            sin(bearingRad) * sin(angularDistance) * cos(lat),
            cos(angularDistance) - sin(lat) * sin(lat2)
        )

        return LatLng(lat2.toDegrees(), lon2.toDegrees(), point.type)
    }

    /**
     * 使用 Haversine 算法获取多边形的几何中心（适用于中小范围）
     *
     * @param points 多边形顶点列表（必须闭合或不闭合均可）
     * @return 几何中心点
     */
    override fun getCenterOfPolygon(points: List<LatLng>): LatLng {
        require(points.isNotEmpty()) { "点集合不能为空" }
        requireSameCoordinateType(points[0], points[1])

        var x = 0.0
        var y = 0.0
        var z = 0.0

        for (point in points) {
            val (lat, lon) = point.radians()
            x += cos(lat) * cos(lon)
            y += cos(lat) * sin(lon)
            z += sin(lat)
        }

        val total = points.size.toDouble()
        x /= total
        y /= total
        z /= total

        val lon = atan2(y, x)
        val hyp = sqrt(x.pow(2) + y.pow(2))
        val lat = atan2(z, hyp)

        return LatLng(
            latitude = lat.toDegrees(),
            longitude = lon.toDegrees(),
            type = points[0].type
        )
    }



    /**
     * 计算球面上一点 P 到线段 AB 的最短大地线距离（单位：米）
     *
     * @param A 线段起点
     * @param B 线段终点
     * @param P 目标点
     * @return 最短距离（米）
     */
    override fun distanceToSegment(A: LatLng, B: LatLng, P: LatLng): Double {
        requireSameCoordinateType(A, B, P)

        val earthRadius = 6378137.0 // 地球长半轴（米）

        // 转换为地心坐标系下的三维向量
        val a = latLngToVector(A)
        val b = latLngToVector(B)
        val p = latLngToVector(P)

        // 向量 AB 方向
        val ab = vectorSubtract(b, a)
        // 投影点 t：在 AB 直线上离 P 最近的点的比例参数
        val ap = vectorSubtract(p, a)
        val dotAP_Ab = vectorDot(ap, ab)
        val dotAb_Ab = vectorDot(ab, ab)

        if (dotAP_Ab <= 0) {
            // 最近点就是 A
            return distanceTo(A, P)
        }

        if (dotAP_Ab >= dotAb_Ab) {
            // 最近点就是 B
            return distanceTo(B, P)
        }

        // 最近点在线段 AB 上
        val projection = vectorAdd(a, vectorScale(ab, dotAP_Ab / dotAb_Ab))
        val nearestPointOnSphere = normalizeVector(projection)
        val nearestLatLng = vectorToLatLng(nearestPointOnSphere)

        // 返回球面最近点到 P 的大地线距离
        return distanceTo(nearestLatLng, P)
    }

}
