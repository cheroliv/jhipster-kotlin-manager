buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${properties["nav_version"]}")
    }
}
plugins {
    id("com.android.application") version ("7.1.1") apply (false)
    id("com.android.library") version ("7.1.1") apply (false)
    id("org.jetbrains.kotlin.android") version ("1.6.21") apply (false)
    id("org.jetbrains.kotlin.jvm") version ("1.6.21") apply (false)
}
val pathSeparator: String = System.getProperty("file.separator")

tasks.register<Delete>("clean") {
    description = "Delete directory build"
    group = "build"
    delete(rootProject.buildDir)
}

tasks.register<GradleBuild>("serve") {
    description = "lance le server backend de l'application"
    buildFile = File("${rootDir.path}${pathSeparator}backend${pathSeparator}build.gradle")
    tasks = listOf("bootRun")
}
/*
ext {
    kotlin_version = '1.6.20'
    activityVersion = '1.4.0'
    appCompatVersion = '1.4.1'
    constraintLayoutVersion = '2.1.3'
    coreTestingVersion = '2.1.0'
    coroutines = '1.3.9'
    lifecycleVersion = '2.4.1'
    materialVersion = '1.5.0'
    roomVersion = '2.4.2'
    // testing
    junitVersion = '4.13.2'
    espressoVersion = '3.4.0'
    androidxJunitVersion = '1.1.3'
}
 */