import Constants.JDL_FILE
import Constants.WEBAPP
import Constants.WEBAPP_SRC
import Constants.sep
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
tasks.register("displayWebappSrc") {
    doFirst {
        webAppSrc()
            .reduce { acc, s -> "$acc\n\t$s" }
            .run { println("$WEBAPP_SRC: $this\n") }
    }
}

/*=================================================================================*/

tasks.register<Copy>("exportWebappSource") {
    group = WEBAPP
    description = "copy sources from webapp into webapp-src"
    doFirst {
        webAppSrc()
            .forEach { move(it, WEBAPP, WEBAPP_SRC) }
    }
}

/*=================================================================================*/

tasks.register<Copy>("syncWebappSource") {
    group = WEBAPP
    description = "copy sources from webapp-src into webapp"
    doFirst {
        webAppSrc()
            .forEach { move(it, WEBAPP_SRC, WEBAPP) }
    }
}

/*=================================================================================*/
fun displayJdl() {
    projectDir
        .listFiles()
        ?.first { it.name == WEBAPP }
        ?.listFiles()
        ?.first { it.name == JDL_FILE }
        ?.readText(UTF_8)
        .run { println("$WEBAPP$sep$JDL_FILE:\n$this") }
}
tasks.register("jdl") {

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

tasks.register("printDependencies") {
    description = "printDependencies"
    group = "build"
    doLast { println("printDependencies") }
}
/*=================================================================================*/
