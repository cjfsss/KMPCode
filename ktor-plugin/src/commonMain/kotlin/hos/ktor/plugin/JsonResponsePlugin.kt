package hos.ktor.plugin

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.statement.HttpResponseContainer
import io.ktor.client.statement.HttpResponsePipeline
import io.ktor.util.AttributeKey
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import io.ktor.utils.io.readUTF8Line
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * <p>Title: BaseResponsePlugin </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-20 11:42
 * @version : 1.0
 */
class JsonResponsePlugin(private val config: Config) {

    class Config {
        var messageKey = listOf<String>("msg", "message")

        var codeKey = listOf<String>("code", "status", "success")

        var dataKey = listOf<String>("data", "rows", "result")

        var successCode = listOf<Any>(200, "200", true, "true")

        var parseResult: (JsonElement.(Config) -> String)? = null
    }

    companion object : HttpClientPlugin<Config, JsonResponsePlugin> {
        override val key: AttributeKey<JsonResponsePlugin>
            get() = AttributeKey<JsonResponsePlugin>("JsonResponsePlugin")

        override fun prepare(block: Config.() -> Unit): JsonResponsePlugin =
            JsonResponsePlugin(Config().apply { block() })

        /**
         * 安装一个响应插件到HttpClient中
         * 此函数主要用于拦截HTTP响应，根据需求解析响应体，并根据解析结果决定是否抛出异常
         *
         * @param plugin 要安装的响应插件，用于配置解析规则
         * @param scope HttpClient实例，用于应用插件
         */
        override fun install(
            plugin: JsonResponsePlugin,
            scope: HttpClient
        ) {
            // 拦截HTTP响应管道，以处理接收到的响应
            scope.responsePipeline.intercept(phase = HttpResponsePipeline.Receive) {
                val response = it.response
                // 确保响应是ByteReadChannel类型，否则直接返回
                if (response !is ByteReadChannel) return@intercept
                // 读取响应体文本
                val bodyText = response.readUTF8Line() ?: ""

                // 获取插件配置
                val config = plugin.config
                // 创建一个Json解析器，配置忽略未知键和使用替代名称
                val json = Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = true
                }
                // 尝试解析响应体文本为JsonElement
                val jsonElement = try {
                    json.parseToJsonElement(bodyText)
                } catch (e: Exception) {
                    // 如果解析失败，抛出自定义JSONException
                    throw JSONException(
                        ExceptionCode.JSON_PARSE_ERROR.code.toString(),
                        e.message ?: "Json 解析失败"
                    )
                }
                // 如果解析结果是JsonNull，抛出异常
                if (jsonElement is JsonNull) {
                    throw JSONException(ExceptionCode.JSON_NULL.code.toString(), "返回数据为空")
                }
                // 如果配置了parseResult，应用自定义解析逻辑
                if (config.parseResult != null) {
                    config.parseResult?.apply {
                        val jsonData = invoke(jsonElement, config)
                        val byteArray = jsonData.toByteArray(Charsets.UTF_8)
                        val httpResponseContainer =
                            HttpResponseContainer(it.expectedType, ByteReadChannel(byteArray))
                        proceedWith(httpResponseContainer)
                    }
                    return@intercept
                }
                // 确保解析结果是JsonObject类型
                if (jsonElement !is JsonObject) {
                    throw JSONException(ExceptionCode.JSON_NULL.code.toString(), "返回数据为空")
                }
                // 初始化状态码和消息
                var status = ""
                val jsonObject = jsonElement.jsonObject
                // 根据配置的codeKey，获取状态码
                for (key in config.codeKey) {
                    val temp = jsonObject[key]
                    if (temp != null && temp !is JsonNull && temp is JsonPrimitive) {
                        status = temp.jsonPrimitive.content.trim()
                        break
                    }
                }
                var message = ""
                // 根据配置的messageKey，获取消息
                for (key in config.messageKey) {
                    val temp = jsonObject[key]
                    if (temp != null && temp !is JsonNull && temp is JsonPrimitive) {
                        message = temp.jsonPrimitive.content.trim()
                        break
                    }
                }
                // 如果状态码不在成功码列表中，抛出异常
                if (!config.successCode.contains(status)) {
                    throw JSONException(status, message)
                }
                // 初始化数据Json字符串
                var dataJson = ""
                // 根据配置的dataKey，获取数据部分并重新编码为Json字符串
                for (key in config.dataKey) {
                    val temp = jsonObject[key]
                    if (temp != null && temp !is JsonNull) {
                        dataJson = json.encodeToString(temp)
                        break
                    }
                }
                val byteArray = dataJson.toByteArray(Charsets.UTF_8)

                // 创建新的HttpResponseContainer并继续处理管道
                val httpResponseContainer =
                    HttpResponseContainer(it.expectedType, ByteReadChannel(byteArray))
                proceedWith(httpResponseContainer)
            }
        }

    }

}