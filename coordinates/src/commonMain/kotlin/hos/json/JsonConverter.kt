package hos.json

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.float
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull
import kotlin.collections.iterator
import kotlin.text.iterator

/**
 * JsonConverter 对象提供了将数据结构转换为 JSON 字符串的功能
 */
object JsonConverter {

    /**
     * 将 Map 转换为 JSON 字符串
     */
    fun toJson(map: Map<*, *>): String {
        val sb = StringBuilder()
        appendMap(sb, map)
        return sb.toString()
    }

    /**
     * 将 List 转换为 JSON 字符串
     */
    fun toJson(list: List<*>): String {
        val sb = StringBuilder()
        appendList(sb, list)
        return sb.toString()
    }


    /**
     * 将 Map 类型的数据追加到 StringBuilder 中
     */
    private fun appendMap(sb: StringBuilder, map: Map<*, *>) {
        sb.append('{')
        var first = true
        for ((key, value) in map) {
            if (key == null) continue
            if (!first) sb.append(',')
            first = false
            sb.append('"').append(escape(key.toString())).append('"')
            sb.append(':')
            appendValue(sb, value)
        }
        sb.append('}')
    }

    /**
     * 将 List 类型的数据追加到 StringBuilder 中
     */
    private fun appendList(sb: StringBuilder, list: List<*>) {
        sb.append('[')
        var first = true
        for (item in list) {
            if (!first) sb.append(',')
            first = false
            appendValue(sb, item)
        }
        sb.append(']')
    }

    /**
     * 将值追加到 StringBuilder 中，根据值的类型选择不同的处理方式
     */
    private fun appendValue(sb: StringBuilder, value: Any?) {
        when (value) {
            null -> sb.append("null")
            is String -> sb.append('"').append(escape(value)).append('"')
            is Number -> sb.append(value)
            is Boolean -> sb.append(value)
            is Map<*, *> -> appendMap(sb, value)
            is List<*> -> appendList(sb, value)
            else -> throw IllegalArgumentException("Unsupported value type: ${value::class}")
        }
    }

    /**
     * 对字符串进行转义处理，以便在 JSON 中正确显示
     */
    private fun escape(str: String): String {
        val sb = StringBuilder()
        for (c in str) {
            when (c) {
                '\\' -> sb.append("\\\\")
                '"' -> sb.append("\\\"")
                '\b' -> sb.append("\\b")
                '\u000c' -> sb.append("\\f")
                '\n' -> sb.append("\\n")
                '\r' -> sb.append("\\r")
                '\t' -> sb.append("\\t")
                else -> if (c.isISOControl()) {
                    val hex = c.code.toString(16).uppercase()
                    sb.append("\\u").append("0".repeat(4 - hex.length)).append(hex)
                } else {
                    sb.append(c)
                }
            }
        }
        return sb.toString()
    }

    fun parseJsonToMap(jsonString: String): Map<String, Any?> {
        val jsonObject = Json.parseToJsonElement(jsonString).jsonObject
        return parseJsonToMap(jsonObject)
    }

    fun parseJsonToMap(jsonObject: JsonObject): Map<String, Any?> {
        return jsonObject.mapValues { (key, value) ->
            when (value) {
                is JsonPrimitive -> {
                    when {
                        value.isString -> value.content
                        value.content == "true" -> true
                        value.content == "false" -> false
                        value.intOrNull != null -> value.int
                        value.longOrNull != null -> value.long
                        value.booleanOrNull != null -> value.boolean
                        value.doubleOrNull != null -> value.double
                        value.floatOrNull != null -> value.float
                        else -> null
                    }
                }

                is JsonObject -> parseJsonToMap(value) // 已修正：传入 JsonObject
                is JsonArray -> parseJsonToList(value)
            }
        }
    }

    fun parseJsonToList(jsonString: String): List<Any?> {
        val jsonArray = Json.parseToJsonElement(jsonString).jsonArray
        return parseJsonToList(jsonArray)
    }

    fun parseJsonToList(array: JsonArray): List<Any?> {
        return array.map { element ->
            when (element) {
                is JsonPrimitive -> {
                    when {
                        element.isString -> element.content
                        element.content == "true" -> true
                        element.content == "false" -> false
                        element.intOrNull != null -> element.int
                        element.longOrNull != null -> element.long
                        element.booleanOrNull != null -> element.boolean
                        element.doubleOrNull != null -> element.double
                        element.floatOrNull != null -> element.float
                        else -> null
                    }
                }

                is JsonObject -> parseJsonToMap(element) // 已修正：传入 JsonObject
                is JsonArray -> parseJsonToList(element)
            }
        }
    }


}
