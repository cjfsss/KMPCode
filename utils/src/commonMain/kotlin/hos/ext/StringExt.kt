package hos.ext

import kotlin.math.floor


/**
 * <p>Title: StringExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2024-07-11 13:39
 * @version : 1.0
 */


//fun CharSequence?.isNull(): Boolean {
//    return this == null || (isNullOrEmpty() || "null" === this
//            || "NULL" === this || "Null" === this) || isNullOrBlank()
//}

/**
 * 如果字符串为空，则返回给定的默认值
 *
 * @param value 默认值，默认为空字符串
 * @return 如果字符串为空，则返回默认值，否则返回字符串本身
 */
fun String?.empty(value: String = ""): String {
    return getOrDefault(value)
}

/**
 * 将字符串转换为模糊匹配的格式
 *
 * @param value 默认值，默认为空字符串
 * @return 如果字符串为空，则返回默认值，否则返回字符串本身，并在前后添加百分号
 */
fun String?.like(value: String = ""): String {
    return empty(value).convert {
        "%$this%"
    }
}

/**
 * 检查字符串是否可以转换为整数
 *
 * @return 如果字符串可以转换为整数，则返回true，否则返回false
 */
fun String?.isInt(): Boolean {
    return this != null && this.toIntOrNull() != null
}

/**
 * 检查字符串是否可以转换为双精度浮点数
 *
 * @return 如果字符串可以转换为双精度浮点数，则返回true，否则返回false
 */
fun String?.isDouble(): Boolean {
    return this != null && this.toDoubleOrNull() != null
}

/**
 * 检查字符串是否可以转换为布尔值
 *
 * @return 如果字符串可以转换为布尔值，则返回true，否则返回false
 */
fun String?.isBool(): Boolean {
    return this != null && this.toBooleanStrictOrNull() != null
}

// 定义一组被认为是真值的字符串
private val TRUE_VALUES = setOf("true", "是", "有", "正确", "1", "1.0")

// 定义一组被认为是假值的字符串
private val FALSE_VALUES = setOf("false", "否", "无", "错误", "0", "0.0")

/**
 * 将字符串转换为布尔值
 *
 * @param value 默认值，默认为false
 * @return 如果字符串为真值，则返回true，如果字符串为假值，则返回false，否则返回默认值
 */
fun String?.asBool(value: Boolean = false): Boolean {
    if (this == null || this.isEmpty()) {
        return value
    }
    return when (this) {
        in TRUE_VALUES -> true
        in FALSE_VALUES -> false
        else -> try {
            toBooleanStrict()
        } catch (e: IllegalArgumentException) {
            value
        }
    }
}

/**
 * 将字符串转换为整数
 *
 * @param value 默认值，默认为0
 * @return 如果字符串为空或无法转换为整数，则返回默认值，否则返回转换后的整数
 */
fun String?.asInt(value: Int): Int {
    if (this == null || this.isEmpty()) {
        return value
    }
    return parseCustomNumeric().toInt()
}

/**
 * 将字符串转换为双精度浮点数
 *
 * @param value 默认值，默认为0.0
 * @return 如果字符串为空或无法转换为双精度浮点数，则返回默认值，否则返回转换后的双精度浮点数
 */
fun String?.asDouble(value: Double): Double {
    if (this == null || this.isEmpty()) {
        return value
    }
    return parseCustomNumeric()
}

/**
 * 将字符串转换为适当的数值类型
 *
 * @param value 默认值，默认为0
 * @return 如果字符串为空或无法转换为数值类型，则返回默认值，否则返回转换后的数值类型
 */
fun String?.asNum(value: Number): Number {
    if (this == null || this.isEmpty()) {
        return value
    }
    return try {
        toDouble().let { d ->
            if (d == floor(d)) d.toInt() as Number else d as Number
        }
    } catch (e: NumberFormatException) {
        when (this) {
            in TRUE_VALUES -> 1
            in FALSE_VALUES -> 0
            else -> value
        }
    }
}

/**
 * 解析字符串为自定义的数值类型
 *
 * @return 如果字符串为真值，则返回1.0，如果字符串为假值，则返回0.0，否则尝试将字符串转换为双精度浮点数
 */
