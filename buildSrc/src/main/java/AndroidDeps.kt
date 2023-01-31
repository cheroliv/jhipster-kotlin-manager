object AndroidDeps {
    private const val ROOM_VERSION = "room_version"
    private const val ANDROIDX_CORE_VERSION = "androidx_core_version"
    private const val MOCKITO_KOTLIN_VERSION = "mockito_kotlin_version"

    val kapts = mapOf<String, String?>("androidx.room:room-compiler:" to ROOM_VERSION)
    val annotationProcessors = mapOf<String, String?>()
    val testAnnotationProcessors = mapOf<String, String?>(
        "androidx.room:room-compiler:" to ROOM_VERSION
    )

    /*
        implementation(]}")
        implementation(]}")
        implementation("androidx.navigation:navigation-fragment-ktx:${properties["nav_version"]}")
        implementation("androidx.navigation:navigation-ui-ktx:${properties["nav_version"]}")
        implementation("androidx.navigation:navigation-dynamic-features-fragment:${properties["nav_version"]}")
        implementation("androidx.room:room-runtime:${properties["room_version"]}")
        implementation("androidx.room:room-guava:${properties["room_version"]}")
        implementation("androidx.room:room-paging:${properties["room_version"]}")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${properties["androidx_lifecycle_version"]}")//androidx_lifecycle_version
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:${properties["androidx_lifecycle_version"]}")
        implementation("androidx.lifecycle:lifecycle-common-java8:${properties["androidx_lifecycle_version"]}")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${properties["kotlin_version"]}")
        implementation("androidx.constraintlayout:constraintlayout:${properties["constraint_layout_version"]}")
        implementation("com.google.android.material:material:${properties["material_version"]}")
        implementation("io.insert-koin:koin-core:${properties["koin_version"]}")
        implementation("io.insert-koin:koin-android:${properties["koin_android_version"]}")
        implementation("io.insert-koin:koin-androidx-workmanager:${properties["koin_android_version"]}")
        implementation("io.insert-koin:koin-androidx-navigation:${properties["koin_android_version"]}")
    */
    val implementations = mapOf<String, String?>(
        "androidx.core:core-ktx" to ANDROIDX_CORE_VERSION,
        "androidx.appcompat:appcompat" to "app_compat_version",
        "com.google.android.material:material" to "material_version",
        "androidx.constraintlayout:constraintlayout" to "constraint_layout_version",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core" to "kotlinx_coroutines_version",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android" to "kotlinx_coroutines_version",
        )

    /*
        androidTestImplementation("org.jetbrains.kotlin:kotlin-test")
        androidTestImplementation("org.jetbrains.kotlin:kotlin-test-junit")
        androidTestImplementation("androidx.test.ext:junit:${properties["androidx_junit_version"]}")
        androidTestImplementation("org.mockito.kotlin:mockito-kotlin:${properties["mockito_kotlin_version"]}")
        androidTestImplementation("androidx.navigation:navigation-testing:${properties["nav_version"]}")
        androidTestImplementation("androidx.arch.core:core-testing:${properties["androidx_arch_core_version"]}")
        androidTestImplementation("androidx.test.espresso:espresso-core:${properties["espresso_version"]}") {
            exclude("com.android.support", "support-annotations")
        }
        androidTestImplementation("androidx.test.ext:junit:${properties["androidx_junit_version"]}")
        androidTestImplementation("io.insert-koin:koin-test-junit4:${properties["koin_version"]}")
        androidTestImplementation("io.insert-koin:koin-test:${properties["koin_version"]}")
    */
    val androidTestImplementations = mapOf<String, String?>(
        "org.jetbrains.kotlin:kotlin-test" to null
    )

    /*
        testImplementation("org.mockito.kotlin:mockito-kotlin:${properties["mockito_kotlin_version"]}")
        testImplementation("com.squareup.retrofit2:retrofit:${properties["retrofit_version"]}")
        testImplementation("com.squareup.retrofit2:converter-moshi:${properties["retrofit_version"]}")
        testImplementation("androidx.room:room-testing:${properties["room_version"]}")
        testImplementation("io.insert-koin:koin-test:${properties["koin_version"]}")
        testImplementation("io.insert-koin:koin-test-junit4:${properties["koin_version"]}")
     */
    val testImplementations = mapOf<String, String?>(
        "org.mockito.kotlin:mockito-kotlin:" to MOCKITO_KOTLIN_VERSION
    )
}