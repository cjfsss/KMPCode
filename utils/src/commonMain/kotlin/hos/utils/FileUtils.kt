package hos.utils

/**
 * <p>Title: FileUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-11 14:12
 * @version : 1.0
 */
object FileUtils {

    // 懒加载初始化文件扩展名到文件类型的映射
    private val extensionToTypeMap by lazy {
        mapOf(
            ".m4a" to FileType.AUDIO,
            ".mp3" to FileType.AUDIO,
            ".wav" to FileType.AUDIO,
            ".amr" to FileType.AUDIO,
            ".aac" to FileType.AUDIO,

            ".mp4" to FileType.VIDEO,
            ".mts" to FileType.VIDEO,

            ".jpg" to FileType.IMAGE,
            ".gif" to FileType.IMAGE,
            ".jpeg" to FileType.IMAGE,
            ".bmp" to FileType.IMAGE,

            ".xls" to FileType.EXCEL,
            ".xlsx" to FileType.EXCEL,

            ".txt" to FileType.TXT,

            ".chm" to FileType.CHM,

            ".docx" to FileType.WORD,
            ".doc" to FileType.WORD,

            ".pdf" to FileType.PDF,

            ".ppt" to FileType.PPT,

            ".apk" to FileType.APK,

            ".html" to FileType.HTML
        )
    }

    /**
     * 根据文件名获取文件类型
     *
     * @param fileName 文件名
     * @return 文件类型，如果无法确定则返回FileType.ALL
     */
    fun getFileType(fileName: String): FileType {
        if (fileName.isEmpty()) return FileType.ALL

        val ext = getFileExtension(fileName) ?: return FileType.ALL

        return extensionToTypeMap[ext] ?: FileType.ALL
    }

    /**
     * 提取文件扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名，如果不存在则返回null
     */
    fun getFileExtension(fileName: String): String? {
        val lastDotIndex = fileName.lastIndexOf('.')
        return if (lastDotIndex > 0 && lastDotIndex < fileName.length - 1) {
            fileName.substring(lastDotIndex).lowercase()
        } else {
            null
        }
    }

}

enum class FileType(val type: String, val typeName: String) {
    HTML("text/html", "html"),
    IMAGE("image/*", "image"),
    PDF("application/pdf", "pdf"),
    TXT("text/plain", "txt"),
    AUDIO("audio/*", "audio"),
    VIDEO("video/*", "video"),
    CHM("application/x-chm", "chm"),
    WORD("application/msword", "word"),
    EXCEL("application/vnd.ms-excel", "excel"),
    PPT("application/vnd.ms-powerpoint", "ppt"),
    APK("application/vnd.android.package-archive", "apk"),
    ALL("*/*", "all");
}