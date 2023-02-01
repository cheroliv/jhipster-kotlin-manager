import org.gradle.api.JavaVersion.VERSION_11
import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    kotlin("jvm")
    id("java-library")
}

dependencies {
    DomainDeps.implementations.forEach { implementation(it.toDependency()) }
    DomainDeps.testImplementations.forEach { testImplementation(it.toDependency()) }
}

fun Map.Entry<String, String?>.toDependency() = key + when (value) {
    "" -> ""
    else -> ":${properties[value]}"
}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_11
}