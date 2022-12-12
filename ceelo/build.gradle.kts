buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${properties["nav_version"]}")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    }
}

plugins {
    id("com.android.application") version ("7.2.1") apply (false)
    id("com.android.library") version ("7.3.1") apply (false)
    id("org.jetbrains.kotlin.android") version ("1.7.20") apply (false)
    id("org.jetbrains.kotlin.jvm") version ("1.7.20") apply (false)
}

tasks.register<Delete>("clean") {
    description = "Delete directory build"
    group = "build"
    delete(rootProject.buildDir)
}

/**
 * groovy dsl version
 * task serve(type: GradleBuild) {
 * description = "lance le server backend de l'application"
 * buildFile = 'backend/build.gradle'
 * tasks = ['bootRun']
 * }
 */
tasks.register<GradleBuild>("webflux") {
    val pathSeparator: String = System.getProperty("file.separator")
    description = "lance le webflux backend de l'application"
    buildFile = File("${rootDir.path}${pathSeparator}webflux${pathSeparator}build.gradle")
    tasks = listOf("bootRun")
}