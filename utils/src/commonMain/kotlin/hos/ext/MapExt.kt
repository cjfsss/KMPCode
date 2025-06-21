package hos.ext

/**
 * <p>Title: IterableExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2024-07-12 11:42
 * @version : 1.0
 */

/**
 * 检查给定键是否映射到一个非空值
 *
 * @param key 要检查的键
 * @return 如果映射为空或不包含键，或者键映射到null，则返回true；否则返回false
 */
fun <K, T> Map<K, T>?.isEmpty(key: K): Boolean {
    if (this == null || this.isEmpty()) return true
    if (!containsKey(key)) return true
    return this[key] == null
}

/**
 * 检查给定键是否映射到一个非空且非"null"字符串的值
 *
 * @param key 要检查的键
 * @return 如果映射为空、不包含键、键映射到null，或者键映射到特定的"null"字符串变体，则返回true；否则返回false
 */
fun <K, T> Map<K, T>?.isNull(key: K): Boolean {
    if (this == null || this.isNull()) return true
    if (!containsKey(key)) return true
    return get(key).isNull()
}

/**
 * 检查给定键是否映射到一个非空且非"null"字符串的值
 *
 * @param key 要检查的键
 * @return 如果映射不为空、包含键且键不映射到null或特定的"null"字符串变体，则返回true；否则返回false
 */
fun <K, T> Map<K, T>?.isNotNull(key: K): Boolean {
    return !isNull(key)
}


/**
 * 检查映射是否包含给定的值
 *
 * @param item 要检查的值
 * @return 如果映射为空，则返回false；如果映射包含该值，则返回true
 */
fun <K, T> Map<K, T>?.includes(item: T?): Boolean {
    if (this == null || isNull()) return false
    return this.containsValue(item)
}

/**
 * 获取给定键的值，如果键不存在或映射为空，则返回默认值
 *
 * @param key 要获取值的键
 * @param defaultValue 默认值
 * @return 键的值或默认值
 */
fun <K, T> Map<K, T>?.getOrDefaultValue(
    key: K,
    defaultValue: T? = null,
): T? {
    if (this == null || isNull<K, T>(key)) {
        return defaultValue
    }
    return get(key) ?: defaultValue
}

/**
 * 获取映射中与指定键关联的值，如果映射为空或指定键对应的值为null，则返回默认值。
 *
 * @param key 映射中的键，用于获取关联的值。
 * @param defaultValue 如果映射为空或指定键对应的值为null时，返回的默认值。
 * @return 映射中与指定键关联的值，或者如果映射为空或指定键对应的值为null，则返回默认值。
 */
fun <K, T> Map<K, T>?.empty(
    key: K,
    defaultValue: T,
): T {
    // 使用getOrDefault方法尝试获取与key关联的值，如果key对应的值为null，则返回defaultValue。
    // 如果映射本身为null，getOrDefault方法的结果也将为null，此时同样返回defaultValue。
    return getOrDefaultValue<K, T>(key, defaultValue) ?: defaultValue
}


/**
 * 遍历映射中的每个值并执行给定的操作
 *
 * @param block 每个值执行的操作
 */
fun <K, T> Map<K, T>?.forEachValue(block: T?.() -> Unit) {
    if (this == null) {
        return
    }
    keys.forEach {
        block.invoke(this[it])
    }
}

/**
 * 过滤映射中满足给定条件的值并返回它们的列表
 *
 * @param action 过滤条件，接受键和值并返回一个布尔值
 * @return 满足条件的值的列表
 */
inline fun <K, T> Map<K, T>?.where(crossinline action: K.(value: T?) -> Boolean): MutableList<T> {
    if (this == null) {
        return mutableListOf()
    }
    val list = mutableListOf<T>()
    this.forEach { (k, t) ->
        if (action(k, t)) {
            list.add(t)
        }
    }
    return list
}

/**
 * 将给定的键值对从Map中转换为新类型
 *
 * 此函数提供了一种安全的方式来转换Map中指定键对应的值它接受一个Map，一个键和一个转换函数作为参数
 * 如果Map为空或者指定的键为null，则会将null作为参数传递给转换函数执行转换操作
 * 否则，会使用转换函数将查找到的值转换为新类型
 *
 * @param K 键的类型
 * @param T 值的原始类型
 * @param R 转换后的值的类型
 * @param map 可能为空的Map对象
 * @param key 要转换值的键
 * @param convert 转换函数，接受一个可能为null的原始类型值，并返回转换后的值
 * @return 转换后的值
 */
fun <K, T, R> Map<K, T>?.convert(key: K, convert: (T?.() -> R)): R {
    return convert(this.getOrDefaultValue<K,T>(key))
}
