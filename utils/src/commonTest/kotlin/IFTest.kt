import hos.ext.*
import kotlin.test.Test

/**
 * <p>Title: IfExtTest </p>
 * <p>Description: 用于测试 if 相关扩展函数逻辑 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-14 20:00
 * @version : 1.0
 */
class IFTest {

    @Test
    fun main() {
        println("=== 测试 doIf ===")
        testDoIf(true)
        testDoIf(false)

        println("\n=== 测试 doIfResult ===")
        testDoIfResult(true)
        testDoIfResult(false)

        println("\n=== 测试 Boolean.then(值) ===")
        testThenValue(true)
        testThenValue(false)

        println("\n=== 测试 Boolean.then(代码块) ===")
        testThenBlock(true)
        testThenBlock(false)
    }

    private fun testDoIf(condition: Boolean) {
        doIf(condition) {
            println("doIf($condition): 条件为真时执行了代码块 (期望: $condition)")
        }
        println("doIf($condition): 结束 (期望: 不执行代码块当 condition 为 false)")
    }

    private fun testDoIfResult(condition: Boolean) {
        val result = doIfResult(
            condition = condition,
            elseBlock = { "else分支" },
            block = { "then分支" }
        )
        println("doIfResult($condition): $result (期望: ${if (condition) "then分支" else "else分支"})")
    }

    private fun testThenValue(condition: Boolean) {
        val value = condition.then("条件为真", "条件为假")
        println("Boolean.then(值)($condition): $value (期望: ${if (condition) "条件为真" else "条件为假"})")
    }

    private fun testThenBlock(condition: Boolean) {
        val result = condition.then(
            trueBlock = { "执行 then 分支" },
            falseBlock = { "执行 else 分支" }
        )
        println("Boolean.then(代码块)($condition): $result (期望: ${if (condition) "执行 then 分支" else "执行 else 分支"})")
    }
}
