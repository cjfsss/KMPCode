package hos.coordinates.calculator

import hos.coordinates.geometry.LatLng

/**
 * <p>Title: LatLngDistance </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 10:17
 * @version : 1.0
 */
data class LatLngDistance(
    val from: LatLng,
    val to: LatLng,
    val distance: Double
)