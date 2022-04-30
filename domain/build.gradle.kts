plugins {
    id("java-library")
    kotlin("jvm")
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
application {
    mainClass.set("game.ceelo.domain.CeeloKt")
}
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    testImplementation("junit:junit:4.13.2")
}