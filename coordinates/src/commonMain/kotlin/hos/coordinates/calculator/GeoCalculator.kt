package hos.coordinates.calculator

import hos.coordinates.geometry.LatLng
import kotlin.collections.zipWithNext

/**
 * 地理坐标计算接口，支持多种算法实现（Haversine / Vincenty / Spherical）
 */
interface GeoCalculator {
    /**
     * 计算两点之间的大地线距离（单位：米）
     */
    fun distanceTo(a: LatLng, b: LatLng): Double

    /**
     * 获取多个点之间的总路径长度（单位：米）
     */
    fun totalDistance(points: List<LatLng>): Double {
        if (points.size < 2) return 0.0
        return points.zipWithNext().sumOf { (a, b) -> distanceTo(a, b) }
    }

    /**
     * 使用 Vincenty 算法计算闭合多边形面积（单位：平方公里）
     */
    fun calculateArea(points: List<LatLng>): Double

    /**
     * 返回平方米单位下的面积
     */
    fun calculateAreaInSquareMeters(points: List<LatLng>): Double =
        calculateArea(points) * 1_000_000

    /**
     * 计算从当前点到目标点的方位角（单位：度）
     * 0° 表示正北，90° 表示正东
     */
    fun bearingTo(from: LatLng, to: LatLng): Double

    /**
     * 根据当前经纬度点、偏移距离和方向，计算偏移后的新点
     */
    fun offset(point: LatLng, distance: Double, bearing: Double): LatLng

    /**
     * 获取多边形的几何中心（适用于全球范围）
     */
    fun getCenterOfPolygon(points: List<LatLng>): LatLng

    /**
     * 查找最近的点对
     */
    fun findNearestPair(points: List<LatLng>): LatLngDistance? {
        return calculateAllPairsDistance(points).minByOrNull { it.distance }
    }

    /**
     * 查找最远的点对
     */
    fun findFarthestPair(points: List<LatLng>): LatLngDistance? {
        return calculateAllPairsDistance(points).maxByOrNull { it.distance }
    }

    /**
     * 获取所有点对的距离
     */
    fun calculateAllPairsDistance(points: List<LatLng>): List<LatLngDistance> {
        return buildList {
            for (i in points.indices) {
                for (j in i + 1 until points.size) {
                    add(
                        LatLngDistance(
                            points[i], points[j],
                            VincentyCalculator.distanceTo(points[i], points[j])
                        )
                    )
                }
            }
        }
    }

    /**
     * 判断一个点是否位于以 center 为圆心、radius 为半径的圆形区域内
     */
    fun isPointInCircle(center: LatLng, radius: Double, point: LatLng): Boolean {
        requireSameCoordinateType(center, point)
        return VincentyCalculator.distanceTo(center, point) <= radius
    }

    /**
     * 批量判断多个点是否在指定圆内
     */
    fun arePointsInCircle(
        center: LatLng,
        radius: Double,
        points: List<LatLng>
    ): List<Boolean> {
        requireSameCoordinateType(center, points.firstOrNull() ?: return emptyList())

        return points.map { point ->
            isPointInCircle(center, radius, point)
        }
    }

    /**
     * 计算球面上一点 P 到线段 AB 的最短大地线距离（单位：米）
     */
    fun distanceToSegment(a: LatLng, b: LatLng, p: LatLng): Double

    // ————————————————————
    // 🛠 辅助函数
    // ————————————————————

    /**
     * 批量计算指定点到多个点的距离
     */
    fun batchDistanceFrom(base: LatLng, points: List<LatLng>): List<Double> {
        return points.map { HaversineCalculator.distanceTo(base, it) }
    }

    /**
     * 校验两个坐标点的坐标类型是否相同
     */
    fun requireSameCoordinateType(a: LatLng, b: LatLng) {
        require(a.type == b.type) { "坐标类型不一致，无法计算" }
    }

    /**
     * 校验三个坐标点的坐标类型是否相同
     */
    fun requireSameCoordinateType(a: LatLng, b: LatLng, c: LatLng) {
        require(a.type == b.type && a.type == c.type) { "坐标类型不一致，无法计算" }
    }
}
