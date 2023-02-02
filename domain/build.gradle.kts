import org.gradle.api.JavaVersion.VERSION_11
import org.gradle.api.JavaVersion.VERSION_1_8
import DomainDeps.toDependency

plugins {
    kotlin("jvm")
    id("java-library")
}

dependencies {
    DomainDeps.implementations.forEach { implementation(it.toDependency(project)) }
    DomainDeps.testImplementations.forEach { testImplementation(it.toDependency(project)) }
}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_11
}

