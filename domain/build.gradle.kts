import org.gradle.api.JavaVersion.VERSION_11
import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    kotlin(module = "jvm")
    id("java-library")
}

dependencies {
    // coroutines
//    implementation(dependencyNotation = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["kotlinx_coroutines_version"]}")
    implementation(dependencyNotation = "io.insert-koin:koin-core:${properties["koin_version"]}")
    testImplementation(dependencyNotation = "org.jetbrains.kotlin:kotlin-test")
    testImplementation(dependencyNotation = "org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation (dependencyNotation = "io.insert-koin:koin-test:${properties["koin_version"]}")
    testImplementation(dependencyNotation = "io.insert-koin:koin-test-junit4:${properties["koin_version"]}")
}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_11
}