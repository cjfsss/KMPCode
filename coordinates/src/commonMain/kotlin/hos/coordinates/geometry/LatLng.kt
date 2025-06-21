package hos.coordinates.geometry

import hos.coordinates.CoordinateType
import hos.coordinates.calculator.GeoCalculator
import hos.coordinates.calculator.VincentyCalculator
import hos.coordinates.ext.convertTo
import hos.coordinates.ext.to6DecimalPlaces
import hos.coordinates.ext.toDMS
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.Json

/**
 * <p>Title: LatLng </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 15:10
 * @version : 1.0
 */
/**
 * 表示一个地理坐标点，包含纬度和经度信息。
 * 该类支持将坐标转换为不同的坐标系，如百度坐标、高德坐标和WGS84坐标。
 *
 * @property latitude 纬度值
 * @property longitude 经度值
 * @property type 坐标类型，表示当前坐标所属的坐标系
 */
@Serializable
class LatLng constructor(
    val latitude: Double,
    val longitude: Double,
    val type: CoordinateType
) {

    /**
     * 拷贝构造函数，用于从已有的 LatLng 对象创建新对象。
     *
     * @param latLng 要拷贝的 LatLng 对象
     */
    constructor(latLng: LatLng) : this(latLng.latitude, latLng.longitude, latLng.type)

    constructor(latitude: Double, longitude: Double) : this(
        latitude,
        longitude,
        CoordinateType.WGS84
    )

    companion object {

        /**
         * 从 Map 数据中解析出经纬度并构建 LatLng 对象。
         *
         * @param map 包含 "latitude" 和 "longitude" 字段的 Map 数据
         * @param coordinateType 可选参数，指定目标坐标类型，默认根据 map 中的 type 字段自动判断
         * @return 构建完成的 LatLng 对象
         */
        fun fromMap(map: Map<String, Any>, coordinateType: CoordinateType? = null): LatLng {
            val latitude = (map["latitude"] as? Double) ?: 0.0
            val longitude = (map["longitude"] as? Double) ?: 0.0
            val type = coordinateType.run {
                val type = map["type"]
                type as? CoordinateType
                    ?: if (type is String) {
                        CoordinateType.form(type)
                    } else {
                        CoordinateType.WGS84
                    }
            }
            return LatLng(latitude, longitude, type)
        }
    }

    /**
     * 将纬度保留 6 位小数的字符串形式（四舍五入处理）
     */
    val latitude6: String get() = latitude.to6DecimalPlaces()

    /**
     * 将经度保留 6 位小数的字符串形式（四舍五入处理）
     */
    val longitude6: String get() = longitude.to6DecimalPlaces()

    /**
     * 将纬度转换为度分秒格式（DMS）字符串
     */
    val latitudeDMS: String get() = latitude.toDMS()

    /**
     * 将经度转换为度分秒格式（DMS）字符串
     */
    val longitudeDMS: String get() = longitude.toDMS()

    /**
     * 返回格式化的坐标字符串，包含度分秒和小数点两种格式
     *
     * @return 格式化后的字符串，例如：
     * Latitude: 39.9042 (39°54'15"), Longitude: 116.4074 (116°24'26"), Type: wgs84
     */
    override fun toString(): String {
        return "Latitude: $latitude6 ($latitudeDMS), Longitude: $longitude6 ($longitudeDMS) , Type: ${type.type}"
    }

    /**
     * 判断两个 LatLng 是否在六位小数精度上相等（使用 epsilon 比较）
     *
     * @param other 另一个 LatLng 对象
     * @return 如果纬度和经度在六位小数上相等，则返回 true，否则返回 false
     */
    fun equal6(other: LatLng): Boolean {
        return latitude6 == other.latitude6 &&
                longitude6 == other.longitude6 && type == other.type
    }

    /**
     * 将当前坐标转换为地图数据格式的 Map 结构
     *
     * @return 包含 id 和 type 的 Map 对象
     */
    fun toMap(): MutableMap<String, Any> {
        val toMap = mutableMapOf<String, Any>()
        toMap.put("type", type)
        toMap.put("latitude", latitude)
        toMap.put("longitude", longitude)
        return toMap
    }

    /**
     * 将当前坐标转换为百度坐标系（BD09MC）
     *
     * @return 转换后的 LatLng 对象，坐标类型为 BD09MC
     */
    fun toBaiDu(): LatLng {
        return convertTo(CoordinateType.BD09MC)
    }

    /**
     * 将当前坐标转换为高德坐标系（GCJ02）
     *
     * @return 转换后的 LatLng 对象，坐标类型为 GCJ02
     */
    fun toAMap(): LatLng {
        return convertTo(CoordinateType.GCJ02)
    }

    /**
     * 将当前坐标转换为 WGS84 坐标系
     *
     * @return 转换后的 LatLng 对象，坐标类型为 WGS84
     */
    fun toWgs(): LatLng {
        return convertTo(CoordinateType.WGS84)
    }

    fun toJson(): String {
        return Json.encodeToString(this)
    }
}

