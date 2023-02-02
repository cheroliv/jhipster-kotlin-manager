import java.lang.System.getProperty

object Constants {
    const val WEBAPP = "webapp"
    const val WEBAPP_SRC = "webapp_src"
    const val JDL_FILE = "ceelo.jdl"
    @JvmStatic
    val sep: String by lazy { getProperty("file.separator") }
}