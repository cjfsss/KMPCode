package hos.coordinates.geometry

import hos.coordinates.CoordinateType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * <p>Title: LatLngCollection </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-13 9:19
 * @version : 1.0
 */
/**
 * 表示一个地理坐标点集合，支持对多个 LatLng 点的增删改查和常见几何操作。
 * 继承自 Geometry 类，表示该集合具有特定的坐标类型。
 *
 * @param type 坐标系统类型，用于标识当前集合所使用的坐标系（如 WGS84、GCJ02、BD09MC）
 */
@Serializable
class LatLngCollection(
    val points: MutableList<LatLng>,
    val type: CoordinateType
) {

    /**
     * 构造函数，通过已有的 LatLng 列表初始化集合
     *
     * @param collection 包含经纬度点的列表
     */
    constructor(collection: Collection<LatLng>) : this(
        collection.firstOrNull()?.type ?: CoordinateType.WGS84
    ) {
        addAll(collection)
    }

    /**
     * 构造函数，创建一个空的集合
     */
    constructor() : this(
        mutableListOf(),
        CoordinateType.WGS84
    )

    /**
     * 构造函数，创建一个空的集合
     */
    constructor(type: CoordinateType) : this(
        mutableListOf(),
        type
    )


    /**
     * 向集合中添加一个经纬度点
     *
     * @param latLng 要添加的经纬度点
     * @return 返回自身对象，支持链式调用
     */
    fun add(latLng: LatLng): LatLngCollection {
        points.add(latLng)
        return this
    }

    /**
     * 向集合中添加一个经纬度点
     *
     * @param latLng 要添加的经纬度点
     * @return 返回自身对象，支持链式调用
     */
    fun adds(vararg latLng: LatLng): LatLngCollection {
        points.addAll(latLng)
        return this
    }

    /**
     * 通过纬度和经度创建一个 LatLng 对象并添加到集合中
     *
     * @param y 纬度值
     * @param x 经度值
     * @return 返回自身对象，支持链式调用
     */
    fun add(y: Double, x: Double): LatLngCollection {
        return add(LatLng(y, x, type))
    }

    /**
     * 在指定索引位置插入一个经纬度点
     *
     * @param index 插入位置索引
     * @param y 纬度值
     * @param x 经度值
     * @return 返回自身对象，支持链式调用
     */
    fun add(index: Int, y: Double, x: Double): LatLngCollection {
        return add(index, LatLng(y, x, type))
    }

    /**
     * 添加一个经纬度点列表到集合中
     *
     * @param latLngList 待添加的经纬度点列表
     * @return 返回自身对象，支持链式调用
     */
    fun addAll(latLngList: Collection<LatLng>): LatLngCollection {
        this.points.addAll(latLngList)
        return this
    }

    /**
     * 获取指定索引位置的经纬度点
     *
     * @param index 索引位置
     * @return 返回对应的经纬度点
     */
    fun get(index: Int): LatLng {
        return points[index]
    }

    /**
     * 获取集合中的第一个经纬度点
     *
     * @return 第一个经纬度点
     */
    fun first(): LatLng {
        return points.first()
    }

    /**
     * 移除并返回集合中的第一个经纬度点
     *
     * @return 被移除的第一个经纬度点
     */
    fun removeFirst(): LatLng {
        return points.removeFirst()
    }

    /**
     * 获取集合中的最后一个经纬度点
     *
     * @return 最后一个经纬度点
     */
    fun last(): LatLng {
        return points.last()
    }

    /**
     * 移除并返回集合中的最后一个经纬度点
     *
     * @return 被移除的最后一个经纬度点
     */
    fun removeLast(): LatLng {
        return points.removeLast()
    }

    /**
     * 返回一个按逆序排列的新 LatLngCollection 集合
     *
     * @return 逆序排列的经纬度点集合
     */
    fun asReversed(): LatLngCollection {
        return LatLngCollection(points.asReversed())
    }

    /**
     * 获取集合中元素的数量
     *
     * @return 元素个数
     */
    fun size(): Int {
        return points.size
    }

    /**
     * 移除指定索引位置的经纬度点
     *
     * @param index 要移除的索引位置
     * @return 被移除的经纬度点
     */
    fun remove(index: Int): LatLng {
        return points.removeAt(index)
    }

    /**
     * 移除集合中指定的经纬度点
     *
     * @param latLng 要移除的经纬度点
     * @return 如果成功移除返回 true，否则返回 false
     */
    fun remove(latLng: LatLng): Boolean {
        return points.remove(latLng)
    }

    /**
     * 从集合中移除一组经纬度点
     *
     * @param latLngList 要移除的经纬度点列表
     * @return 如果集合被修改则返回 true，否则返回 false
     */
    fun removeAll(latLngList: List<LatLng>): Boolean {
        return this.points.removeAll(latLngList)
    }

    /**
     * 判断集合中是否包含指定的经纬度点
     *
     * @param latLng 要查找的经纬度点
     * @return 如果包含返回 true，否则返回 false
     */
    fun contains(latLng: LatLng): Boolean {
        return points.contains(latLng)
    }

    /**
     * 判断集合中是否包含指定列表中的所有经纬度点
     *
     * @param latLngList 要检查的经纬度点列表
     * @return 如果全部包含返回 true，否则返回 false
     */
    fun containsAll(latLngList: List<LatLng>): Boolean {
        return this.points.containsAll(latLngList)
    }

    /**
     * 获取指定经纬度点在集合中的索引位置（首次出现）
     *
     * @param latLng 要查找的经纬度点
     * @return 找到的位置索引，未找到返回 -1
     */
    fun indexOf(latLng: LatLng): Int {
        return points.indexOf(latLng)
    }

    /**
     * 获取指定经纬度点在集合中的最后出现位置索引
     *
     * @param latLng 要查找的经纬度点
     * @return 最后出现的位置索引，未找到返回 -1
     */
    fun lastIndexOf(latLng: LatLng): Int {
        return points.lastIndexOf(latLng)
    }

    /**
     * 判断集合是否为空
     *
     * @return 如果集合为空返回 true，否则返回 false
     */
    fun isEmpty(): Boolean {
        return points.isEmpty()
    }

    /**
     * 判断集合是否非空
     *
     * @return 如果集合不为空返回 true，否则返回 false
     */
    fun isNotEmpty(): Boolean {
        return points.isNotEmpty()
    }

    /**
     * 将集合转换为可变的 MutableList<LatLng>
     *
     * @return 可变的经纬度点列表
     */
    fun toMutableList(): MutableList<LatLng> {
        return points
    }

    /**
     * 将集合转换为数组形式
     *
     * @return 包含所有经纬度点的数组
     */
    fun toArray(): Array<LatLng> {
        return points.toTypedArray()
    }

    /**
     * 替换指定索引位置的经纬度点
     *
     * @param index 要替换的索引位置
     * @param element 新的经纬度点
     * @return 被替换掉的旧经纬度点
     */
    fun set(index: Int, element: LatLng): LatLng {
        return points.set(index, element)
    }

    /**
     * 在指定索引位置插入一个经纬度点
     *
     * @param index 插入位置索引
     * @param element 要插入的经纬度点
     * @return 返回自身对象，支持链式调用
     */
    fun add(index: Int, element: LatLng): LatLngCollection {
        points.add(index, element)
        return this
    }

    /**
     * 获取集合的列表迭代器，用于遍历或修改集合
     *
     * @return 列表迭代器
     */
    fun listIterator(): MutableListIterator<LatLng> {
        return points.listIterator()
    }

    /**
     * 获取集合的指定起始位置的列表迭代器
     *
     * @param index 迭代起始位置
     * @return 指定起始位置的列表迭代器
     */
    fun listIterator(index: Int): MutableListIterator<LatLng> {
        return points.listIterator(index)
    }

    /**
     * 清空集合中的所有经纬度点
     */
    fun clear() {
        points.clear()
    }

    /**
     * 构建一个边界矩形框，覆盖集合中所有点
     *
     * @return 返回构建完成的 LatLngBounds 对象
     */
    fun toLatLngBounds(): LatLngBounds {
        require(points.isNotEmpty()) { "至少需要一个点来构建边界框" }

        val type = points[0].type
        var minLat = points[0].latitude
        var maxLat = minLat
        var minLng = points[0].longitude
        var maxLng = minLng

        for (point in points) {
            if (point.type != type) continue
            if (point.latitude < minLat) minLat = point.latitude
            if (point.latitude > maxLat) maxLat = point.latitude
            if (point.longitude < minLng) minLng = point.longitude
            if (point.longitude > maxLng) maxLng = point.longitude
        }

        val southwest = LatLng(minLat, minLng, type)
        val northeast = LatLng(maxLat, maxLng, type)

        return LatLngBounds(southwest, northeast, type)
    }


    /**
     * 将集合中的所有点转换为百度坐标系（BD09MC）下的点集合
     *
     * @return 转换后的 LatLngCollection 实例
     */
    fun toBaiDu(): LatLngCollection {
        val latLngCollection = LatLngCollection(CoordinateType.BD09MC)
        points.forEach {
            latLngCollection.add(it.toBaiDu())
        }
        return latLngCollection
    }

    /**
     * 将集合中的所有点转换为高德坐标系（GCJ02）下的点集合
     *
     * @return 转换后的 LatLngCollection 实例
     */
    fun toAMap(): LatLngCollection {
        val latLngCollection = LatLngCollection(CoordinateType.GCJ02)
        points.forEach {
            latLngCollection.add(it.toAMap())
        }
        return latLngCollection
    }

    /**
     * 将集合中的所有点转换为地球坐标系（WGS84）下的点集合
     *
     * @return 转换后的 LatLngCollection 实例
     */
    fun toWgs(): LatLngCollection {
        val latLngCollection = LatLngCollection(CoordinateType.WGS84)
        points.forEach {
            latLngCollection.add(it.toWgs())
        }
        return latLngCollection
    }

    override fun toString(): String {
        return points.toString()
    }

    fun toJson(): String {
        return Json.encodeToString(this)
    }
}
