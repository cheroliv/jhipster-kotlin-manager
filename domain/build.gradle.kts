import org.gradle.api.JavaVersion.VERSION_11
import org.gradle.api.JavaVersion.VERSION_1_8
import BuildTools.dependency
import Deps.domainDeps
import Deps.domainTestDeps

plugins {
    kotlin("jvm")
    id("java-library")
}

dependencies {
    domainDeps.forEach { implementation(dependency(it)) }
    domainTestDeps.forEach { testImplementation(dependency(it)) }
}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_11
}