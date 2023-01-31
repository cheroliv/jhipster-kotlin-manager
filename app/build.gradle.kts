import org.gradle.api.JavaVersion.VERSION_1_8
//import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("com.github.triplet.play")
//    `kotlin-dsl`
}

android {
    namespace = "game.ceelo"
    compileSdk = 33

    defaultConfig {
        applicationId = "game.ceelo"
        minSdk = 23
        targetSdk = 33
        versionCode = 11
        versionName = "0.0.11"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            mapOf(
                "room.schemaLocation" to "$projectDir/schemas",
                "room.incremental" to "true",
                "room.expandProjection" to "true"
            ).forEach { annotationProcessorOptions.arguments[it.key] = it.value }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }

    kotlinOptions { jvmTarget = VERSION_1_8.toString() }
    viewBinding { android.buildFeatures.viewBinding = true }
    packagingOptions {
        resources.excludes.add("META-INF/atomicfu.kotlin_module")
    }
}

dependencies {
    implementation(project(":domain"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:${properties["app_compat_version"]}")
    implementation("com.google.android.material:material:${properties["material_version"]}")
    implementation("androidx.constraintlayout:constraintlayout:${properties["constraint_layout_version"]}")
    androidTestImplementation("org.jetbrains.kotlin:kotlin-test")
    androidTestImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    androidTestImplementation("androidx.test.ext:junit:${properties["androidx_junit_version"]}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${properties["mockito_kotlin_version"]}")
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:${properties["mockito_kotlin_version"]}")

    // Retrofit
    testImplementation("com.squareup.retrofit2:retrofit:${properties["retrofit_version"]}")
    testImplementation("com.squareup.retrofit2:converter-moshi:${properties["retrofit_version"]}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["kotlinx_coroutines_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${properties["kotlinx_coroutines_version"]}")

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:${properties["nav_version"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${properties["nav_version"]}")
    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:${properties["nav_version"]}")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:${properties["nav_version"]}")

    kapt("androidx.room:room-compiler:${properties["room_version"]}")
    testAnnotationProcessor("androidx.room:room-compiler:${properties["room_version"]}")
    //noinspection GradleDependency
    implementation("androidx.room:room-runtime:${properties["room_version"]}")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:${properties["room_version"]}")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:${properties["room_version"]}")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:${properties["room_version"]}")

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.5.1")

    // Kotlin components
    //noinspection GradleDependency
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${properties["kotlin_version"]}")

    // UI
    implementation("androidx.constraintlayout:constraintlayout:${properties["constraint_layout_version"]}")
    implementation("com.google.android.material:material:${properties["material_version"]}")

    // Testing
    androidTestImplementation ("androidx.arch.core:core-testing:${properties["androidx_arch_core_version"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${properties["espresso_version"]}") {
        exclude (group= "com.android.support", module= "support-annotations")
    }
    androidTestImplementation("androidx.test.ext:junit:${properties["androidx_junit_version"]}")

    // Koin main features for Android
    implementation("io.insert-koin:koin-core:${properties["koin_version"]}")
    implementation("io.insert-koin:koin-android:${properties["koin_android_version"]}")
    // Jetpack WorkManager
    implementation("io.insert-koin:koin-androidx-workmanager:${properties["koin_android_version"]}")
    // Navigation Graph
    implementation("io.insert-koin:koin-androidx-navigation:${properties["koin_android_version"]}")
    // Koin testing tools
    testImplementation("io.insert-koin:koin-test:${properties["koin_version"]}")
    androidTestImplementation("io.insert-koin:koin-test:${properties["koin_version"]}")
    // Needed JUnit version
    testImplementation("io.insert-koin:koin-test-junit4:${properties["koin_version"]}")
    androidTestImplementation("io.insert-koin:koin-test-junit4:${properties["koin_version"]}")
}