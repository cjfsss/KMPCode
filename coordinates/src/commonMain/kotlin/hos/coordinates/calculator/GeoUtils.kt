package hos.coordinates.calculator

import hos.coordinates.geometry.LatLng

/**
 * <p>Title: GeoUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 15:28
 * @version : 1.0
 */
object GeoUtils {

    // ————————————————————————
    // 🌍 基础几何计算
    // ————————————————————————
    /**
     * 计算两点之间的大地线距离（单位：米）
     *
     * @param a 起点坐标
     * @param b 终点坐标
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 两点之间的大地线距离（单位：米）
     */
    fun distanceTo(
        a: LatLng,
        b: LatLng,
        calculator: GeoCalculator = VincentyCalculator
    ): Double {
        return calculator.distanceTo(a, b)
    }

    /**
     * 获取多个点之间的总路径长度（单位：米）
     *
     * @param points 地理坐标点列表
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 多个点之间的总路径长度（单位：米）
     */
    fun totalDistance(
        points: List<LatLng>,
        calculator: GeoCalculator = VincentyCalculator
    ): Double {
        return calculator.totalDistance(points)
    }

    /**
     * 使用指定算法计算闭合多边形面积（单位：平方公里）
     *
     * @param points 闭合多边形顶点列表（建议 WGS84 坐标系）
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 面积（平方公里）
     */
    fun calculateArea(
        points: List<LatLng>,
        calculator: GeoCalculator = VincentyCalculator
    ): Double {
        return try {
            calculator.calculateArea(points)
        } catch (e: Exception) {
            HaversineCalculator.calculateArea(points)
        }
    }

    /**
     * 使用指定算法计算闭合多边形面积（单位：平方米）
     *
     * @param points 闭合多边形顶点列表（建议 WGS84 坐标系）
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 面积（平方米）
     */
    fun calculateAreaInSquareMeters(
        points: List<LatLng>,
        calculator: GeoCalculator = VincentyCalculator
    ): Double {
        return try {
            calculator.calculateAreaInSquareMeters(points)
        } catch (e: Exception) {
            HaversineCalculator.calculateAreaInSquareMeters(points)
        }
    }

    /**
     * 计算从当前点到目标点的方位角（单位：度）
     *
     * 0° 表示正北方向，90° 表示正东方向
     *
     * @param from 起始点
     * @param to 目标点
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 方位角（度数）
     */
    fun bearingTo(
        from: LatLng,
        to: LatLng,
        calculator: GeoCalculator = VincentyCalculator
    ): Double {
        return try {
            calculator.bearingTo(from, to)
        } catch (e: Exception) {
            HaversineCalculator.bearingTo(from, to)
        }
    }

    /**
     * 根据当前经纬度点、偏移距离和方向，计算偏移后的新点
     *
     * @param point 原始点
     * @param distance 偏移距离（单位：米）
     * @param bearing 偏移方向（单位：度，0° 正北）
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 偏移后的新坐标点
     */
    fun offset(
        point: LatLng,
        distance: Double,
        bearing: Double,
        calculator: GeoCalculator = VincentyCalculator
    ): LatLng {
        return calculator.offset(point, distance, bearing)
    }

    /**
     * 获取多边形的几何中心（适用于全球范围）
     *
     * @param points 多边形顶点列表（建议 WGS84 坐标系）
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 多边形的几何中心点
     */
    fun getCenterOfPolygon(
        points: List<LatLng>,
        calculator: GeoCalculator = VincentyCalculator
    ): LatLng {
        return calculator.getCenterOfPolygon(points)
    }

    /**
     * 计算球面上一点 P 到线段 AB 的最短大地线距离（单位：米）
     *
     * @param a 线段起点 A
     * @param b 线段终点 B
     * @param p 目标点 P
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 最短大地线距离（单位：米）
     */
    fun distanceToSegment(
        a: LatLng,
        b: LatLng,
        p: LatLng,
        calculator: GeoCalculator = VincentyCalculator
    ): Double {
        return calculator.distanceToSegment(a, b, p)
    }

    // ————————————————————————
    // 🔍 点对分析
    // ————————————————————————
    /**
     * 查找最近的点对
     *
     * @param points 点集合
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 最近的一对点及其距离信息
     */
    fun findNearestPair(
        points: List<LatLng>,
        calculator: GeoCalculator = VincentyCalculator
    ): LatLngDistance? {
        return calculator.findNearestPair(points)
    }

    /**
     * 查找最远的点对
     *
     * @param points 点集合
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 最远的一对点及其距离信息
     */
    fun findFarthestPair(
        points: List<LatLng>,
        calculator: GeoCalculator = VincentyCalculator
    ): LatLngDistance? {
        return calculator.findFarthestPair(points)
    }

    /**
     * 获取所有点对的距离信息
     *
     * @param points 点集合
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 包含每对点及其距离的列表
     */
    fun calculateAllPairsDistance(
        points: List<LatLng>,
        calculator: GeoCalculator = VincentyCalculator
    ): List<LatLngDistance> {
        return calculator.calculateAllPairsDistance(points)
    }

    // ————————————————————————
    // 🕘 辅助函数
    // ————————————————————————
    /**
     * 批量计算多个点相对于一个基准点的距离
     *
     * @param base 基准点
     * @param points 待计算点集合
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 每个点到基准点的距离列表（单位：米）
     */
    fun batchDistanceFrom(
        base: LatLng,
        points: List<LatLng>,
        calculator: GeoCalculator = VincentyCalculator
    ): List<Double> {
        return calculator.batchDistanceFrom(base, points)
    }

    /**
     * 判断一个点是否位于以 center 为圆心、radius 为半径的圆形区域内
     *
     * @param center 圆心坐标
     * @param radius 圆的半径（单位：米）
     * @param point 待判断的点
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return true 表示在圆内，false 表示在圆外
     */
    fun isPointInCircle(
        center: LatLng,
        radius: Double,
        point: LatLng,
        calculator: GeoCalculator = VincentyCalculator
    ): Boolean {
        return calculator.isPointInCircle(center, radius, point)
    }

    /**
     * 批量判断多个点是否在指定圆内
     *
     * @param center 圆心坐标
     * @param radius 圆的半径（单位：米）
     * @param points 待判断的点集合
     * @param calculator 可选的地理计算实现，默认为 [VincentyCalculator]
     * @return 每个点是否在圆内的布尔值列表
     */
    fun arePointsInCircle(
        center: LatLng,
        radius: Double,
        points: List<LatLng>,
        calculator: GeoCalculator = VincentyCalculator
    ): List<Boolean> {
        return calculator.arePointsInCircle(center, radius, points)
    }

}