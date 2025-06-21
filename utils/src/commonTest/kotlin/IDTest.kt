import hos.ext.genID
import hos.ext.genUUID
import hos.ext.genUuid
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi

/**
 * <p>Title: IDTest </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-11 17:52
 * @version : 1.0
 */
class IDTest {
    // JUnit的测试注解，表明这是一个JUnit测试方法
    @Test
    // OptIn注解用于表明该测试方法有意使用实验性的UUID API
    @OptIn(ExperimentalUuidApi::class)
    fun main() {
        println("=== 测试 genUuid ===")
        repeat(3) {
            val uuid = genUuid()
            println("genUuid(): $uuid (期望: 标准 UUID v4 格式)")
        }

        println("\n=== 测试 genUUID ===")
        repeat(3) {
            val upperUuid = genUUID()
            println("genUUID(): $upperUuid (期望: 大写 UUID)")
        }

        println("\n=== 测试 genID ===")
        repeat(3) {
            val id = genID()
            println("genID(): $id (期望: 无连字符的大写 UUID)")
        }
    }

}