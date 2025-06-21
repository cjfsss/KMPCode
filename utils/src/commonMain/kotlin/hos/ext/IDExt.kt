package hos.ext

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * <p>Title: IDExt </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2024-07-20 16:38
 * @version : 1.0
 */
/**
 * 生成随机UUID字符串
 *
 * 此函数使用实验性的UUID API来生成一个随机的UUID，并将其转换为字符串格式
 * 由于使用了实验性API，因此需要注意未来版本的兼容性问题
 *
 * @return 随机生成的UUID字符串
 */
@ExperimentalUuidApi
fun genUuid() = Uuid.random().toString()

/**
 * 生成大写的随机UUID字符串
 *
 * 在[genUuid]函数的基础上，将生成的UUID字符串转换为大写格式
 * 同样使用了实验性API，需要注意兼容性问题
 *
 * @return 大写格式的随机UUID字符串
 */
@ExperimentalUuidApi
fun genUUID() = genUuid().uppercase()

/**
 * 生成大写且无连字符的随机UUID字符串
 *
 * 在[genUUID]函数的基础上，移除了UUID字符串中的连字符，常用于需要紧凑型ID的场景
 * 使用了实验性API，需要注意兼容性问题
 *
 * @return 大写且无连字符的随机UUID字符串
 */
@ExperimentalUuidApi
fun genID() = genUuid().uppercase().replace("-".toRegex(), "")

