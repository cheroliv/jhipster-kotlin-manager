import AppDeps.appModules
import BuildDeps.buildDependencies
import BuildTools.dependency
import BuildTools.jdl
import BuildTools.move
import BuildTools.sep
import BuildTools.webAppSrc
import Constants.WEBAPP
import Constants.WEBAPP_SRC
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
    kotlin("jvm") version Versions.kotlin_version apply false
    kotlin("android") version Versions.kotlin_version apply false
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
    doFirst {
        dir = File(buildString {
            append(rootDir.path)
            append(sep)
            append(WEBAPP)
        })
    }
    doLast {
        tasks = listOf("bootRun")
    }
}
/*=================================================================================*/
tasks.register<GradleBuild>("checkWebapp") {
    group = WEBAPP
    description = "launch ceelo backend web application"
    doFirst {
        dir = File(buildString {
            append(rootDir.path)
            append(sep)
            append(WEBAPP)
        })
    }
    doLast {
        tasks = listOf("check")
    }
}
/*=================================================================================*/
tasks.register("moveWebappNode") {
    doLast {
        ant.withGroovyBuilder {
            "move"(
                "webapp/node_modules" to "$rootDir/webapp-src/node_modules",
            )
        }
    }
}
/*=================================================================================*/
tasks.register<Copy>("exportWebappSource") {
    group = WEBAPP
    description = "copy sources from webapp into webapp-src"
    dependsOn("moveWebappNode")
    doLast {
        webAppSrc
            .forEach { move(it, WEBAPP, WEBAPP_SRC) }
    }
}
/*=================================================================================*/
tasks.register<Copy>("syncWebappSource") {
    group = WEBAPP
    description = "copy sources from webapp-src into webapp"
    doLast {
        webAppSrc
            .forEach { move(it, WEBAPP_SRC, WEBAPP) }
    }
}
/*=================================================================================*/
tasks.register<Tar>("tarWebapp") {
    dependsOn("moveWebappNpm")
    group = WEBAPP
    description = "tar webapp"
    doLast {
        archiveFileName.set("webapp.tar")
        destinationDirectory.set(File("${rootDir.absolutePath}$sep$WEBAPP_SRC"))
        setOf(
            "build",
            "target",
            "node_modules"
        ).forEach { dir -> exclude { it.name == dir } }
    }
}
/*=================================================================================*/
tasks.register("printWebappSrc") {
    description = "print webapp sources"
    group = WEBAPP
    doLast {
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
val sqlQueries="""SELECT * FROM JHI_AUTHORITY;
SELECT * FROM JHI_USER;
SELECT * FROM JHI_USER_AUTHORITY;
SELECT * FROM INFORMATION_SCHEMA.COLUMNS ;"""

tasks.register<Exec>("jhipster") {
    group = WEBAPP
    description = "launch jdl source generator"
//    dependsOn("exportWebappSource","nvmAdjust").run { println("export")}

    doLast {
        //delete webapp
        //create webapp
        //copier ceelo.jdl
        //copier yo-rc.json?
        println("cmdline")
        // commandLine("jhipster")
        jdl()
    }
//    finalizedBy("syncWebappSource")
}
/*=================================================================================*/
