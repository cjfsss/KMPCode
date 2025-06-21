package hos.utils

import hos.ext.isNull


/**
 * <p>Title: RegexUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-11 9:12
 * @version : 1.0
 */
object RegexUtils {
    /**
     * 验证纬度是否有效
     *
     * @param lat 纬度字符串
     * @param zh 是否限制为中国范围
     * @return 纬度是否有效
     */
    fun isValidLatitude(lat: String?, zh: Boolean = true): Boolean {
        // 检查纬度字符串是否为空或特殊值
        if (lat == null || lat.isNull()) {
            return false
        }
        if (lat.startsWith("0") || lat.startsWith("O", ignoreCase = true) ||
            lat == "0.0" || lat == "0.00"
        ) {
            return false
        }

        // 使用正则表达式匹配纬度格式
        val regex = """^(-?(?:[0-8]?[0-9]|90)(?:\.[0-9]+)?)$""".toRegex()
        val matches = lat.matches(regex)
        // 根据匹配结果和是否限制为中国范围来决定返回值
        if (!matches || !zh) {
            return matches
        }

        // 将纬度字符串转换为双精度浮点数并检查是否在中国范围内
        val value = lat.toDoubleOrNull() ?: return false
        return value in 3.86..53.56
    }

    /**
     * 验证经度是否有效
     *
     * @param lng 经度字符串
     * @param zh 是否限制为中国范围
     * @return 经度是否有效
     */
    fun isValidLongitude(lng: String?, zh: Boolean = true): Boolean {
        // 检查经度字符串是否为空或特殊值
        if (lng == null || lng.isNull()) {
            return false
        }
        if (lng.startsWith("0") || lng.startsWith("O", ignoreCase = true) ||
            lng == "0.0" || lng == "0.00"
        ) {
            return false
        }

        // 使用正则表达式匹配经度格式
        val regex = """^(-?(?:1[0-7][0-9]|[0-9]{1,2})(?:\.[0-9]+)?|180(?:\.0+)?)$""".toRegex()
        val matches = lng.matches(regex)
        // 根据匹配结果和是否限制为中国范围来决定返回值
        if (!matches || !zh) {
            return matches
        }

        // 将经度字符串转换为双精度浮点数并检查是否在中国范围内
        val value = lng.toDoubleOrNull() ?: return false
        return value in 73.66..135.05
    }

    /**
     * 检查密码是否符合指定条件
     *
     * @param target 密码字符串
     * @param upperCase 是否包含大写字母
     * @param lowerCase 是否包含小写字母
     * @param letter 是否包含字母
     * @param digit 是否包含数字
     * @param special 是否包含特殊字符
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @param specialCharSet 特殊字符集合
     * @return 密码是否有效
     */
    fun checkPassword(
        target: String?,
        upperCase: Boolean = false,
        lowerCase: Boolean = false,
        letter: Boolean = false,
        digit: Boolean = false,
        special: Boolean = false,
        minLength: Int = 8,
        maxLength: Int = 30,
        specialCharSet: MutableSet<Char> = mutableSetOf<Char>(
            '~', '`', '!', '@', '#', '$', '%', '^',
            '&', '*', '(', ')', '-', '_', '+', '=',
            '{', '[', '}', ']', '|', '\\', ':', ';',
            '"', '\'', '<', ',', '>', '.', '?', '/',
        ),
    ): Boolean {
        // 检查密码字符串是否为空
        if (target == null || target.isNull()) {
            return false
        }
        val password = target
        // 检查密码长度是否符合要求
        if ((password.length < minLength) || password.length > maxLength) {
            return false
        }
        // 初始化标志变量以检查密码是否包含指定类型的字符
        var containUpperCase = false
        var containLowerCase = false
        var containLetter = false
        var containDigit = false
        var containSpecial = false
        // 遍历密码中的每个字符
        for (ch in password.toCharArray()) {
            // 更新标志变量
            if (ch.isUpperCase()) {
                containUpperCase = true
                containLetter = true
            } else if (ch.isLowerCase()) {
                containLowerCase = true
                containLetter = true
            } else if (ch.isDigit()) {
                containDigit = true
            } else if (specialCharSet.contains(ch)) {
                containSpecial = true
            } else {
                // 如果遇到非法字符则返回false
                return false
            }
        }
        // 根据密码是否包含指定类型的字符来决定返回值
        if (upperCase && !containUpperCase) {
            return false
        }
        if (lowerCase && !containLowerCase) {
            return false
        }
        if (letter && !containLetter) {
            return false
        }
        if (digit && !containDigit) {
            return false
        }
        if (special && !containSpecial) {
            return false
        }
        return true
    }

    // 定义合法字符集合
    private val VALID_CHARS = setOf(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
        'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
        'W', 'X', 'Y', 'Z'
    )

    // 定义权重数组
    private val WEIGHTS = intArrayOf(1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28)

    /**
     * 判断是否是合法的社会信用代码（18 位，符合 GB/T 34590-2017 标准）
     */
    fun isSocialNumber(value: String?): Boolean {
        // 将输入转换为大写并检查是否为空
        val code = value?.uppercase() ?: return false
        // 检查代码长度和字符合法性
        return when {
            value.isNull() -> false
            value.length != 18 -> false
            !code.all { it in VALID_CHARS } -> false
            else -> validateCheckDigit(code)
        }
    }

