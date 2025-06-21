import hos.coordinates.calculator.GeoUtils
import hos.coordinates.geometry.LatLng
import hos.coordinates.CoordinateType
import kotlin.test.Test

/**
 * <p>Title: GeoUtilsTest </p>
 * <p>Description: 测试 GeoUtils 中的所有公共地理计算方法 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-18 10:00
 * @version : 1.0
 */
class GeoUtilsTest {

    // 定义几个测试点（WGS84 坐标）
    private val beijing = LatLng(39.9042, 116.4074, CoordinateType.WGS84)
    private val shanghai = LatLng(31.2304, 121.4737, CoordinateType.WGS84)
    private val guangzhou = LatLng(23.1291, 113.2644, CoordinateType.WGS84)
    private val chengdu = LatLng(30.5728, 104.0668, CoordinateType.WGS84)

    // 多边形测试点（闭合矩形）
    private val polygonPoints = listOf(
        LatLng(39.9042, 116.4074, CoordinateType.WGS84), // 北京
        LatLng(39.9042, 116.5074, CoordinateType.WGS84),
        LatLng(39.8042, 116.5074, CoordinateType.WGS84),
        LatLng(39.8042, 116.4074, CoordinateType.WGS84),
        LatLng(39.9042, 116.4074, CoordinateType.WGS84)  // 闭合
    )

    // 线段和点测试
    private val pointA = LatLng(39.9042, 116.4074, CoordinateType.WGS84)
    private val pointB = LatLng(39.9142, 116.4174, CoordinateType.WGS84)
    private val pointP = LatLng(39.9092, 116.4124, CoordinateType.WGS84)

    @Test
    fun testAllFunctions() {
        println("——— 开始测试 GeoUtils 功能 ———")

        // 1. distanceTo - 计算两点距离
        val distanceBeijingShanghai = GeoUtils.distanceTo(beijing, shanghai)
        println("[distanceTo] 北京到上海的距离: $distanceBeijingShanghai 米")

        // 2. totalDistance - 多点路径总长度
        val pathPoints = listOf(beijing, shanghai, guangzhou, chengdu)
        val totalPathLength = GeoUtils.totalDistance(pathPoints)
        println("[totalDistance] 北京->上海->广州->成都 总距离: $totalPathLength 米")

        // 3. calculateArea - 多边形面积
        val area = GeoUtils.calculateArea(polygonPoints)
        println("[calculateArea] 多边形面积: $area 平方公里")

        // 4. calculateAreaInSquareMeters - 面积转平方米
        val areaInSquareMeters = GeoUtils.calculateAreaInSquareMeters(polygonPoints)
        println("[calculateAreaInSquareMeters] 多边形面积: $areaInSquareMeters 平方米")

        // 5. bearingTo - 方位角
        val bearing = GeoUtils.bearingTo(beijing, shanghai)
        println("[bearingTo] 北京到上海的方位角: $bearing 度")

        // 6. offset - 偏移点
        val offsetPoint = GeoUtils.offset(beijing, 10000.0, 45.0)
        println("[offset] 北京向东北偏移10km后坐标: ${offsetPoint.latitude}, ${offsetPoint.longitude}")

        // 7. getCenterOfPolygon - 获取多边形中心
        val center = GeoUtils.getCenterOfPolygon(polygonPoints)
        println("[getCenterOfPolygon] 多边形中心点: ${center.latitude}, ${center.longitude}")

        // 8. distanceToSegment - 点到线段距离
        val distanceToSegment = GeoUtils.distanceToSegment(pointA, pointB, pointP)
        println("[distanceToSegment] 点 P 到 AB 线段的最短距离: $distanceToSegment 米")

        // 9. findNearestPair - 最近点对
        val nearestPair = GeoUtils.findNearestPair(pathPoints)
        println("[findNearestPair] 最近点对: $nearestPair")

        // 10. findFarthestPair - 最远点对
        val farthestPair = GeoUtils.findFarthestPair(pathPoints)
        println("[findFarthestPair] 最远点对: $farthestPair")

        // 11. calculateAllPairsDistance - 所有点对距离
        val allPairs = GeoUtils.calculateAllPairsDistance(pathPoints)
        println("[calculateAllPairsDistance] 所有点对距离:")
        allPairs.forEach { pair ->
            println("  - ${pair.from} <-> ${pair.to}: ${pair.distance} 米")
        }

        // 12. batchDistanceFrom - 批量距离计算
        val distances = GeoUtils.batchDistanceFrom(beijing, pathPoints)
        println("[batchDistanceFrom] 各点到北京的距离列表:")
        distances.forEachIndexed { index, d ->
            println("  - 点${index + 1}: $d 米")
        }

        // 13. isPointInCircle - 点是否在圆内
        val inCircle = GeoUtils.isPointInCircle(beijing, 100000.0, shanghai)
        println("[isPointInCircle] 上海是否在北京100km半径内: $inCircle")

        // 14. arePointsInCircle - 批量判断点是否在圆内
        val pointsInCircle = GeoUtils.arePointsInCircle(beijing, 100000.0, pathPoints)
        println("[arePointsInCircle] 各点是否在北京100km半径内:")
        pointsInCircle.forEachIndexed { index, b ->
            println("  - 点${index + 1}: $b")
        }

        println("——— GeoUtils 测试完成 ———")
    }
}
