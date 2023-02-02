import org.gradle.api.JavaVersion.VERSION_11
import org.gradle.api.JavaVersion.VERSION_1_8
import BuildStructure.dependency
import DomainDeps.implementations
import DomainDeps.testImplementations

plugins {
    kotlin("jvm")
    id("java-library")
}

dependencies {
    implementations.forEach { implementation(dependency(it)) }
    testImplementations.forEach { testImplementation(dependency(it)) }
}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_11
}