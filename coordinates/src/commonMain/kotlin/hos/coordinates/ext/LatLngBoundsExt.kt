package hos.coordinates.ext

import hos.coordinates.CoordinateType
import hos.coordinates.geometry.LatLngBounds
import hos.coordinates.geometry.LatLngCollection

/**
 * <p>Title: LatLngBoundsExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 17:27
 * @version : 1.0
 */
inline fun buildLatLngBounds(
    type: CoordinateType = CoordinateType.WGS84,
    block: LatLngCollection.() -> Unit
): LatLngBounds {
    return buildLatLngCollection(type, block).toLatLngBounds()
}

inline fun buildLatLngCollection(
    type: CoordinateType = CoordinateType.WGS84,
    block: LatLngCollection.() -> Unit
): LatLngCollection {
    val builder = LatLngCollection(type)
    builder.block()
    return builder
}