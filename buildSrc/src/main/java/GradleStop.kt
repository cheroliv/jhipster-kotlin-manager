import Constants.WEBAPP
import org.gradle.api.Action
import org.gradle.api.Task
import org.gradle.api.tasks.Exec
import java.io.ByteArrayOutputStream

open class GradleStop : Exec() {
    init {
        group = WEBAPP
        description = "Stop any gradle daemons running!"+"\n"+
                "use system gradle to launch gradle --stop task, to kill webapp process"
        workingDir = project.rootDir
        @Suppress("LeakingThis")
        commandLine(buildString {
            append(System.getProperty("user.home"))
            append("/.sdkman/candidates/gradle/current/bin/gradle")
        }, "--stop")
        standardOutput = ByteArrayOutputStream()
    }

}