private fun String.parseCustomNumeric(): Double {
    return when (this) {
        in TRUE_VALUES -> 1.0
        in FALSE_VALUES -> 0.0
        else -> try {
            toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }
}


/**
 * 判断一个字符序列是否包含另一个字符序列。
 *
 * @param other 要查找的字符序列。
 * @param ignoreCase 是否忽略大小写，默认为false。
 * @return 如果包含则返回true，否则返回false。
 */
fun CharSequence?.includes(other: CharSequence, ignoreCase: Boolean = false): Boolean {
    // 确保当前字符序列不为空，并且包含另一个字符序列
    return this != null && contains(other, ignoreCase)
}

/**
 * 从当前字符序列中提取子字符串。
 *
 * @param start 子字符串的起始位置。
 * @param count 子字符串的长度。
 * @return 提取的子字符串，如果参数无效则返回空字符串。
 */
fun CharSequence?.subStr(start: Int, count: Int): String {
    // 参数有效性检查
    if (this == null || start < 0 || count < 0 || start >= length) {
        return ""
    }
    val end = start + count
    // 检查结束位置是否超出字符序列长度
    if (end > length) {
        return subSequence(start, length).toString()
    }
    // 正常情况，直接提取子字符串
    return substring(start, end)
}



/**
 * 计算字符串的长度，其中表意文字（如中文）的长度计为2，其他字符长度计为1。
 * 此方法主要用于处理可能包含null值的字符串，通过安全地处理null值，避免了NullPointerException。
 *
 * @return 字符串的调整后长度，null值返回0。
 */
fun String?.fontLength(): Int {
    val target = this ?: return 0 // 如果为 null，返回 0
    var strLength = 0
    for (c in target) {
        strLength += if (c.isIdeographic()) {
            2
        } else {
            1
        }
    }
    return strLength
}

/**
 * 判断字符是否为表意文字。
 * 主要用于判断字符是否属于CJK统一表意文字范围，包括但不限于中文字符。
 *
 * @return 如果字符是表意文字，则返回true；否则返回false。
 */
private fun Char.isIdeographic(): Boolean {
    val code = this.code
    return when (code) {
        in 0x4E00..0x9FFF -> true // CJK Unified Ideographs
        in 0x3400..0x4DBF -> true // CJK Unified Ideographs Extension A
        in 0xF900..0xFAFF -> true // CJK Compatibility Ideographs
        in 0x13000..0x18AFF -> true // 多种象形文字扩展块
        else -> false
    }
}
/**
 * 获取字符串的左侧部分
 *
 * @param len 指定获取的字符串长度，必须为非负数
 * @return 返回字符串的左侧部分如果输入为null、空字符串或len小于0，则返回空字符串如果字符串长度小于等于len，则返回整个字符串
 */
fun CharSequence?.left(len: Int): String {
    if (this == null || isNull() || len < 0) {
        return ""
    }
    if (length <= len) {
        return this.toString()
    }
    return substring(0, len)
}

/**
 * 获取字符串的右侧部分
 *
 * @param len 指定获取的字符串长度，必须为非负数
 * @return 返回字符串的右侧部分如果输入为null、空字符串或len小于0，则返回空字符串如果字符串长度小于等于len，则返回整个字符串
 */
fun CharSequence?.right(len: Int): String {
    if (this == null || isNull() || len < 0) {
        return ""
    }
    if (length <= len) {
        return this.toString()
    }
    return substring(length - len)
}

/**
 * 格式化时间字符串
 *
 * @param value 默认为空字符串，用作空值替代使用
 * @return 返回格式化后的时间字符串如果输入为null，则返回value的值
 */
fun String?.time(value: String = ""): String {
    return empty(value).convert {
        var time = this!!
        // 检查并替换时间字符串中的特定字符或格式
        if (contains("T") ||
            contains("+") ||
            contains(".")
        ) {
            if (contains("T")) {
                time = (time).replace("T", " ");
            }
            if (contains(".")) {
                time = time.substring(0, time.lastIndexOf("."));
            }
            if (contains("+")) {
                time = time.substring(0, time.lastIndexOf("+"));
            }
            return@convert time
        }
        return@convert time
    }
}



