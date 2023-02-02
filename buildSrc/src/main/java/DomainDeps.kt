object DomainDeps {
    const val implementation = "implementation"
    const val testImplementation = "testImplementation"
    const val kapt = "kapt"
    const val annotationProcessor = "annotationProcessor"
    const val testAnnotationProcessor = "testAnnotationProcessor"
    const val KOIN_VERSION = "koin_version"
    const val BLANK = ""

    @JvmStatic
    val domainDeps: Map<String, String?> by lazy {
        mapOf("io.insert-koin:koin-core" to KOIN_VERSION)
    }

    @JvmStatic
    val domainTestDeps: Map<String, String?> by lazy {
        mapOf(
            "org.jetbrains.kotlin:kotlin-test" to BLANK,
            "org.jetbrains.kotlin:kotlin-test-junit" to BLANK,
            "io.insert-koin:koin-test" to KOIN_VERSION,
            "io.insert-koin:koin-test-junit4" to KOIN_VERSION,
        )
    }
}
