import hos.coordinates.geometry.LatLng
import hos.location.LocationData
import hos.location.buildLocationData
import hos.coordinates.CoordinateType
import hos.coordinates.ext.buildLatLngBounds
import hos.json.JsonConverter
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import kotlin.test.Test

/**
 * <p>Title: LocationDataTest </p>
 * <p>Description: 测试 LocationData 类的功能与构建逻辑 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-18 19:00
 * @version : 1.0
 */
class LocationDataTest {

    @Test
    fun testAllFunctions() {
        println("——— 开始测试 LocationData 功能 ———")

        // 1. 构建一个基础 LocationData 对象（使用 buildLocationData DSL）
        val location = buildLocationData(
            LatLng(39.9042, 116.4074, CoordinateType.WGS84)
        ) {
            provider = "GPS"
            speed = 10.5f
            direction = 45.0f
            accuracy = 10.0f
            altitude = 50.0
            satellitesNum = 7
            time = Clock.System.now().toEpochMilliseconds()
            header = 35.0
            addAddress("北京市")
            addAddress("海淀区")
            city = "北京市"
            cityCode = "010"
            country = "中国"
            province = "北京市"
            thoroughfare = "中关村大街"
        }

        val buildLatLngBounds = buildLatLngBounds {
            add(39.9042, 116.4074)
            add(39.0042, 116.0074)
        }

        // 2. 打印构建结果
        println("[构建] 构建的 LocationData 内容:")
        println("  - 坐标: ${location.latLng.latitude}, ${location.latLng.longitude}")
        println("  - 类型: ${location.getType()}")
        println("  - 提供商: ${location.provider}")
        println("  - 速度: ${location.speed} m/s")
        println("  - 方向: ${location.direction} 度")
        println("  - 精度: ±${location.accuracy} 米")
        println("  - 海拔: ${location.altitude} 米")
        println("  - 卫星数: ${location.satellitesNum}")
        println("  - 时间戳: ${location.time}")
        println("  - 头部方向: ${location.header} 度")
        println("  - 地址: ${location.address?.joinToString(", ")}")
        println("  - 城市: ${location.city}")
        println("  - 城市区号: ${location.cityCode}")
        println("  - 国家: ${location.country}")
        println("  - 省份: ${location.province}")
        println("  - 街道: ${location.thoroughfare}")

        // 3. toMap 测试
        val map = location.toMap()
        println("\n[toMap] 转换为 Map 结构:")
        map.forEach { (key, value) ->
            println("  - $key: $value")
        }

        val jsonLatLng = LatLng(39.9042, 116.4074, CoordinateType.WGS84).toJson()
        // 4. toJson 测试
        val json = location.toJson()
        println("\n[toJson] JSON 输出:")
        println("  ${location.toJson()}")
        println("  $jsonLatLng")
        println("  ${buildLatLngBounds.toJson()}")
        println("  ${CoordinateType.WGS84.toJson()}")
        val type = Json.decodeFromString<CoordinateType>(CoordinateType.WGS84.toJson())
        println("  $type")

        // 5. parseJsonToMap 测试
        println("\n[parseJsonToMap] JSON 输出:")
        println("  ${JsonConverter.parseJsonToMap(json)}")

        // 5. fromJson 测试
        val decodeFromString = Json.decodeFromString<LocationData>(json)
        println("\n[decodeFromString] 从 JSON 解析后的数据:$decodeFromString")

        val parsed = LocationData.fromMap(map)
        println("\n[fromJson] 从 JSON 解析后的数据:")

        println("  - 坐标: ${parsed.latLng.latitude}, ${parsed.latLng.longitude}")
        println("  - 类型: ${parsed.getType()}")
        println("  - 提供商: ${parsed.provider}")
        println("  - 速度: ${parsed.speed} m/s")
        println("  - 方向: ${parsed.direction} 度")
        println("  - 精度: ±${parsed.accuracy} 米")
        println("  - 海拔: ${parsed.altitude} 米")
        println("  - 卫星数: ${parsed.satellitesNum}")
        println("  - 时间戳: ${parsed.time}")
        println("  - 头部方向: ${parsed.header} 度")
        println("  - 地址: ${parsed.address?.joinToString(", ")}")
        println("  - 城市: ${parsed.city}")
        println("  - 城市区号: ${parsed.cityCode}")
        println("  - 国家: ${parsed.country}")
        println("  - 省份: ${parsed.province}")
        println("  - 街道: ${parsed.thoroughfare}")

        // 6. newLocationData 测试
        val updated = location.newLocationData {
            speed = 15.0f
            addAddress("朝阳区")
        }

        println("\n[newLocationData] 更新后的新对象:")
        println("  - 速度: ${updated.speed} m/s")
        println("  - 地址: ${updated.address?.joinToString(", ")}")

        println("——— LocationData 测试完成 ———")
    }
}
