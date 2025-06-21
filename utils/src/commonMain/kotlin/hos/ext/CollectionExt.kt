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
 * 判断集合中指定索引的元素是否为null
 *
 * @param index 要检查的索引位置
 * @return 如果集合为null、为空、索引越界或指定索引处的元素为null，则返回true；否则返回false
 */
fun <T> Collection<T>?.isNull(index: Int): Boolean {
    // 检查集合是否为null或空，或索引是否越界
    if (this == null || isNull() || index < 0 || index >= size) return true
    // 检查指定索引处的元素是否为null
    return elementAt(index).isNull()
}

/**
 * 判断集合中指定索引的元素是否不为null
 *
 * @param index 要检查的索引位置
 * @return 如果集合为null、为空、索引越界或指定索引处的元素不为null，则返回true；否则返回false
 */
fun <T> Collection<T>?.isNotNull(index: Int): Boolean {
    // 利用isNull函数的结果进行取反，以判断元素是否不为null
    return !isNull(index)
}

/**
 * 判断集合是否包含指定的元素
 *
 * @param item 要检查的元素
 * @return 如果集合包含指定的元素，则返回true；否则返回false
 */
fun <T> Collection<T>.includes(item: T?): Boolean {
    // 使用contains方法检查集合是否包含指定元素
    return contains(item)
}

/**
 * 获取集合中指定索引的元素，如果索引处的元素为null或索引越界，则返回默认值
 *
 * @param index 要获取的元素的索引位置
 * @param defaultValue 如果索引处的元素为null或索引越界时返回的默认值
 * @return 索引处的元素或默认值
 */
fun <T> Collection<T>?.getOrDefault(
    index: Int,
    defaultValue: T? = null
): T? {
    // 检查索引处的元素是否为null或索引是否越界
    if (isNull(index)) {
        return defaultValue
    }
    // 返回索引处的元素，如果集合为null，则返回默认值
    return this?.elementAt(index) ?: defaultValue
}

/**
 * 获取集合中指定索引的元素或默认值
 *
 * 此函数旨在处理可能为空的集合，并从中获取指定索引的元素如果索引超出集合范围或集合为空，
 * 则返回默认值这种设计避免了直接访问集合元素时可能遇到的索引越界异常，提供了更安全的访问方式
 *
 * @param T 集合中元素的类型
 * @param index 要获取的元素的索引
 * @param defaultValue 如果索引越界或集合为空时返回的默认值
 * @return 集合中指定索引的元素或默认值
 */
fun <T> Collection<T>?.empty(
    index: Int,
    defaultValue: T,
): T {
    // 使用getOrDefault尝试获取集合中指定索引的元素，如果索引越界则返回默认值
    // 如果集合为空，那么getOrDefault将返回null，此时使用Elvis操作符返回默认值
    return getOrDefault(index, defaultValue) ?: defaultValue
}

/**
 * 将集合中的指定元素转换为另一种类型。
 *
 * 此函数提供了一种安全的方式来转换集合中的元素，它会检查集合是否为null以及指定索引是否越界，
 * 并在这些情况下提供适当的处理方式。
 *
 * @param T 集合中元素的类型。
 * @param R 转换后的元素类型。
 * @param index 要转换的元素在集合中的索引。
 * @param convert 转换逻辑，作为一个函数类型参数，接受可能为null的元素并返回转换后的结果。
 * @return 转换后的元素，如果集合为null或索引越界，则返回对null元素进行转换的结果。
 */
fun <T, R> Collection<T>?.convert(index: Int, convert: (T?.() -> R)): R {
    return convert(getOrDefault(index))
}
