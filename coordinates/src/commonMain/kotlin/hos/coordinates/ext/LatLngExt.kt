package hos.coordinates.ext

import hos.coordinates.CoordinateType
import hos.coordinates.calculator.GeoCalculator
import hos.coordinates.calculator.VincentyCalculator
import hos.coordinates.converter.CoordinateConverterFactory
import hos.coordinates.geometry.LatLng
import hos.coordinates.geometry.LatLngBounds
import hos.coordinates.geometry.LatLngCollection

/**
 * <p>Title: LatLngExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 15:01
 * @version : 1.0
 */


/**
 * 将LatLng对象从其当前坐标系转换为目标坐标系
 *
 * @param targetType 目标坐标系，默认为WGS84
 * @return 转换后的LatLng对象
 */
fun LatLng.convertTo(targetType: CoordinateType = CoordinateType.WGS84): LatLng {
    if (type == targetType) return this

    val converter = CoordinateConverterFactory.getConverter(type, targetType)
        ?: error("不支持从 $type 到 $targetType 的转换")

    val (newLat, newLon) = converter.convert(latitude, longitude)
    return LatLng(newLat, newLon, targetType)
}

/**
 * 将LatLng列表中的所有点转换为目标坐标系
 *
 * @param targetType 目标坐标系
 * @return 转换后的LatLng列表
 */
fun List<LatLng>.convertAllTo(targetType: CoordinateType): List<LatLng> {
    return map { it.convertTo(targetType) }
}

/**
 * 使用指定算法偏移点
 *
 * @param distance 偏移距离
 * @param bearing 偏移方位角
 * @param calculator 地理计算算法，默认使用Vincenty算法
 * @return 偏移后的LatLng对象
 */
fun LatLng.offset(
    distance: Double,
    bearing: Double,
    calculator: GeoCalculator = VincentyCalculator
): LatLng {
    return calculator.offset(this, distance, bearing)
}

/**
 * 计算当前点与目标点之间的距离
 *
 * @param target 目标LatLng对象
 * @param calculator 地理计算算法，默认使用Vincenty算法
 * @return 两点之间的距离
 */
fun LatLng.distanceTo(
    target: LatLng,
    calculator: GeoCalculator = VincentyCalculator
): Double {
    return calculator.distanceTo(this, target)
}

/**
 * 计算LatLngBounds对象的中心点
 *
 * @param calculator 地理计算算法，默认使用Vincenty算法
 * @return 中心点的LatLng对象
 */
fun LatLngBounds.center(calculator: GeoCalculator = VincentyCalculator): LatLng {
    return calculator.getCenterOfPolygon(getCorners())
}

/**
 * 计算LatLng集合的中心点
 *
 * @param calculator 地理计算算法，默认使用Vincenty算法
 * @return 中心点的LatLng对象
 */
fun LatLngCollection.center(calculator: GeoCalculator = VincentyCalculator): LatLng {
    return calculator.getCenterOfPolygon(points)
}

/**
 * 计算LatLngBounds对象的面积
 *
 * @param calculator 地理计算算法，默认使用Vincenty算法
 * @return 面积
 */
fun LatLngBounds.calculateArea(calculator: GeoCalculator = VincentyCalculator): Double {
    return calculator.calculateArea(getCorners())
}

/**
 * 计算LatLng集合的面积
 *
 * @param calculator 地理计算算法，默认使用Vincenty算法
 * @return 面积
 */
fun LatLngCollection.calculateArea(calculator: GeoCalculator = VincentyCalculator): Double {
    return calculator.calculateArea(points)
}

