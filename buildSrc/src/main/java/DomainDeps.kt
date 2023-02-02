
object DomainDeps {
    const val KOIN_VERSION = "koin_version"
    const val BLANK = ""

    val implementations: Map<String, String?> = mapOf(
        "io.insert-koin:koin-core" to KOIN_VERSION,
    )
    val testImplementations: Map<String, String?> = mapOf(
        "org.jetbrains.kotlin:kotlin-test" to BLANK,
        "org.jetbrains.kotlin:kotlin-test-junit" to BLANK,
        "io.insert-koin:koin-test" to KOIN_VERSION,
        "io.insert-koin:koin-test-junit4" to KOIN_VERSION,
    )
}
