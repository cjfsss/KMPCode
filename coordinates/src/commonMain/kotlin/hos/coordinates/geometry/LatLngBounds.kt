package hos.coordinates.geometry

import hos.coordinates.CoordinateType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * <p>Title: LatLngBounds </p>
 * <p>Description: 表示由两个对角点定义的地理边界矩形</p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-18 10:00
 * @version : 1.0
 */
/**
 * 表示由两个对角点定义的地理边界矩形（西南角和东北角）
 *
 * @param southwest 地理矩形的西南角坐标
 * @param northeast 地理矩形的东北角坐标
 * @param type 坐标类型，表示当前边界所属的坐标系，默认与西南角一致
 */
@Serializable
class LatLngBounds constructor(
    val southwest: LatLng,
    val northeast: LatLng,
    val type: CoordinateType
) {

    constructor(
        southwest: LatLng,
        northeast: LatLng,
    ) : this(
        southwest,
        northeast,
        southwest.type
    )

    /**
     * 私有构造函数，用于从已有对象复制创建新实例
     */
    constructor(bounds: LatLngBounds) : this(
        bounds.southwest,
        bounds.northeast,
        bounds.type
    )

    /**
     * 将当前边界转换为百度坐标系（BD09MC）下的边界
     *
     * @return 转换后的 LatLngBounds 对象，坐标类型为 BD09MC
     */
    fun toBaiDu(): LatLngBounds {
        return LatLngBounds(southwest.toBaiDu(), northeast.toBaiDu(), CoordinateType.BD09MC)
    }

    /**
     * 将当前边界转换为高德坐标系（GCJ02）下的边界
     *
     * @return 转换后的 LatLngBounds 对象，坐标类型为 GCJ02
     */
    fun toAMap(): LatLngBounds {
        return LatLngBounds(southwest.toAMap(), northeast.toAMap(), CoordinateType.GCJ02)
    }

    /**
     * 将当前边界转换为 WGS84 坐标系下的边界
     *
     * @return 转换后的 LatLngBounds 对象，坐标类型为 WGS84
     */
    fun toWgs(): LatLngBounds {
        return LatLngBounds(southwest.toWgs(), northeast.toWgs(), CoordinateType.WGS84)
    }

    /**
     * 获取当前对象的字符串表示形式
     *
     * @return 字符串格式：LatLngBounds(southwest=..., northeast=..., type=...)
     */
    override fun toString(): String {
        return "LatLngBounds(southwest=$southwest, northeast=$northeast, type=${type.type})"
    }

    /**
     * 获取矩形的四个角点
     *
     * @return 包含西南、东南、东北、西北四个角点的列表
     */
    fun getCorners(): List<LatLng> {
        val southwest = this.southwest
        val northeast = this.northeast

        val northwest = LatLng(northeast.latitude, southwest.longitude, southwest.type)
        val southeast = LatLng(southwest.latitude, northeast.longitude, southwest.type)

        return listOf(
            southwest,  // 西南角
            southeast,  // 东南角
            northeast,  // 东北角
            northwest   // 西北角
        )
    }

    /**
     * 判断当前边界是否包含指定的经纬度点
     *
     * @param point 待判断的点
     * @return 如果包含该点则返回 true，否则返回 false
     */
    fun contains(point: LatLng): Boolean {
        return point.latitude >= southwest.latitude &&
                point.latitude <= northeast.latitude &&
                point.longitude >= southwest.longitude &&
                point.longitude <= northeast.longitude &&
                point.type == this.type
    }

    /**
     * 判断当前边界是否包含给定的所有点
     *
     * @param points 点集合
     * @return 所有点都在边界内返回 true，否则返回 false
     */
    fun containsAll(points: List<LatLng>): Boolean {
        return points.all { contains(it) }
    }

    /**
     * 判断当前边界是否包含任意一个点
     *
     * @param points 点集合
     * @return 只要有一个点在边界内就返回 true，否则返回 false
     */
    fun containsAny(points: List<LatLng>): Boolean {
        return points.any { contains(it) }
    }

    /**
     * 判断当前边界是否完全包含另一个边界
     *
     * @param other 待比较的边界
     * @return 如果当前边界完全包含另一个边界则返回 true，否则返回 false
     */
    fun contains(other: LatLngBounds): Boolean {
        return other.southwest.latitude >= this.southwest.latitude &&
                other.northeast.latitude <= this.northeast.latitude &&
                other.southwest.longitude >= this.southwest.longitude &&
                other.northeast.longitude <= this.northeast.longitude &&
                other.type == this.type
    }

    /**
     * 判断当前边界是否与另一个边界相交
     *
     * @param other 待比较的边界
     * @return 如果两个边界存在交集则返回 true，否则返回 false
     */
    fun intersects(other: LatLngBounds): Boolean {
        return !(
                other.northeast.latitude < this.southwest.latitude ||
                        other.southwest.latitude > this.northeast.latitude ||
                        other.northeast.longitude < this.southwest.longitude ||
                        other.southwest.longitude > this.northeast.longitude ||
                        other.type != this.type
                )
    }

    /**
     * 将当前对象转换为 Map 数据结构
     *
     * @return 包含 sw（西南角）和 ne（东北角）信息的 Map
     */
    fun toMap(): MutableMap<String, Any> {
        val toMap = mutableMapOf<String, Any>()
        toMap.put("sw", southwest.toMap())
        toMap.put("ne", northeast.toMap())
        return toMap
    }

    /**
     * 将当前对象转换为LatLngCollection类型集合
     *
     * 该函数主要用于将当前对象（假定它包含了地理位置信息）转换为一个LatLngCollection对象
     * 这种转换在需要对地理位置信息进行集合操作时非常有用
     *
     * @return LatLngCollection对象，包含了当前对象的地理位置信息
     */
    fun toLatLngCollection(): LatLngCollection {
        return LatLngCollection(getCorners())
    }


    /**
     * 构建器类，用于通过添加多个点来构建边界矩形
     */
    fun copy(): LatLngBounds {
        return LatLngBounds(this)
    }

    fun toJson(): String {
        return Json.encodeToString(this)
    }
}
