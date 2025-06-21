package hos.coordinates.converter

/**
 * 坐标转换统一接口
 */
interface CoordinateConverter {
    fun convert(lat: Double, lon: Double): Pair<Double, Double>
}


object BD09MCToWGS84Converter : CoordinateConverter {
    override fun convert(lat: Double, lon: Double): Pair<Double, Double> {
        val (gcjLat, gcjLon) = BD09MCTransformer.bd09mcToGcj02(lat, lon)
        return GCJ02OffsetAlgorithm.reverse(gcjLat, gcjLon)
    }
}

object WGS84ToGCJ02Converter : CoordinateConverter {
    override fun convert(lat: Double, lon: Double): Pair<Double, Double> {
        return GCJ02OffsetAlgorithm.offset(lat, lon)
    }
}
object GCJ02ToWGS84Converter : CoordinateConverter {
    override fun convert(lat: Double, lon: Double): Pair<Double, Double> {
        return GCJ02OffsetAlgorithm.reverse(lat, lon)
    }
}
object GCJ02ToBD09MCConverter : CoordinateConverter {
    override fun convert(lat: Double, lon: Double): Pair<Double, Double> {
        return BD09MCTransformer.gcj02ToBd09mc(lat, lon)
    }
}


object BD09MCToGCJ02Converter : CoordinateConverter {
    override fun convert(lat: Double, lon: Double): Pair<Double, Double> {
        return BD09MCTransformer.bd09mcToGcj02(lat, lon)
    }
}

object WGS84ToBD09MCConverter : CoordinateConverter {
    override fun convert(lat: Double, lon: Double): Pair<Double, Double> {
        val (gcjLat, gcjLon) = GCJ02OffsetAlgorithm.offset(lat, lon)
        return BD09MCTransformer.gcj02ToBd09mc(gcjLat, gcjLon)
    }
}
