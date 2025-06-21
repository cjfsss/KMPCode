package hos.ext

/**
 * <p>Title: ifExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2024-07-26 14:54
 * @version : 1.0
 */
/**
 * 根据给定的条件执行特定的代码块
 *
 * @param condition 布尔条件，如果为真，则执行代码块
 * @param block 一个无返回值的代码块，当条件为真时执行
 */
inline fun doIf(condition: Boolean, block: () -> Unit) {
    if (condition) {
        block()
    }
}

/**
 * 根据给定的条件返回不同的执行结果
 *
 * @param condition 布尔条件，如果为真，则执行true代码块，否则执行false代码块
 * @param elseBlock 一个返回值的代码块，当条件为假时执行
 * @param block 一个返回值的代码块，当条件为真时执行
 * @return 根据条件执行的代码块的结果
 */
inline fun <T> doIfResult(condition: Boolean, elseBlock: () -> T, block: () -> T): T {
    if (condition) {
        return block()
    }
    return elseBlock()
}

/**
 * 根据布尔值选择不同的结果
 *
 * @param trueValue 如果布尔值为真，则返回此参数
 * @param falseValue 如果布尔值为假，则返回此参数
 * @return 根据布尔值选择的结果
 */
fun <T> Boolean.then(trueValue: T, falseValue: T) = if (this) trueValue else falseValue

/**
 * 根据布尔值选择不同的代码块执行
 *
 * @param trueBlock 如果布尔值为真，则执行此代码块
 * @param falseBlock 如果布尔值为假，则执行此代码块
 * @return 执行选定代码块的结果
 */
fun <T> Boolean.then(trueBlock: () -> T, falseBlock: () -> T) =
    if (this) trueBlock() else falseBlock()



