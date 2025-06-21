import hos.coordinates.ext.*
import hos.coordinates.CoordinateType
import hos.coordinates.calculator.GeoUtils
import hos.coordinates.geometry.LatLng
import kotlin.test.Test

/**
 * <p>Title: CoordinatesExtTest </p>
 * <p>Description: 用于测试 CoordinatesExt 中扩展函数的功能 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-17 14:30
 * @version : 1.0
 */
class LatLngTest {

    @Test
    fun main() {
        println("——— 开始测试 LatLng 类 ———")

        // 1. 测试构造函数
        val latLng1 = LatLng(39.9042, 116.4074, CoordinateType.WGS84)
        println("[构造函数] 纬度: ${latLng1.latitude}, 经度: ${latLng1.longitude}, 类型: ${latLng1.type}")

        // 2. 测试拷贝构造函数
        val latLng2 = LatLng(latLng1)
        println("[拷贝构造] 纬度: ${latLng2.latitude}, 经度: ${latLng2.longitude}, 类型: ${latLng2.type}")

        // 3. 测试 fromMap 方法
        val map = mapOf(
            "latitude" to "31.2304",
            "longitude" to "121.4737",
            "type" to "wgs84"
        )
        val latLng3 = LatLng.fromMap(map)
        println("[fromMap] 纬度: ${latLng3.latitude}, 经度: ${latLng3.longitude}, 类型: ${latLng3.type}")

        // 4. 测试 latitude6 和 longitude6
        val latLng4 = LatLng(39.904212345, 116.407456789, CoordinateType.WGS84)
        println("[保留6位小数] 纬度6位: ${latLng4.latitude6}, 经度6位: ${latLng4.longitude6}")

        // 5. 测试 DMS 格式转换
        val latLng5 = LatLng(39.9042, 116.4074, CoordinateType.WGS84)
        println("[DMS格式] 纬度DMS: ${latLng5.latitudeDMS}, 经度DMS: ${latLng5.longitudeDMS}")

        // 6. 测试 toString()
        println("[toString] $latLng5")

        // 7. 测试 equal6 方法
        val latLng6 = LatLng(39.904212, 116.407457, CoordinateType.WGS84)
        val latLng7 = LatLng(39.904213, 116.407456, CoordinateType.WGS84)
        val isEqual = latLng6.equal6(latLng7)
        println("[equal6比较] latLng6 == latLng7 (六位精度): $isEqual")

        // 8. 测试 toMap()
        val latLng8 = LatLng(40.7128, -74.0060, CoordinateType.GCJ02)
        val mapResult = latLng8.toMap()
        println("[toMap] 转换为 Map: $mapResult")

        // 9. 测试坐标转换
        val latLng9 = LatLng(39.9042, 116.4074, CoordinateType.WGS84)
        val bdLatLng = latLng9.toBaiDu()
        val gcjLatLng = latLng9.toAMap()
        val wgsLatLng = latLng9.toWgs()

//        [坐标转换] WGS84 -> BD09MC: 39.911871289712465, 116.41994243518026
//        [坐标转换] WGS84 -> GCJ02 : 39.90560806345825, 116.41353878670297

        // 116.42004633029816,39.91186533561899
        // 39.91186533561899	116.42004633029816
        println("[坐标转换] WGS84 -> BD09MC: ${bdLatLng.latitude}, ${bdLatLng.longitude}")
        // 116.41364225378803,39.90560334316507
        // 39.90560334316507	116.41364225378803
        println("[坐标转换] WGS84 -> GCJ02 : ${gcjLatLng.latitude}, ${gcjLatLng.longitude}")
        println("[坐标转换] WGS84 -> WGS84 : ${wgsLatLng.latitude}, ${wgsLatLng.longitude}")

        val distanceToGCJ02 = GeoUtils.distanceTo(
            LatLng(39.90560334316507, 116.41364225378803, CoordinateType.GCJ02),
            gcjLatLng
        )
        println("——— GCJ02 距离 ———$distanceToGCJ02")
        val distanceToBD09MC = GeoUtils.distanceTo(
            LatLng(39.91186533561899, 116.42004633029816, CoordinateType.BD09MC),
            bdLatLng
        )
        println("——— BD09MC 距离 ———$distanceToBD09MC")





        println("——— LatLng 类测试完成 ———")
    }


}
