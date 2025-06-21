import hos.utils.*
import kotlin.test.Test

/**
 * <p>Title: FileUtilsTest </p>
 * <p>Description: 用于测试文件类型识别逻辑 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-15 15:00
 * @version : 1.0
 */
class FileUtilsTest {

    @Test
    fun main() {
        println("=== 测试 extractFileExtension ===")
        testExtractFileExtension("test.txt", ".txt")
        testExtractFileExtension("image.jpg", ".jpg")
        testExtractFileExtension("data.tar.gz", ".gz")
        testExtractFileExtension(".hiddenfile", null)
        testExtractFileExtension("no_extension", null)
        testExtractFileExtension("music.mp3", ".mp3")
        testExtractFileExtension("document.docx", ".docx")

        println("\n=== 测试 getFileType ===")
        testGetFileType("photo.jpg", FileType.IMAGE)
        testGetFileType("song.mp3", FileType.AUDIO)
        testGetFileType("video.mp4", FileType.ALL)
        testGetFileType("report.pdf", FileType.PDF)
        testGetFileType("spreadsheet.xlsx", FileType.EXCEL)
        testGetFileType("document.docx", FileType.WORD)
        testGetFileType("archive.zip", FileType.ALL)
        testGetFileType("manual.chm", FileType.CHM)
        testGetFileType("presentation.ppt", FileType.PPT)
        testGetFileType("app.apk", FileType.APK)
        testGetFileType("page.html", FileType.HTML)
        testGetFileType("", FileType.ALL)
        testGetFileType("no_extension", FileType.ALL)
    }

    private fun testExtractFileExtension(fileName: String, expected: String?) {
        val result = FileUtils.getFileExtension(fileName)
        println("extractFileExtension(\"$fileName\"): $result (期望: $expected)")
    }

    private fun testGetFileType(fileName: String, expected: FileType) {
        val result = FileUtils.getFileType(fileName)
        println("getFileType(\"$fileName\"): ${result.typeName} (期望: ${expected.typeName})")
    }
}
