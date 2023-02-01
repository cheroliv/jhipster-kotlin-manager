import Versions.kotlin_version
import DomainDeps.KOIN_VERSION

object AndroidDeps {
    private const val ROOM_VERSION = "room_version"
    private const val ANDROIDX_CORE_VERSION = "androidx_core_version"
    private const val MOCKITO_KOTLIN_VERSION = "mockito_kotlin_version"

    const val NAV_VERSION = "nav_version"
    private const val KOIN_ANDROID_VERSION = "koin_android_version"
    private const val RETROFIT_VERSION = "retrofit_version"
    private const val MATERIAL_VERSION = "material_version"
    private const val ESPRESSO_VERSION = "espresso_version"
    private const val ANDROIDX_LIFECYCLE_VERSION = "androidx_lifecycle_version"
    private const val ANDROIDX_JUNIT_VERSION = "androidx_junit_version"
    private const val CONSTRAINT_LAYOUT_VERSION = "constraint_layout_version"
    private const val ANDROIDX_ARCH_CORE_VERSION = "androidx_arch_core_version"
    private const val APP_COMPAT_VERSION = "app_compat_version"
    private const val KOTLINX_COROUTINES_VERSION = "kotlinx_coroutines_version"

    val kapts = mapOf<String, String?>("androidx.room:room-compiler" to ROOM_VERSION)
    val annotationProcessors = mapOf<String, String?>()
    val testAnnotationProcessors =
        mapOf<String, String?>("androidx.room:room-compiler" to ROOM_VERSION)
    val implementations: Map<String, String?> = mapOf(
        "androidx.core:core-ktx" to ANDROIDX_CORE_VERSION,
        "androidx.appcompat:appcompat" to APP_COMPAT_VERSION,
        "com.google.android.material:material" to MATERIAL_VERSION,
        "androidx.constraintlayout:constraintlayout" to CONSTRAINT_LAYOUT_VERSION,
        "org.jetbrains.kotlinx:kotlinx-coroutines-core" to KOTLINX_COROUTINES_VERSION,
        "org.jetbrains.kotlinx:kotlinx-coroutines-android" to KOTLINX_COROUTINES_VERSION,
        "androidx.navigation:navigation-fragment-ktx" to NAV_VERSION,
        "androidx.navigation:navigation-ui-ktx" to NAV_VERSION,
        "androidx.navigation:navigation-dynamic-features-fragment" to NAV_VERSION,
        "androidx.room:room-runtime" to ROOM_VERSION,
        "androidx.room:room-guava" to ROOM_VERSION,
        "androidx.room:room-paging" to ROOM_VERSION,
        "androidx.lifecycle:lifecycle-viewmodel-ktx" to ANDROIDX_LIFECYCLE_VERSION,
        "androidx.lifecycle:lifecycle-livedata-ktx" to ANDROIDX_LIFECYCLE_VERSION,
        "androidx.lifecycle:lifecycle-common-java8" to ANDROIDX_LIFECYCLE_VERSION,
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7" to kotlin_version,
        "androidx.constraintlayout:constraintlayout" to CONSTRAINT_LAYOUT_VERSION,
        "com.google.android.material:material" to MATERIAL_VERSION,
        "io.insert-koin:koin-core" to KOIN_VERSION,
        "io.insert-koin:koin-android" to KOIN_ANDROID_VERSION,
        "io.insert-koin:koin-androidx-workmanager" to KOIN_ANDROID_VERSION,
        "io.insert-koin:koin-androidx-navigation" to KOIN_ANDROID_VERSION,
    )
    val androidTestImplementations:Map<String, String?> = mapOf(
        "org.jetbrains.kotlin:kotlin-test" to "",
        "org.jetbrains.kotlin:kotlin-test" to "",
        "org.jetbrains.kotlin:kotlin-test-junit" to "",
        "androidx.test.ext:junit" to ANDROIDX_JUNIT_VERSION,
        "org.mockito.kotlin:mockito-kotlin" to MOCKITO_KOTLIN_VERSION,
        "androidx.navigation:navigation-testing" to NAV_VERSION,
        "androidx.arch.core:core-testing" to ANDROIDX_ARCH_CORE_VERSION,
        "androidx.test.espresso:espresso-core" to ESPRESSO_VERSION,
        "androidx.test.ext:junit" to ANDROIDX_JUNIT_VERSION,
        "io.insert-koin:koin-test-junit4" to KOIN_VERSION,
        "io.insert-koin:koin-test" to KOIN_VERSION,
    )
    val testImplementations = mapOf<String, String?>(
        "org.mockito.kotlin:mockito-kotlin" to MOCKITO_KOTLIN_VERSION,
        "org.mockito.kotlin:mockito-kotlin" to MOCKITO_KOTLIN_VERSION,
        "com.squareup.retrofit2:retrofit" to RETROFIT_VERSION,
        "com.squareup.retrofit2:converter-moshi" to RETROFIT_VERSION,
        "androidx.room:room-testing" to ROOM_VERSION,
        "io.insert-koin:koin-test" to KOIN_VERSION,
        "io.insert-koin:koin-test-junit4" to KOIN_VERSION,
    )
}