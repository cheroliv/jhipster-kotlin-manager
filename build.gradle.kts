import java.io.ByteArrayOutputStream
import java.lang.System.getProperty

/*=================================================================================*/

buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${properties["nav_version"]}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${properties["kotlin_version"]}")
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
//save source: sauve les sources de webapp dans webapp-src
//import source: copie les sources de webapp-src vers webapp
//TODO: effacer les dossier webapp avec les sources a ajouter, copier les sources 
// de webapp-src vers webapp.
//recuperer build.gradle
// et le jdl
//TODO: task qui sauve les fichiers de webapps dans webapp-src
tasks.register<GradleBuild>("serve") {
    description = "launch ceelo backend web application"
    dir = File(buildString {
        append(rootDir.path)
        append(getProperty("file.separator"))
        append("webapp")
    })
    tasks = listOf("bootRun")
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

project.tasks.register<GradleStop>("gradleStop")

project.tasks.withType<GradleStop> { doLast { logger.info(standardOutput.toString()) } }
/*=================================================================================*/