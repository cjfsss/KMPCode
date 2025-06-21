import hos.coordinates.CoordinateType
import hos.coordinates.ext.buildLatLngBounds
import hos.coordinates.ext.center
import hos.coordinates.geometry.LatLng
import hos.coordinates.geometry.LatLngBounds
import kotlin.test.Test

/**
 * <p>Title: LatLngBoundsTest </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 17:02
 * @version : 1.0
 */
class LatLngBoundsTest {
    @Test
    fun main() {
        println("——— 开始测试 LatLngBounds 类 ———")

        // 定义一些测试点
        val beijingWGS84 = LatLng(39.9042, 116.4074, CoordinateType.WGS84)
        val shanghaiWGS84 = LatLng(31.2304, 121.4737, CoordinateType.WGS84)

        val beijingGCJ02 = beijingWGS84.toAMap()
        val shanghaiGCJ02 = shanghaiWGS84.toAMap()

        val beijingBD09MC = beijingWGS84.toBaiDu()
        val shanghaiBD09MC = shanghaiWGS84.toBaiDu()

        val boundsWGS84 = LatLngBounds(beijingWGS84, shanghaiWGS84)
        val boundsGCJ02 = LatLngBounds(beijingGCJ02, shanghaiGCJ02)
        val boundsBD09MC = LatLngBounds(beijingBD09MC, shanghaiBD09MC)

        println("\n[1] 构造函数测试")
        println("西南角: $beijingWGS84")
        println("东北角: $shanghaiWGS84")
        println("边界类型: ${boundsWGS84.type}")

        println("\n[2] 拷贝构造函数测试")
        val copyBounds = boundsWGS84.copy()
        println("拷贝后的西南角: ${copyBounds.southwest}")
        println("拷贝后的东北角: ${copyBounds.northeast}")
        println("拷贝后的坐标类型: ${copyBounds.type}")

        println("\n[3] toBaiDu 转换测试")
        val convertedBD = boundsWGS84.toBaiDu()
        println("转换后的边界类型: ${convertedBD.type}")
        println("转换后的西南角: ${convertedBD.southwest}")
        println("转换后的东北角: ${convertedBD.northeast}")

        println("\n[4] toAMap 转换测试")
        val convertedAMap = boundsWGS84.toAMap()
        println("转换后的边界类型: ${convertedAMap.type}")
        println("转换后的西南角: ${convertedAMap.southwest}")
        println("转换后的东北角: ${convertedAMap.northeast}")

        println("\n[5] toWgs 转换测试")
        val convertedWGS84 = boundsGCJ02.toWgs()
        println("转换后的边界类型: ${convertedWGS84.type}")
        println("转换后的西南角: ${convertedWGS84.southwest}")
        println("转换后的东北角: ${convertedWGS84.northeast}")

        println("\n[6] contains 方法测试")
        val inPoint = LatLng(35.0, 120.0, CoordinateType.WGS84)
        val outPoint = LatLng(20.0, 100.0, CoordinateType.WGS84)
        println("点 $inPoint 是否在边界内: ${boundsWGS84.contains(inPoint)}")
        println("点 $outPoint 是否在边界内: ${boundsWGS84.contains(outPoint)}")
        println("不同坐标系点是否被识别为边界外: ${boundsWGS84.contains(beijingBD09MC)}")

        println("\n[7] containsAll 方法测试")
        val pointsInside = listOf(
            LatLng(35.0, 120.0, CoordinateType.WGS84),
            LatLng(32.0, 118.0, CoordinateType.WGS84)
        )
        val pointsOutside = listOf(
            LatLng(20.0, 100.0, CoordinateType.WGS84),
            LatLng(35.0, 120.0, CoordinateType.WGS84)
        )
        println("pointsInside 是否全部包含: ${boundsWGS84.containsAll(pointsInside)}")
        println("pointsOutside 是否全部包含: ${boundsWGS84.containsAll(pointsOutside)}")

        println("\n[8] containsAny 方法测试")
        val allOutside = listOf(
            LatLng(20.0, 100.0, CoordinateType.WGS84),
            LatLng(25.0, 105.0, CoordinateType.WGS84)
        )
        val someInside = listOf(
            LatLng(35.0, 120.0, CoordinateType.WGS84),
            LatLng(20.0, 100.0, CoordinateType.WGS84)
        )
        println("allOutside 是否任意包含: ${boundsWGS84.containsAny(allOutside)}")
        println("someInside 是否任意包含: ${boundsWGS84.containsAny(someInside)}")

        println("\n[9] intersects 方法测试")
        val overlapBounds = buildLatLngBounds {
            adds(
                LatLng(35.0, 118.0, CoordinateType.WGS84),
                LatLng(40.0, 120.0, CoordinateType.WGS84)
            )
        }
        val noOverlapBounds = buildLatLngBounds {
            adds(
                LatLng(10.0, 100.0, CoordinateType.WGS84),
                LatLng(20.0, 110.0, CoordinateType.WGS84)
            )
        }
        println("overlapBounds 是否相交: ${boundsWGS84.intersects(overlapBounds)}")
        println("noOverlapBounds 是否相交: ${boundsWGS84.intersects(noOverlapBounds)}")

        println(
            "不同坐标类型边界是否相交: ${
                boundsWGS84.intersects(
                    buildLatLngBounds {
                        add(beijingWGS84)
                        add(shanghaiWGS84)
                    }
                )
            }"
        )

        println("\n[10] getCorners 获取四个角点测试")
        val corners = boundsWGS84.getCorners()
        corners.forEachIndexed { index, latLng ->
            println("角点 ${index + 1}: $latLng")
        }

        println("\n[11] toMap 方法测试")
        val map = boundsWGS84.toMap()
        println("toMap 输出:")
        println("SW Latitude: ${(map["sw"] as Map<*, *>)["latitude"]}")
        println("SW Longitude: ${(map["sw"] as Map<*, *>)["longitude"]}")
        println("SW Type: ${(map["sw"] as Map<*, *>)["type"]}")
        println("NE Latitude: ${(map["ne"] as Map<*, *>)["latitude"]}")
        println("NE Longitude: ${(map["ne"] as Map<*, *>)["longitude"]}")
        println("NE Type: ${(map["ne"] as Map<*, *>)["type"]}")

        println("\n[12] toString 方法测试")
        println("toString 输出: $boundsWGS84")

        println("\n[13] Builder 构建边界测试")
        val builtBounds = buildLatLngBounds {
            add(beijingWGS84)
                .add(shanghaiWGS84)
        }
        println("构建的西南角: ${builtBounds.southwest}")
        println("构建的东北角: ${builtBounds.northeast}")
        println("构建的坐标类型: ${builtBounds.type}")

        println("\n[14] Builder 添加多个点后构建边界测试")
        val extraPoints = listOf(
            LatLng(35.0, 118.0, CoordinateType.WGS84),
            LatLng(32.0, 119.0, CoordinateType.WGS84)
        )
        val extendedBounds = buildLatLngBounds {
            addAll(extraPoints)
                .add(beijingWGS84)
                .add(shanghaiWGS84)
        }
        println("扩展构建的西南角: ${extendedBounds.southwest}")
        println("扩展构建的东北角: ${extendedBounds.northeast}")
        println("扩展构建的坐标类型: ${extendedBounds.type}")

        println("\n[15] toLatLngCollection 测试")
        val collection = boundsWGS84.toLatLngCollection()
        println("生成的 LatLngCollection 内容:")
        collection.points.forEach {
            println(" - $it")
        }

        println("\n——— LatLngBounds 类测试完成 ———")

    }

}