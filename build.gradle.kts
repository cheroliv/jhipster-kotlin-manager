import AppDeps.appModules
import BuildDeps.buildDependencies
import BuildTools.dependency
import BuildTools.displayJdl
import BuildTools.move
import BuildTools.webAppSrc
import Constants.WEBAPP
import Constants.WEBAPP_SRC
import BuildTools.sep
import DomainDeps.domainDeps
import DomainDeps.domainTestDeps


/*=================================================================================*/
buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        @Suppress("RemoveRedundantQualifierName")
        BuildDeps.buildDependencies
            .forEach { classpath("${it.key}:${properties[it.value]}") }
    }
}
/*=================================================================================*/
plugins {
    kotlin("android") version Versions.kotlin_version apply false
    kotlin("jvm") version Versions.kotlin_version apply false
    id("com.android.application") version Versions.android_app_version apply false
    id("com.android.library") version Versions.android_lib_version apply false
}
/*=================================================================================*/
tasks.register<Delete>("clean") {
    description = "Delete directory build"
    group = "build"
    doLast { delete(rootProject.buildDir) }
}
/*=================================================================================*/
project.tasks.register<GradleStop>("gradleStop") {
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
tasks.register("printWebappSrc") {
    description = "print webapp sources"
    group = WEBAPP
    doFirst {
        webAppSrc
            .reduce { acc, s -> "$acc\n\t$s" }
            .run { println("$WEBAPP_SRC: $this\n") }
    }
}
/*=================================================================================*/
tasks.register("printDependencies") {
    description = "print project dependencies"
    group = WEBAPP
    doFirst { println("${project.name} dependencies") }
    doLast {
        mutableMapOf(
            "buildDependencies" to buildDependencies,
            "domainDeps" to domainDeps,
            "domainTestDeps" to domainTestDeps,
        ).apply { putAll(appModules) }
            .forEach { module ->
                if (module.value.isNotEmpty()) {
                    println("${module.key}:")
                    module.value.forEach { println(dependency(it)) }
                    println()
                }
            }
    }
}
/*=================================================================================*/
tasks.register("jdl") {
    group = WEBAPP
    description = "launch jdl source generator"
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