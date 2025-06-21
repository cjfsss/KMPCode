package hos.coordinates.calculator

import hos.coordinates.CoordinateType
import hos.coordinates.geometry.LatLng
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * <p>Title: GeoExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 15:06
 * @version : 1.0
 */


// 将角度转换为弧度
internal fun Double.toRadians(): Double = this * PI / 180.0

// 辅助函数：弧度转角度
internal fun Double.toDegrees(): Double = this * 180.0 / PI

// 创建一个Pair对象，包含纬度和经度的弧度值
internal fun LatLng.radians() = Pair(latitude.toRadians(), longitude.toRadians())

// ————————————————————————
// 🌐 辅助函数：将经纬度转换为三维单位向量
internal fun latLngToVector(latlng: LatLng): Vector3D {
    val (lat, lon) = latlng.radians()
    val clat = cos(lat)
    return Vector3D(
        x = clat * cos(lon),
        y = clat * sin(lon),
        z = sin(lat)
    )
}

// ————————————————————————
// 🌐 辅助函数：将三维向量转回经纬度
internal fun vectorToLatLng(vector: Vector3D): LatLng {
    val norm = sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z)
    val x = vector.x / norm
    val y = vector.y / norm
    val z = vector.z / norm

    val lat = atan2(z, sqrt(x * x + y * y)).toDegrees()
    val lon = atan2(y, x).toDegrees()

    return LatLng(lat, lon, CoordinateType.WGS84)
}

// ————————————————————————
// 📐 向量运算类
data class Vector3D(val x: Double, val y: Double, val z: Double)

// 向量减法
internal fun vectorSubtract(v1: Vector3D, v2: Vector3D): Vector3D {
    return Vector3D(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z)
}

// 向量加法
internal fun vectorAdd(v1: Vector3D, v2: Vector3D): Vector3D {
    return Vector3D(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z)
}

// 向量点积
internal fun vectorDot(v1: Vector3D, v2: Vector3D): Double {
    return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
}

// 向量数乘
internal fun vectorScale(v: Vector3D, scale: Double): Vector3D {
    return Vector3D(v.x * scale, v.y * scale, v.z * scale)
}

// 向量归一化
internal fun normalizeVector(v: Vector3D): Vector3D {
    val length = sqrt(v.x.pow(2) + v.y.pow(2) + v.z.pow(2))
    return Vector3D(v.x / length, v.y / length, v.z / length)
}
