package hos.coordinates.calculator

import hos.coordinates.geometry.LatLng
import kotlin.math.*

/**
 * <p>Title: VincentyCalculator </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 13:58
 * @version : 1.0
 */
object VincentyCalculator : GeoCalculator {

    // WGS84 椭球参数
    private const val WGS84_A = 6378137.0             // 长半轴（米）
    private const val WGS84_B = 6356752.3142          // 短半轴（米）
    private const val WGS84_F = 1.0 / 298.257223563    // 扁率
    private const val VINCENTY_EPS = 1e-12
    private const val MAX_ITERATIONS = 1000

    override fun distanceTo(a: LatLng, b: LatLng): Double {
        requireSameCoordinateType(a, b)
        val wgs1 = a.toWgs()
        val wgs2 = b.toWgs()

        val lat1 = wgs1.latitude.toRadians()
        val lon1 = wgs1.longitude.toRadians()
        val lat2 = wgs2.latitude.toRadians()
        val lon2 = wgs2.longitude.toRadians()

        val L = lon2 - lon1
        val U1 = atan((1 - WGS84_F) * tan(lat1))
        val U2 = atan((1 - WGS84_F) * tan(lat2))

        var sinU1 = sin(U1)
        var cosU1 = cos(U1)
        var sinU2 = sin(U2)
        var cosU2 = cos(U2)

        var lambda = L
        var iterLimit = MAX_ITERATIONS

        var sinLambda: Double
        var cosLambda: Double
        var sinSqSigma: Double
        var sinSigma: Double
        var cosSigma: Double
        var sigma: Double
        var sinAlpha: Double
        var cosSqAlpha: Double
        var cos2SigmaM: Double
        var C: Double

        do {
            sinLambda = sin(lambda)
            cosLambda = cos(lambda)
            sinSqSigma =
                (cosU2 * sinLambda).pow(2) + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda).pow(2)
            if (sinSqSigma == 0.0) return 0.0
            sinSigma = sqrt(sinSqSigma)
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda
            sigma = atan2(sinSigma, cosSigma)
            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma
            cosSqAlpha = 1.0 - sinAlpha.pow(2)
            cos2SigmaM = if (cosSqAlpha != 0.0) {
                cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha
            } else {
                0.0
            }
            C = WGS84_F / 16 * cosSqAlpha * (4 + WGS84_F * (4 - 3 * cosSqAlpha))
            val lambdaOrig = lambda
            lambda = L + (1 - C) * WGS84_F * sinAlpha * (
                    sigma + C * sinSigma * (
                            cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM.pow(2))
                            )
                    )
        } while (abs(lambda - lambdaOrig) > VINCENTY_EPS && --iterLimit > 0)

        if (iterLimit == 0) throw RuntimeException("Vincenty 距离计算未收敛")

        val uSquared = cosSqAlpha * (WGS84_A.pow(2) - WGS84_B.pow(2)) / (WGS84_B.pow(2))
        val A =
            1 + uSquared / 16384 * (4096 + uSquared * (-768 + uSquared * (320 - 175 * uSquared)))
        val B = uSquared / 1024 * (256 + uSquared * (-128 + uSquared * (74 - 47 * uSquared)))

        val deltaSigma = B * sinSigma * (
                cos2SigmaM + B / 4 * (
                        cosSigma * (-1 + 2 * cos2SigmaM.pow(2)) -
                                B / 6 * cos2SigmaM * sinSigma * (-3 + 4 * sinSigma.pow(2)) * (-3 + 4 * cos2SigmaM.pow(
                            2
                        ))
                        )
                )

        return WGS84_B * A * (sigma - deltaSigma)
    }

    /**
     * 使用 Vincenty 算法计算闭合多边形的地理面积（单位：平方公里）
     *
     * @param points 闭合多边形顶点列表（建议 WGS84 坐标系）
     * @return 面积（平方公里）
     */
    override fun calculateArea(points: List<LatLng>): Double {
        require(points.size >= 3) { "至少需要三个点才能构成多边形" }

        val a = 6378137.0       // 地球长半轴（米）
        val b = 6356752.3142    // 地球短半轴（米）
        val f = 1 / 298.257223563 // 扁率

        var total = 0.0

        for (i in points.indices) {
            val p1 = points[i].toWgs()
            val p2 = points[(i + 1) % points.size].toWgs()

            val lat1 = p1.latitude.toRadians()
            val lon1 = p1.longitude.toRadians()
            val lat2 = p2.latitude.toRadians()
            val lon2 = p2.longitude.toRadians()

            val L = lon2 - lon1
            val U1 = atan((1 - f) * tan(lat1))
            val U2 = atan((1 - f) * tan(lat2))

            val sinU1 = sin(U1)
            val cosU1 = cos(U1)
            val sinU2 = sin(U2)
            val cosU2 = cos(U2)

            var lambda = L
            var iterLimit = 1000

            var sinLambda: Double
            var cosLambda: Double
            var sinSigma: Double
            var cosSigma: Double
            var sigma: Double
            var sinAlpha: Double
            var cosSqAlpha: Double
            var cos2AlphaM: Double
            var C: Double

            do {
                sinLambda = sin(lambda)
                cosLambda = cos(lambda)

                val sinSqSigma =
                    (cosU2 * sinLambda).pow(2) + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda).pow(2)
                if (sinSqSigma == 0.0) return@calculateArea 0.0 // 两点重合，面积为零

                sinSigma = sqrt(sinSqSigma)
                cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda
                sigma = atan2(sinSigma, cosSigma)
                sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma
                cosSqAlpha = 1.0 - sinAlpha.pow(2)
                cos2AlphaM = if (cosSqAlpha != 0.0) {
                    cos(sigma) - 2 * sinU1 * sinU2 / cosSqAlpha
                } else {
                    0.0
                }
                C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha))
                val lambdaOrig = lambda
                lambda += (1 - C) * f * sinAlpha * (
                        sigma + C * sinSigma * (
                                cos2AlphaM + C * cosSigma * (-1 + 2 * cos2AlphaM.pow(2))
                                )
                        )

            } while (abs(lambda - lambdaOrig) > 1e-10 && --iterLimit > 0)

            if (iterLimit == 0) throw RuntimeException("Vincenty 计算未收敛")

            val uSquared = cosSqAlpha * (a.pow(2) - b.pow(2)) / (b.pow(2))
            val A =
                1 + uSquared / 16384 * (4096 + uSquared * (-768 + uSquared * (320 - 175 * uSquared)))
            val B = uSquared / 1024 * (256 + uSquared * (-128 + uSquared * (74 - 47 * uSquared)))

            val deltaSigma = B * sinSigma * (
                    cosSigma + B / 4 * (
                            cosSigma * (-1 + 2 * cos2AlphaM.pow(2)) -
                                    B / 6 * cos2AlphaM * sinSigma * (-3 + 4 * sinSigma.pow(2)) * (-3 + 4 * cos2AlphaM.pow(
                                2
                            ))
                            )
                    )

            val K = b * (1 - f) * A
            total += K * K * (sigma - deltaSigma) * (1 - cos2AlphaM) * sinAlpha
        }

        val areaKm2 = abs(total / (2 * (1 - f))) / 1_000_000
        return areaKm2
    }


    override fun bearingTo(from: LatLng, to: LatLng): Double {
        requireSameCoordinateType(from, to)
        // 处理两点重合的情况
        if (from.latitude == to.latitude && from.longitude == to.longitude) {
            return 0.0
        }
        val wgs1 = from.toWgs()
        val wgs2 = to.toWgs()

        val lat1 = wgs1.latitude.toRadians()
        val lon1 = wgs1.longitude.toRadians()
        val lat2 = wgs2.latitude.toRadians()
        val lon2 = wgs2.longitude.toRadians()

        val L = lon2 - lon1
        val U1 = atan((1 - WGS84_F) * tan(lat1))
        val U2 = atan((1 - WGS84_F) * tan(lat2))

        var lambda = L
        var iterLimit = MAX_ITERATIONS

        val sinU1 = sin(U1)
        val cosU1 = cos(U1)
        val sinU2 = sin(U2)
        val cosU2 = cos(U2)

        var sinLambda: Double
        var cosLambda: Double
        var sinSqSigma: Double
        var sinSigma: Double
        var cosSigma: Double
        var sigma: Double
        var sinAlpha: Double
        var cosSqAlpha: Double
        var cos2SigmaM: Double
        var C: Double

        do {
            sinLambda = sin(lambda)
            cosLambda = cos(lambda)

            sinSqSigma =
                (cosU2 * sinLambda).pow(2) + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda).pow(2)
            if (sinSqSigma == 0.0) return 0.0

            sinSigma = sqrt(sinSqSigma)
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda
            sigma = atan2(sinSigma, cosSigma)
            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma
            cosSqAlpha = 1.0 - sinAlpha.pow(2)
            cos2SigmaM = if (cosSqAlpha != 0.0) {
                cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha
            } else {
                0.0
            }
            C = WGS84_F / 16 * cosSqAlpha * (4 + WGS84_F * (4 - 3 * cosSqAlpha))
            val lambdaOrig = lambda
            lambda += (1 - C) * WGS84_F * sinAlpha * (
                    sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM.pow(2))))
        } while (abs(lambda - L) > VINCENTY_EPS && --iterLimit > 0)

        if (iterLimit == 0) throw RuntimeException("Vincenty 方位角计算未收敛")

        val bearing = atan2(
            sin(lambda) * cosU2,
            cosU1 * sinU2 - sinU1 * cosU2 * cos(lambda)
        ).toDegrees()

        return (bearing + 360) % 360
    }


    override fun offset(point: LatLng, distance: Double, bearing: Double): LatLng {
        val a = WGS84_A
        val b = WGS84_B
        val f = WGS84_F

        val wgs = point.toWgs()

        val lat = wgs.latitude.toRadians()
        val lon = wgs.longitude.toRadians()
        val angularDistance = distance / a

        val tanU1 = (1 - f) * tan(lat)
        val cosU1 = 1 / sqrt(1 + tanU1.pow(2))
        val sinU1 = tanU1 * cosU1

        val alpha1 = bearing.toRadians()
        val sinAlpha1 = sin(alpha1)
        val cosAlpha1 = cos(alpha1)

        var sinAlpha = cosU1 * sinAlpha1
        val cosSqAlpha = 1.0 - sinAlpha.pow(2)
        val uSq = cosSqAlpha * (a.pow(2) - b.pow(2)) / b.pow(2)

        var sigma1 = atan2(tanU1, cosAlpha1)
        var sinAlpha0 = sinAlpha1 * cosU1
        var cosAlpha0 = sqrt(1 - sinAlpha0.pow(2))

        var sigma = angularDistance
        var sigmaP = 2 * PI
        var iterLimit = MAX_ITERATIONS

        var cos2SigmaM: Double
        var sinSigma: Double
        var cosSigma: Double
        var deltaSigma: Double
        var A: Double
        var B: Double

        do {
            sinSigma = sin(sigma)
            cosSigma = cos(sigma)
            cos2SigmaM = cos(2 * sigma1 + sigma)

            val delta = 2 * cos2SigmaM + cosSigma * (4 * cos2SigmaM.pow(2) - 1)
            val term = (b * sinSigma) / (1 - f)

            A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)))
            B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)))

            deltaSigma = B * sinSigma * (
                    cos2SigmaM + B / 4 * (
                            cosSigma * (-1 + 2 * cos2SigmaM.pow(2)) -
                                    B / 6 * cos2SigmaM * sinSigma * delta
                            )
                    )
        } while (abs(deltaSigma - sigmaP) > VINCENTY_EPS && --iterLimit > 0)

        val tmp = sinU1 * sinU1 + (cosU1 * sinAlpha0).pow(2)
        val lat2 = atan2(
            sinU1 * cos(sigma) + cosU1 * sin(sigma) * cosAlpha1,
            (1 - f) * sqrt(tmp).let { if (it == 0.0) 1e-12 else it }
        )

        val x = cosU1 * sin(sigma) * sinAlpha0
        val y = cosU1 * cos(sigma) - sinU1 * sin(sigma) * cosAlpha1
        val lon2 = lon + atan2(x, y)

        return LatLng(lat2.toDegrees(), lon2.toDegrees(), point.type)
    }


    override fun getCenterOfPolygon(points: List<LatLng>): LatLng {
        require(points.isNotEmpty()) { "点集合不能为空" }

        var sumX = 0.0
        var sumY = 0.0
        var sumZ = 0.0

        for (point in points) {
            val (lat, lon) = point.radians()
            val clat = cos(lat)
            val slat = sin(lat)
            val clon = cos(lon)
            val slon = sin(lon)

            // 地心坐标转换（假设单位球体）
            val x = clat * clon
            val y = clat * slon
            val z = slat

            sumX += x
            sumY += y
            sumZ += z
        }

        val avgX = sumX / points.size
        val avgY = sumY / points.size
        val avgZ = sumZ / points.size

        // 归一化向量（确保长度为 1）
        val length = sqrt(avgX.pow(2) + avgY.pow(2) + avgZ.pow(2))
        val normalizedX = avgX / length
        val normalizedY = avgY / length
        val normalizedZ = avgZ / length

        // 转换回经纬度
        val lat = atan2(normalizedZ, sqrt(normalizedX.pow(2) + normalizedY.pow(2)))
        val lon = atan2(normalizedY, normalizedX)

        return LatLng(lat.toDegrees(), lon.toDegrees(), points.first().type)
    }


    override fun distanceToSegment(A: LatLng, B: LatLng, P: LatLng): Double {
        requireSameCoordinateType(A, B, P)
        val wgs1 = A.toWgs()
        val wgs2 = B.toWgs()
        val wgs3 = P.toWgs()
        // 转换为三维地心坐标系下的向量
        val a = latLngToVector(wgs1)
        val b = latLngToVector(wgs2)
        val p = latLngToVector(wgs3)

        // 向量 AB 方向
        val ab = vectorSubtract(b, a)
        // 向量 AP
        val ap = vectorSubtract(p, a)

        // 投影比例 t ∈ [0, 1] 表示在线段上的位置
        val dotAP_AB = vectorDot(ap, ab)
        val dotAB_AB = vectorDot(ab, ab)

        if (dotAP_AB <= 0) {
            // 最近点就是 A
            return distanceTo(wgs1, wgs3)
        }

        if (dotAP_AB >= dotAB_AB) {
            // 最近点就是 B
            return distanceTo(wgs2, wgs3)
        }

        // 最近点在线段 AB 上，求投影点
        val projection = vectorAdd(a, vectorScale(ab, dotAP_AB / dotAB_AB))
        val nearestPointOnSphere = normalizeVector(projection)
        val nearestLatLng = vectorToLatLng(nearestPointOnSphere)

        // 返回球面最近点到 P 的大地线距离
        return distanceTo(nearestLatLng, wgs3)
    }

}