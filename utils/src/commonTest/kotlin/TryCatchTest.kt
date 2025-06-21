import hos.ext.doTryCatch
import hos.ext.runCatchingToResult
import kotlin.test.Test

/**
 * <p>Title: TryCatchTest </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 20:58
 * @version : 1.0
 */
class TryCatchTest {

    @Test
    fun main() {
        // 执行一个包含异常的代码块，并处理异常
        val result = doTryCatch({
            throw Exception("测试异常")
        }) {
            println(it.message)
            "异常处理"
        }
        println("result:$result")

        // 在处理结果中执行一个不会抛出异常的代码块
        val runCatchingToResult = result.runCatchingToResult {
            true
        }
        println("runCatchingToResult:$runCatchingToResult")

        // 在处理结果中执行一个会抛出异常的代码块
        val runCatchingToResultException = result.runCatchingToResult {
            throw Exception("测试异常")
        }
        println("runCatchingToResult:$runCatchingToResultException")
    }

}