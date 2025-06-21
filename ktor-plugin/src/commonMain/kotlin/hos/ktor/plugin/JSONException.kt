package hos.ktor.plugin

/**
 * <p>Title: JSONException </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-20 13:23
 * @version : 1.0
 */
enum class ExceptionCode(val code: Int){
    SUCCESS(200),
    ERROR(500),
    NOT_FOUND(404),
    NOT_ALLOWED(405),
    NOT_AUTHORIZED(401),
    NOT_SUPPORTED(415),
    NOT_ACCEPTABLE(406),
    NOT_ACCEPTABLE_CONTENT_TYPE(415),
    NOT_ACCEPTABLE_ACCEPT_TYPE(406),
    NOT_ACCEPTABLE_ACCEPT_TYPE_CONTENT_TYPE(415),
    JSON_PARSE_ERROR(1001),
    JSON_NULL(1000), ;
}


class JSONException(private val code: String, private val desc: String) : Exception() {
    override val message: String?
        get() = "code:$code desc:$desc"

}