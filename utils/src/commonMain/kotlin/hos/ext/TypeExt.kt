package hos.ext

/**
 * <p>Title: TypeExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-11 11:53
 * @version : 1.0
 */
inline fun <T, R> T?.convert(convert: T?.() -> R): R {
    if (this == null || this.isNull()) {
        return convert(null)
    }
    return convert(this)
}

/**
 * 获取对象本身，如果为 null 则返回默认值。
 *
 * @param defaultAny 当 any 为 null 时的默认值
 * @param <T> 对象的泛型类型
 * @return 如果 any 不为 null，则返回 any；否则返回 defaultAny
 */
fun <T> T?.getOrDefault(defaultAny: T): T {
    if (this == null || (this is String && this.isNull())) {
        return defaultAny
    }
    return this
}


/**
 * 检查给定对象是否非 null，若为 null 则抛出 NullPointerException。
 *
 * @param <T> 对象的泛型类型
 * @return 返回非 null 的对象
 * @throws NullPointerException 如果对象为 null
 */
fun <T> T?.requireNonNull(): T {
    if (this == null) throw NullPointerException("$this is null")
    return this
}

/**
 * 检查给定对象是否非 null，若为 null 则抛出自定义消息的 NullPointerException。
 *
 * @param nullMessage 自定义的异常提示信息
 * @param <T> 对象的泛型类型
 * @return 返回非 null 的对象
 * @throws NullPointerException 如果对象为 null，并携带指定的错误信息
 */
fun <T> T?.requireNonNull(nullMessage: String): T {
    if (this == null) throw NullPointerException(nullMessage)
    return this
}