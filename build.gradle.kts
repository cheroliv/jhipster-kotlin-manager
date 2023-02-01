import Constants.JDL_FILE
import Constants.WEBAPP
import Constants.WEBAPP_SRC
import Constants.sep
import java.io.ByteArrayOutputStream
import java.lang.System.getProperty
import java.util.*
import kotlin.text.Charsets.UTF_8


/*=================================================================================*/
buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        BuildDeps
            .buildDependencies
            .forEach { classpath("${it.key}:${properties[it.value]}") }
    }
}

/*=================================================================================*/

plugins {
    id("com.android.application") version Versions.android_app_version apply false
    id("com.android.library") version Versions.android_lib_version apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlin_version apply false
    id("org.jetbrains.kotlin.jvm") version Versions.kotlin_version apply false
}
/*=================================================================================*/
tasks.register<Delete>("clean") {
    description = "Delete directory build"
    group = "build"
    doLast { delete(rootProject.buildDir) }
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
    group = WEBAPP
    description = "use system gradle to launch gradle --stop task, to kill webapp process"
    doLast { logger.info(standardOutput.toString()) }
}


/*=================================================================================*/
tasks.register<GradleBuild>("serve") {
    group = WEBAPP
    description = "launch ceelo backend web application"
    dir = File(buildString {
        append(rootDir.path)
        append(sep)
        append(WEBAPP)
    })
    tasks = listOf("bootRun")
}
/*=================================================================================*/

tasks.register<GradleBuild>("checkWebapp") {
    group = WEBAPP
    description = "launch ceelo backend web application"
    dir = File(buildString {
        append(rootDir.path)
        append(sep)
        append(WEBAPP)
    })
    tasks = listOf("check")
}
/*=================================================================================*/
val webappSrc: List<String> by lazy {
    StringTokenizer(properties[WEBAPP_SRC].toString(), ",")
        .toList()
        .map { it.toString() }
}
/*=================================================================================*/
tasks.register("displayWebappSrc") {
    doFirst {
        webappSrc
            .reduce { acc, s -> "$acc\n\t$s" }
            .run { println("$WEBAPP_SRC: $this\n") }
    }
}
/*=================================================================================*/
fun Copy.move(
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
            .isDirectory -> layout
            .projectDirectory
            .dir(from)
            .dir(path)
        else -> layout
            .projectDirectory
            .dir(from)
            .file(path)
    })
    into(layout.projectDirectory.dir(into))
}

/*=================================================================================*/

tasks.register<Copy>("exportWebappSource") {
    group = WEBAPP
    description = "copy sources from webapp into webapp-src"
    doFirst { webappSrc.forEach { move(it, WEBAPP, WEBAPP_SRC) } }
}

/*=================================================================================*/

tasks.register<Copy>("syncWebappSource") {
    group = WEBAPP
    description = "copy sources from webapp-src into webapp"
    doFirst { webappSrc.forEach { move(it, WEBAPP_SRC, WEBAPP) } }
}

/*=================================================================================*/

tasks.register("jdl") {
    fun displayJdl() {
        projectDir
            .listFiles()
            ?.first { it.name == WEBAPP }
            ?.listFiles()
            ?.first { it.name == JDL_FILE }
            ?.readText(UTF_8)
            .run { println("$WEBAPP$sep$JDL_FILE:\n$this") }
    }
    //export
//    dependsOn("exportWebappSource")
    doFirst {
        displayJdl()
        //cmdline
    }
    //sync
//    finalizedBy("syncWebappSource")
}

/*=================================================================================*/