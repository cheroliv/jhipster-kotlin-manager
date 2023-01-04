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

tasks.register<Copy>("exportWebappSource") {
    group = "webbap"
    description = "copy sources from webapp into webapp-src"
    doFirst{
        from(layout.projectDirectory.file("webapp/build.gradle"))
        into(layout.projectDirectory.dir("webapp-src"))

        from(layout.projectDirectory.file("webapp/settings.gradle"))
        into(layout.projectDirectory.dir("webapp-src"))

        from(layout.projectDirectory.file("webapp/ceelo.jdl"))
        into(layout.projectDirectory.dir("webapp-src"))

        from(layout.projectDirectory.dir("webapp/src/main/kotlin"))
        into(layout.projectDirectory.dir("webapp-src/main/kotlin"))
    }
}

tasks.register<Copy>("syncWebappSource") {
    group = "webbap"
    description = "copy sources from webapp-src into webapp"
    doFirst{
        from(layout.projectDirectory.file("webapp-src/build.gradle"))
        into(layout.projectDirectory.dir("webapp"))

        from(layout.projectDirectory.file("webapp-src/settings.gradle"))
        into(layout.projectDirectory.dir("webapp"))

        from(layout.projectDirectory.file("webapp-src/ceelo.jdl"))
        into(layout.projectDirectory.dir("webapp"))

    }
}
tasks.register("jdlWebappSource") {
    group = "webbap"
    description = ""
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

//project.tasks.withType<GradleStop> { doLast { logger.info(standardOutput.toString()) } }
/*=================================================================================*/
