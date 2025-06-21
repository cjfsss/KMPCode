package hos.utils


/**
 * <p>Title: AnyUtils</p>
 * <p>Description: 通用对象工具类，用于判断对象是否为空、空值或空白内容，并提供非空断言和默认值返回功能。</p>
 * <p>Company: www.hos.com</p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-10 17:13
 * @version : 1.0
 */
object AnyUtils {

    /**
     * 判断一个对象是否为 null 或者为空。
     *
     * 支持类型包括：
     * - CharSequence（如 String）：检查是否为空字符串
     * - Array：检查数组是否为空
     * - Collection：检查集合是否为空
     * - Map：检查映射是否为空
     *
     * @param any 待判断的对象
     * @return 如果对象为 null 或者为空，返回 true；否则返回 false
     */
    fun isNullOrEmpty(any: Any?): Boolean {
        if (any == null) {
            return true
        }
        if (any is CharSequence) {
            return any.isEmpty()
        }
        if (any is Array<*>) {
            return any.isEmpty()
        }
        if (any is Collection<*>) {
            return any.isEmpty()
        }
        if (any is Map<*, *>) {
            return any.isEmpty()
        }
        return false
    }

    /**
     * 判断一个对象是否不为 null 且不为空。
     *
     * @param any 待判断的对象
     * @return 如果对象不为 null 且不为空，返回 true；否则返回 false
     */
    fun isNotEmpty(any: Any?): Boolean {
        return !isNullOrEmpty(any)
    }

    /**
     * 判断一个对象是否为 null 或者是空白内容。
     *
     * 对于 CharSequence 类型（如 String），会调用 isBlank() 方法判断是否为空白字符串；
     * 其他类型则等价于 isNullOrEmpty。
     *
     * @param any 待判断的对象
     * @return 如果对象为 null 或者是空白内容，返回 true；否则返回 false
     */
    fun isNullOrBlank(any: Any?): Boolean {
        if (any == null) {
            return true
        }
        if (any is CharSequence) {
            return any.isBlank()
        }
        return isNullOrEmpty(any)
    }


}