    /**
     * 验证社会信用代码的校验位
     */
    private fun validateCheckDigit(code: String): Boolean {
        // 计算校验和
        var sum = 0
        for (i in 0..16) {
            val value = charToValue(code[i]) ?: return false
            sum += value * WEIGHTS[i]
        }

        // 根据校验和计算校验位并比较
        val checkDigitIndex = 31 - (sum % 31)
        val expectedCheckChar = when (checkDigitIndex) {
            31 -> '0'
            32 -> 'Z'
            33 -> '1'
            34 -> '2'
            else -> (checkDigitIndex - 1 + '0'.code).toChar()
        }

        return code[17] == expectedCheckChar
    }

    /**
     * 将字符转换为对应的数值
     */
    private fun charToValue(c: Char): Int? = when (c) {
        in '0'..'9' -> c - '0'
        in 'A'..'Z' -> c - 'A' + 10
        else -> null
    }

    /**
     * 检查输入是否匹配给定的正则表达式
     */
    fun isMatch(regex: String, input: CharSequence?): Boolean {
        // 检查正则表达式和输入是否为空
        if (regex.isBlank()) return false
        if (input == null || input.isEmpty()) return false
        // 使用给定的正则表达式匹配输入
        return regex.toRegex().matches(input.toString())
    }

    // 定义IPv4地址的正则表达式
    private val IP_REGEX by lazy {
        Regex("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)")
    }

    // 定义日期格式的正则表达式（支持闰年）
    private val DATE_REGEX by lazy {
        Regex("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$")
    }

    // 定义用户名格式的正则表达式
    private val USERNAME_REGEX by lazy {
        Regex("^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$")
    }

    // 定义中文字符的正则表达式
    private val ZH_REGEX by lazy {
        Regex("^[\\u4e00-\\u9fa5]+$")
    }

    // 定义URL格式的正则表达式
    private val URL_REGEX by lazy {
        Regex("^[a-zA-z]+://[^\\s]*$")
    }

    // 定义邮箱格式的正则表达式
    private val EMAIL_REGEX by lazy {
        Regex("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$")
    }

    // 定义18位身份证号码的正则表达式
    private val ID_CARD_18_REGEX by lazy {
        Regex("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])\$")
    }

    /**
     * 15位身份证号码校验
     */
    private val ID_CARD_15_REGEX by lazy {
        Regex("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}\$")
    }

    /**
     * 固定电话号码校验（区号+号码，允许 - 或空格分隔）
     */
    private val TEL_REGEX by lazy {
        Regex("^0\\d{2,3}[- ]?\\d{7,8}\$")
    }

    /**
     * 检验输入是否为有效的IP地址
     * @param input 待检验的字符串
     * @return 如果是有效的IP地址返回true，否则返回false
     */
    fun isIP(input: CharSequence?): Boolean {
        return input != null && IP_REGEX.matches(input)
    }

    /**
     * 检验输入是否为有效的日期
     * @param input 待检验的字符串
     * @return 如果是有效的日期返回true，否则返回false
     */
    fun isDate(input: CharSequence?): Boolean {
        return input != null && DATE_REGEX.matches(input)
    }

    /**
     * 检验输入是否为有效的用户名
     * @param input 待检验的字符串
     * @return 如果是有效的用户名返回true，否则返回false
     */
    fun isUsername(input: CharSequence?): Boolean {
        return input != null && USERNAME_REGEX.matches(input)
    }

    /**
     * 检验输入是否为有效的汉字字符串
     * @param input 待检验的字符串
     * @return 如果是有效的汉字字符串返回true，否则返回false
     */
    fun isZh(input: CharSequence?): Boolean {
        return input != null && ZH_REGEX.matches(input)
    }

    /**
     * 检验输入是否为有效的URL
     * @param input 待检验的字符串
     * @return 如果是有效的URL返回true，否则返回false
     */
    fun isURL(input: CharSequence?): Boolean {
        return input != null && URL_REGEX.matches(input)
    }

    /**
     * 检验输入是否为有效的电子邮件地址
     * @param input 待检验的字符串
     * @return 如果是有效的电子邮件地址返回true，否则返回false
     */
    fun isEmail(input: CharSequence?): Boolean {
        return input != null && EMAIL_REGEX.matches(input)
    }

    /**
     * 检验输入是否为有效的18位身份证号码
     * @param input 待检验的字符串
     * @return 如果是有效的18位身份证号码返回true，否则返回false
     */
    fun isIDCard18(input: CharSequence?): Boolean {
        return input != null && ID_CARD_18_REGEX.matches(input)
    }

    /**
     * 检验输入是否为有效的15位身份证号码
     * @param input 待检验的字符串
     * @return 如果是有效的15位身份证号码返回true，否则返回false
     */
    fun isIDCard15(input: CharSequence?): Boolean {
        return input != null && ID_CARD_15_REGEX.matches(input)
    }

    /**
     * 检验输入是否为有效的固定电话号码
     * @param input 待检验的字符串
     * @return 如果是有效的固定电话号码返回true，否则返回false
     */
    fun isTel(input: CharSequence?): Boolean {
        return input != null && TEL_REGEX.matches(input)
    }


}