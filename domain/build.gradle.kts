import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    kotlin(module = "jvm")
    id("java-library")
    application
}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
}

application {
    mainClass.set("game.ceelo.domain.CeeloKt")
}

dependencies {
    implementation(dependencyNotation = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["kotlinx_coroutines_version"]}")
    testImplementation(dependencyNotation = "junit:junit:${properties["junit_version"]}")
}