package hos.ext

/**
 * <p>Title: AnyExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-11 13:01
 * @version : 1.0
 */
/**
 * 判断一个对象是否为 null 或者为空。
 *
 * 支持类型包括：
 * - CharSequence（如 String）：检查是否为空字符串
 * - Array：检查数组是否为空
 * - Collection：检查集合是否为空
 * - Map：检查映射是否为空
 *
 * @return 如果对象为 null 或者为空，返回 true；否则返回 false
 */
fun Any?.isNull(): Boolean {
    if (this == null) {
        return true
    }
    if (this is CharSequence) {
        return (isNullOrEmpty() || "null" === this
                || "NULL" === this || "Null" === this) || isNullOrBlank()
    }
    if (this is Array<*>) {
        return this.isEmpty()
    }
    if (this is Collection<*>) {
        return this.isEmpty()
    }
    if (this is Map<*, *>) {
        return this.isEmpty()
    }
    return false
}

/**
 * 判断一个对象是否不为 null。
 *
 * @return 如果对象不为 null，返回 true；否则返回 false
 */
fun Any?.isNotNull(): Boolean {
    return !isNull()
}