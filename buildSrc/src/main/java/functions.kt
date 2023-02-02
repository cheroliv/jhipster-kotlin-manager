import AndroidDeps.androidTestImplementation
import AndroidDeps.androidTestImplementations
import AndroidDeps.annotationProcessors
import AndroidDeps.implementations
import AndroidDeps.kapts
import AndroidDeps.testAnnotationProcessors
import AndroidDeps.testImplementations
import Constants.JDL_FILE
import Constants.WEBAPP
import Constants.WEBAPP_SRC
import Constants.sep
import DomainDeps.annotationProcessor
import DomainDeps.implementation
import DomainDeps.kapt
import DomainDeps.testAnnotationProcessor
import DomainDeps.testImplementation
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.exclude
import java.io.File
import java.util.*
import kotlin.text.Charsets.UTF_8

/*=================================================================================*/
val Project.webAppSrc
    get() = StringTokenizer(properties[WEBAPP_SRC].toString(), ",")
        .toList()
        .map { it.toString() }

/*=================================================================================*/
fun Copy.move(
    path: String,
    from: String,
    into: String
) {
    from(when {
        project.layout
            .projectDirectory
            .dir(from)
            .asFileTree
            .first { it.name == path }
            .isDirectory -> project.layout
            .projectDirectory
            .dir(from)
            .dir(path)
        else -> project.layout
            .projectDirectory
            .dir(from)
            .file(path)
    })
    into(project.layout.projectDirectory.dir(into))
}

/*=================================================================================*/
fun Project.dependency(entry: Map.Entry<String, String?>): String = entry.run {
    key + when (value) {
        ""  -> ""
        else -> ":${properties[value]}"
    }
}
/*=================================================================================*/
fun Project.androidDependencies() {
    implementations.forEach {
        dependencies.add(
            implementation,
            dependency(it)
        )
    }
    testImplementations.forEach {
        dependencies.add(
            testImplementation,
            dependency(it)
        )
    }
    androidTestImplementations.forEach {
        when (it.key) {
            "androidx.test.espresso:espresso-core" -> dependencies.add(
                androidTestImplementation,
                dependency(it)
            ) {
                exclude(
                    "com.android.support",
                    "support-annotations"
                )
            }
            else -> dependencies.add(
                androidTestImplementation,
                dependency(it)
            )
        }
    }
    kapts.forEach { dependencies.add(kapt, dependency(it)) }
    annotationProcessors.forEach {
        dependencies.add(
            annotationProcessor,
            dependency(it)
        )
    }
    testAnnotationProcessors.forEach {
        dependencies.add(
            testAnnotationProcessor,
            dependency(it)
        )
    }
}
/*=================================================================================*/
fun File.displayJdl() {
    listFiles()
        ?.first { it.name == WEBAPP }
        ?.listFiles()
        ?.first { it.name == JDL_FILE }
        ?.readText(UTF_8)
        .run { println("$WEBAPP$sep$JDL_FILE:\n$this") }
}
/*=================================================================================*/
