import java.io.ByteArrayOutputStream
import java.lang.System.getProperty
import java.util.*

/*=================================================================================*/

buildscript {
    val jacksonVersion = "2.14.1"

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${properties["nav_version"]}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${properties["kotlin_version"]}")
        classpath("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
        classpath("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
        classpath("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    }
}
/*=================================================================================*/

plugins {
    id("com.android.application") version ("7.2.1") apply (false)
    id("com.android.library") version ("7.3.1") apply (false)
    id("org.jetbrains.kotlin.android") version ("1.7.20") apply (false)
    id("org.jetbrains.kotlin.jvm") version ("1.7.20") apply (false)
}

/*=================================================================================*/

tasks.register<Delete>("clean") {
    description = "Delete directory build"
    group = "build"
    delete(rootProject.buildDir)
}

/*=================================================================================*/

fun Copy.syncSrc(
    path: String,
    from: String,
    into: String
) {
    from(when {
        layout
            .projectDirectory
            .dir(from)
            .asFileTree
            .first { it.name == path }
            .isDirectory -> layout.projectDirectory.dir(from).dir(path)
        else -> layout
            .projectDirectory
            .dir(from)
            .file(path)
    })
    into(layout.projectDirectory.dir(into))
}
/*=================================================================================*/

tasks.register<Copy>("exportWebappSource") {
    group = "webbap"
    description = "copy sources from webapp into webapp-src"

    doFirst {
        StringTokenizer(properties["webapp_src"].toString(), ",")
            .toList()
            .forEach { syncSrc(it as String, "webapp", "webapp-src") }
    }
}

/*=================================================================================*/

tasks.register<Copy>("syncWebappSource") {
    group = "webbap"
    description = "copy sources from webapp-src into webapp"

    doFirst {
        StringTokenizer(properties["webapp_src"] as String, ",")
            .iterator()
            .forEach { syncSrc(it as String, "webapp-src", "webapp") }
    }
}
/*=================================================================================*/


tasks.register<GradleBuild>("serve") {
    group = "webapp"
    description = "launch ceelo backend web application"
    dir = File(buildString {
        append(rootDir.path)
        append(getProperty("file.separator"))
        append("webapp")
    })
    tasks = listOf("bootRun")
}
/*=================================================================================*/

tasks.register<GradleBuild>("checkWebapp") {
    group = "webapp"
    description = "launch ceelo backend web application"
    dir = File(buildString {
        append(rootDir.path)
        append(getProperty("file.separator"))
        append("webapp")
    })
    tasks = listOf("check")
}
/*=================================================================================*/
open class GradleStop : Exec() {
    init {
        description = "Stop any gradle daemons running!"
        workingDir = project.rootDir
        @Suppress("LeakingThis")
        commandLine(buildString {
            append(getProperty("user.home"))
            append("/.sdkman/candidates/gradle/current/bin/gradle")
        }, "--stop")
        standardOutput = ByteArrayOutputStream()
    }
}
/*=================================================================================*/

project.tasks.register<GradleStop>("gradleStop") {
    group = "webapp"
    description = "use system gradle to launch gradle --stop task, to kill webapp process"
    doLast { logger.info(standardOutput.toString()) }
}
/*=================================================================================*/
