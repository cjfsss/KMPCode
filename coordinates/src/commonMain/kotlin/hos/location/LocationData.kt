package hos.location

import hos.coordinates.CoordinateType
import hos.coordinates.geometry.LatLng
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 *
 * Title: LocationData
 *
 * Description:
 *
 * Company: www.hos.com
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2023-12-10 21:14
 */

/**
 * 表示一次定位结果的数据模型
 */
@Serializable
class LocationData private constructor(
    val latLng: LatLng,
    val provider: String?,
    val speed: Float = 0f,
    val direction: Float = 0f,
    val accuracy: Float = 0f,
    val altitude: Double = 0.0,
    val satellitesNum: Int = 0,
    val time: Long = 0L,
    val header: Double = 0.0,
    val address: List<String>? = null,
    val city: String? = null,
    val cityCode: String? = null,
    val country: String? = null,
    val province: String? = null,
    val thoroughfare: String? = null
) {

    /**
     * 获取当前坐标的类型（WGS84 / GCJ02 / BD09MC）
     */
    fun getType() = latLng.type


    /**
     * 构建新的构建器
     */
    fun newLocationData(block: Builder.() -> Unit): LocationData {
        return buildLocationData(latLng) {
            provider = this@LocationData.provider
            speed = this@LocationData.speed
            direction = this@LocationData.direction
            accuracy = this@LocationData.accuracy
            altitude = this@LocationData.altitude
            satellitesNum = this@LocationData.satellitesNum
            time = this@LocationData.time
            header = this@LocationData.header
            address = this@LocationData.address
            city = this@LocationData.city
            cityCode = this@LocationData.cityCode
            country = this@LocationData.country
            province = this@LocationData.province
            thoroughfare = this@LocationData.thoroughfare
            block()
        }
    }

    /**
     * 转换为 Map 结构用于序列化
     */
    fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        latLng.let {
            map.putAll(it.toMap())
            map["json"] = it.toJson()
        }
        map["provider"] = provider
        map["accuracy"] = accuracy
        map["altitude"] = altitude
        map["direction"] = direction
        map["address"] = address
        map["speed"] = speed
        map["satellitesNum"] = satellitesNum
        map["time"] = time
        map["header"] = header
        map["cityCode"] = cityCode
        map["city"] = city
        map["country"] = country
        map["thoroughfare"] = thoroughfare
        map["province"] = province

        return map
    }

    /**
     * 转换为 JSON 字符串
     */
    fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun toString(): String {
        return toMap().toString()
    }

    companion object {


        /**
         * 从 Map 中解析出 LocationData 对象
         */
        fun fromMap(json: Map<String, Any?>): LocationData {
            val latLngMap = json["latLng"] as? Map<String, Any?>
            if (latLngMap != null) {

            }
            val type = json["type"]
            if (type == null){

            }

            val latitude = json["latitude"] as? Double
                ?: throw IllegalArgumentException("Missing required field 'latitude'")
            val longitude = json["longitude"] as? Double
                ?: throw IllegalArgumentException("Missing required field 'longitude'")

            val coordType = type as? CoordinateType
                ?: if (type is String) {
                    CoordinateType.form(type)
                } else {
                    CoordinateType.WGS84
                }

            val latLng = LatLng(latitude, longitude, coordType)

            val headerValue = (json["header"] as? Number)?.toDouble() ?: 0.0
            val directionFinal = (json["direction"] as? Float?) ?: 0f
            val finalDirection = if (directionFinal == 0f) {
                headerValue.toFloat()
            } else {
                directionFinal
            }

            return buildLocationData(latLng) {
                address = (json["address"] as List<*>?)?.filterIsInstanceOrNulls<String>()
                direction = finalDirection
                header = headerValue
                speed = (json["speed"] as? Number)?.toFloat() ?: 0f
                provider = json["provider"] as? String
                accuracy = (json["accuracy"] as? Float) ?: 0f
                altitude = (json["altitude"] as? Double) ?: 0.0
                satellitesNum = (json["satellitesNum"] as? Int) ?: 0
                time = (json["time"] as? Long) ?: 0L
                city = json["city"] as? String
                cityCode = json["cityCode"] as? String
                country = json["country"] as? String
                province = json["province"] as? String
                thoroughfare = json["thoroughfare"] as? String
            }
        }

        /**
         * 构建 LocationData 实例的工厂方法
         */
        fun buildLocationData(latLng: LatLng, block: Builder.() -> Unit): LocationData {
            val builder = Builder(latLng)
            builder.block()
            return builder.build()
        }

    }

    /**
     * Builder 模式：用于构建 LocationData 实例
     */
    class Builder constructor(private val latLng: LatLng) {

        var speed = 0f
        var direction = 0f
        var accuracy = 0f
        var altitude = 0.0
        var satellitesNum = 0
        var time: Long = 0
        var header = 0.0
        var provider: String? = null
        var address: List<String>? = null
        var city: String? = null
        var cityCode: String? = null
        var country: String? = null
        var province: String? = null
        var thoroughfare: String? = null

//        fun provider(provider: String?): Builder {
//            this.provider = provider
//            return this
//        }
//
//        fun speed(speed: Float): Builder {
//            this.speed = speed
//            return this
//        }
//
//        fun direction(direction: Float): Builder {
//            this.direction = direction
//            return this
//        }
//
//        fun accuracy(accuracy: Float): Builder {
//            this.accuracy = accuracy
//            return this
//        }
//
//        fun altitude(altitude: Double): Builder {
//            this.altitude = altitude
//            return this
//        }
//
//        fun satellitesNum(num: Int): Builder {
//            this.satellitesNum = num
//            return this
//        }
//
//        fun time(time: Long): Builder {
//            this.time = time
//            return this
//        }
//
//        fun header(header: Double): Builder {
//            this.header = header
//            return this
//        }
//
//        fun address(list: List<String>?): Builder {
//            this.address = list
//            return this
//        }

        fun addAddress(item: String): Builder {
            if (this.address == null) {
                this.address = mutableListOf()
            }
            (this.address as MutableList<String>).add(item)
            return this
        }

//        fun city(city: String?): Builder {
//            this.city = city
//            return this
//        }
//
//        fun cityCode(code: String?): Builder {
//            this.cityCode = code
//            return this
//        }
//
//        fun country(country: String?): Builder {
//            this.country = country
//            return this
//        }
//
//        fun province(province: String?): Builder {
//            this.province = province
//            return this
//        }
//
//        fun thoroughfare(thoroughfare: String?): Builder {
//            this.thoroughfare = thoroughfare
//            return this
//        }

        fun build(): LocationData {
            return LocationData(
                provider = provider,
                latLng = latLng,
                speed = speed,
                direction = direction,
                accuracy = accuracy,
                altitude = altitude,
                satellitesNum = satellitesNum,
                time = time,
                header = header,
                address = address,
                city = city,
                cityCode = cityCode,
                country = country,
                province = province,
                thoroughfare = thoroughfare
            )
        }
    }
}

// 扩展函数：过滤列表中的非 String 类型元素
inline fun <reified T> List<*>.filterIsInstanceOrNulls(): List<T>? {
    if (isEmpty()) return null
    val result = mapNotNull { it as? T }
    return if (result.isEmpty()) null else result
}
