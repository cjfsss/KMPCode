import hos.coordinates.ext.buildLatLngCollection
import hos.coordinates.geometry.LatLng
import hos.coordinates.geometry.LatLngCollection
import kotlinx.serialization.json.Json
import kotlin.test.Test

/**
 * <p>Title: LatLngCollectionTest </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-17 16:15
 * @version : 1.0
 */
class LatLngCollectionTest {
    @Test
    fun test() {
        val buildLatLngCollection = buildLatLngCollection {
            add(LatLng(39.909, 116.397))
            add(LatLng(39.109, 116.197))
            add(LatLng(39.209, 116.297))
            add(LatLng(39.309, 116.297))
            add(LatLng(39.409, 116.297))
        }
        val toJson = buildLatLngCollection.toJson()
        println("json $toJson")
        val decodeFromString = Json.decodeFromString<LatLngCollection>(toJson)
        println("decodeFromString $decodeFromString")

    }
}