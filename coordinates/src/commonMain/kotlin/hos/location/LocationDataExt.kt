package hos.location

import hos.coordinates.geometry.LatLng

/**
 * <p>Title: LocationDataExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-17 8:45
 * @version : 1.0
 */
inline fun buildLocationData(
    latLng: LatLng,
    block: LocationData.Builder.() -> Unit
): LocationData {
    val builder = LocationData.Builder(latLng)
    builder.block()
    return builder.build()
}