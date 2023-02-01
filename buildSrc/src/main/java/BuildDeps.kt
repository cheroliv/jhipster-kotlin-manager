
object BuildDeps {
    val buildDeps = mapOf<String, String?>(
        "androidx.navigation:navigation-safe-args-gradle-plugin" to "nav_version",
        "org.jetbrains.kotlin:kotlin-gradle-plugin" to "kotlin_version",
        "com.fasterxml.jackson.module:jackson-module-kotlin" to "jackson_version",
        "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml" to "jackson_version",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to "jackson_version",
        "com.github.triplet.gradle:play-publisher" to "publisher_version",
    )
}