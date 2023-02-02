import org.gradle.api.JavaVersion.VERSION_11
import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    kotlin("jvm")
    id("java-library")
}

dependencies {
    DomainDeps.implementations.forEach { implementation(dependency(it)) }
    DomainDeps.testImplementations.forEach { testImplementation(dependency(it)) }
}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_11
}