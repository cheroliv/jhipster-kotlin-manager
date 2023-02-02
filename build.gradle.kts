import BuildTools.dependency
import BuildTools.displayJdl
import BuildTools.move
import BuildTools.webAppSrc
import Constants.WEBAPP
import Constants.WEBAPP_SRC
import Constants.sep


/*=================================================================================*/
buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        BuildDeps.buildDependencies
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
tasks.register<Copy>("exportWebappSource") {
    group = WEBAPP
    description = "copy sources from webapp into webapp-src"
    doFirst {
        webAppSrc
            .forEach { move(it, WEBAPP, WEBAPP_SRC) }
    }
}
/*=================================================================================*/
tasks.register<Copy>("syncWebappSource") {
    group = WEBAPP
    description = "copy sources from webapp-src into webapp"
    doFirst {
        webAppSrc
            .forEach { move(it, WEBAPP_SRC, WEBAPP) }
    }
}
/*=================================================================================*/
tasks.register("jdl") {
    //export
//    dependsOn("exportWebappSource")
    doFirst {
        projectDir.displayJdl()
        //cmdline
    }
    //sync
//    finalizedBy("syncWebappSource")
}
/*=================================================================================*/
tasks.register("displayWebappSrc") {
    doFirst {
        webAppSrc
            .reduce { acc, s -> "$acc\n\t$s" }
            .run { println("$WEBAPP_SRC: $this\n") }
    }
}
/*=================================================================================*/
tasks.register("printDependencies") {
    description = "printDependencies"
    group = "build"
    doFirst { println("\t${project.name} dependencies") }
    doLast {
        mapOf(
            "buildDependencies:" to BuildDeps.buildDependencies,
            "domainDeps:" to Deps.domainDeps,
            "domainTestDeps:" to Deps.domainTestDeps,
        ).forEach { module ->
            println(module.key)
            module.value.forEach { println(dependency(it)) }
            println()
        }
    }
}
/*=================================================================================*/