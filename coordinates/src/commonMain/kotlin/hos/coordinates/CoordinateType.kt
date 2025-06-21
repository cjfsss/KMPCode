package hos.coordinates

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
 * <p>Title: CoordinateType </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 20:39
 * @version : 1.0
 */
@Serializable(with = CoordinateTypeSerializer::class)
enum class CoordinateType(val type: String, val id: Int) {

    @Serializable(with = CoordinateTypeSerializer::class)
    BD09MC("bd09mc", 102100),  //（百度墨卡托坐标）

    @Serializable(with = CoordinateTypeSerializer::class)
    GCJ02("gcj02", 102113),  //（经国测局加密的坐标）

    @Serializable(with = CoordinateTypeSerializer::class)
    WGS84("wgs84", 4326),  //（gps获取的原始坐标）

    @Serializable(with = CoordinateTypeSerializer::class)
    COMMON("common", 102113); //（google地图、高德地图、腾讯地图、搜狗地图）


    companion object {
        /**
         * 从坐标名称获取坐标类型
         * @param typeName 坐标名称
         * @return 坐标类型
         */
        fun form(typeName: String?): CoordinateType {
            return when (typeName) {
                BD09MC.type -> BD09MC
                GCJ02.type -> GCJ02
                COMMON.type -> COMMON
                else -> WGS84
            }
        }
    }

    fun toJson(): String {
        return Json.encodeToString(this)
    }
}

class CoordinateTypeSerializer : KSerializer<CoordinateType> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CoordinateType") {
            element<String>("type")
            element<Int>("id")
        }

    override fun serialize(
        encoder: Encoder,
        value: CoordinateType
    ) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.type)
            encodeIntElement(descriptor, 1, value.id)
        }
    }

    override fun deserialize(decoder: Decoder): CoordinateType {
        return decoder.decodeStructure(descriptor) {
            var type = ""
            var id = 0
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> type = decodeStringElement(descriptor, index)
                    1 -> id = decodeIntElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index $index")
                }
            }
            CoordinateType.form(type)
        }
    }
}