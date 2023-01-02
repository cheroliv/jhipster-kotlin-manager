

import java.lang.System.getProperty
import java.io.ByteArrayOutputStream

buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${properties["nav_version"]}")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${properties["kotlin_version"]}")
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

tasks.register<GradleBuild>("serve") {
    description = "lance le server backend de l'application"
    val pathSeparator: String = getProperty("file.separator")
    buildFile = File("${rootDir.path}${pathSeparator}webapp${pathSeparator}build.gradle")
    tasks = listOf("bootRun")
}


open class GradleStop : Exec() {
    @Suppress("PropertyName")
    private val GRADLE_PATH="/.sdkman/candidates/gradle/current/bin/gradle"
    init {
        workingDir = project.rootDir
        @Suppress("LeakingThis")
        commandLine("${getProperty("user.home")}${GRADLE_PATH}", "--stop")
        standardOutput = ByteArrayOutputStream()
    }
}

project.tasks.register<GradleStop>("gradleStop")

project.tasks.withType<GradleStop> {
    doLast { logger.info(standardOutput.toString()) }
}
