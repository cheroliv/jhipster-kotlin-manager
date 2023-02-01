import AndroidDeps.NAV_VERSION
import Versions.kotlin_version

object BuildDeps {
    private const val JACKSON_VERSION = "jackson_version"
    private const val PUBLISHER_VERSION = "publisher_version"
    val buildDependencies = mapOf<String, String?>(
        "androidx.navigation:navigation-safe-args-gradle-plugin" to NAV_VERSION,
        "org.jetbrains.kotlin:kotlin-gradle-plugin" to kotlin_version,
        "com.fasterxml.jackson.module:jackson-module-kotlin" to JACKSON_VERSION,
        "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml" to JACKSON_VERSION,
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to JACKSON_VERSION,
        "com.github.triplet.gradle:play-publisher" to PUBLISHER_VERSION,
    )
}