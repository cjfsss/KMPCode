package hos.ext

/**
 * <p>Title: TryCatchExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 20:43
 * @version : 1.0
 */
/**
 * 安全执行一段代码，并提供自定义异常处理逻辑。
 * 仅捕获 Exception 层级以下的异常，避免处理致命错误。
 */
inline fun <R> doTryCatch(
    work: () -> R,
    errorHandler: (Throwable) -> R,
): R {
    return try {
        work()
    } catch (e: Throwable) {
        errorHandler(e)
    }
}

/**
 * 安全执行一个接收者扩展函数 block，并将其结果封装进 Result 返回。
 * 若发生异常，则返回 Result.failure(exception)。
 */
inline fun <T, V> T.runCatchingToResult(block: T.() -> V?): Result<V?> {
    return doTryCatch(
        work = { Result.success(block()) },
        errorHandler = { e -> Result.failure(e) }
    )
}