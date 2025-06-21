package hos.utils

import hos.ext.isNull

/**
 * <p>Title: UrlUitls </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2024-07-11 19:16
 * @version : 1.0
 */
object UrlUtils {

    /**
     * 根据给定的目标字符串和值字符串返回一个处理后的URL
     * 如果目标字符串为空或空白，则返回值字符串
     * 如果目标字符串包含问号，则返回问号前的部分；否则返回目标字符串本身
     *
     * @param target 目标URL字符串，可能为空或空白
     * @param value 默认返回的值字符串
     * @return 处理后的URL
     */
    fun url(target: String?, value: String = ""): String {
        if (target.isNullOrBlank() || target.isNull()) {
            return value
        }
        return if (target.contains("?")) {
            target.split("?", limit = 2)[0]
        } else {
            target
        }
    }

    /**
     * 根据给定的目标字符串和协议标志返回一个带有http或https前缀的URL
     * 如果目标字符串为空或空白，则返回空字符串
     * 如果目标字符串已包含http或https前缀，则直接返回
     * 否则，根据isHttp参数决定添加http还是https前缀
     *
     * @param target 目标URL字符串，可能为空或空白
     * @param isHttp 是否使用http协议，true为http，false为https
     * @return 带有http或https前缀的URL
     */
    fun splitHttp(target: String?, isHttp: Boolean = true): String {
        if (target.isNullOrBlank() || target.isNull()) {
            return ""
        }
        return when {
            target.startsWith("http://") || target.startsWith("https://") -> target
            isHttp -> "http://$target"
            else -> "https://$target"
        }
    }

    /**
     * 将给定的参数字符串解析为键值对映射
     * 参数字符串应包含一个或多个键值对，键值对之间用"&"分隔
     * 每个键值对内部，键和值之间用等号分隔
     * 如果没有"&"字符，则将参数字符串视为单个键值对
     * 如果键没有对应的值，则为其分配空字符串作为值
     *
     * @param params 参数字符串
     * @return 解析后的键值对映射
     */
    private fun urlParams(params: String): Map<String, String> {
        val pairs = if (params.contains("&")) {
            params.split("&")
        } else {
            listOf(params)
        }

        val map = mutableMapOf<String, String>()
        for (pair in pairs) {
            val items = pair.split("=", limit = 2)
            when (items.size) {
                1 -> map[items[0]] = ""
                2 -> map[items[0]] = items[1]
            }
        }
        return map
    }

    /**
     * 根据给定的目标字符串和可变的键值对映射，返回一个更新后的键值对映射
     * 如果目标字符串为空或空白，则直接返回给定的映射
     * 如果目标字符串不包含问号，则将其视为参数字符串并解析
     * 如果目标字符串包含问号，则解析问号后的部分为参数字符串
     *
     * @param target 目标URL字符串，可能包含参数部分
     * @param params 初始键值对映射，将被更新
     * @return 更新后的键值对映射
     */
    fun params(
        target: String?,
        params: MutableMap<String, String> = mutableMapOf()
    ): Map<String, String> {
        if (target.isNullOrBlank() || target.isNull()) {
            return params
        }
        if (!target.contains("?")) {
            return urlParams(target)
        }
        return urlParams(target.split("?", limit = 2)[1])
    }

}