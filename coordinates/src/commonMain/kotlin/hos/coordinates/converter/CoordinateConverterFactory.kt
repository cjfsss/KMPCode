package hos.coordinates.converter

/**
 * <p>Title: CoordinateConverterFactory </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 11:36
 * @version : 1.0
 */

import hos.coordinates.CoordinateType

object CoordinateConverterFactory {

    fun getConverter(from: CoordinateType, to: CoordinateType): CoordinateConverter? {
        return when (from to to) {
            (CoordinateType.WGS84 to CoordinateType.GCJ02) -> WGS84ToGCJ02Converter
            (CoordinateType.GCJ02 to CoordinateType.WGS84) -> GCJ02ToWGS84Converter
            (CoordinateType.GCJ02 to CoordinateType.BD09MC) -> GCJ02ToBD09MCConverter
            (CoordinateType.BD09MC to CoordinateType.GCJ02) -> BD09MCToGCJ02Converter
            (CoordinateType.WGS84 to CoordinateType.BD09MC) -> WGS84ToBD09MCConverter
            (CoordinateType.BD09MC to CoordinateType.WGS84) -> BD09MCToWGS84Converter
            else -> null
        }
    }
}
