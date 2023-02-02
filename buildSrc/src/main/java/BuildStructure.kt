import AndroidDeps.androidTestImplementation
import AndroidDeps.androidTestDeps
import AndroidDeps.annotationProcessors
import AndroidDeps.deps
import AndroidDeps.kaptDeps
import AndroidDeps.testAnnotationProcessorDeps
import AndroidDeps.testDeps
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

object BuildStructure {
    /*=================================================================================*/
    @JvmStatic
    val Project.webAppSrc
        get() = StringTokenizer(properties[WEBAPP_SRC].toString(), ",")
            .toList()
            .map { it.toString() }
    /*=================================================================================*/
    @JvmStatic
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
    @JvmStatic
    fun Project.dependency(entry: Map.Entry<String, String?>): String = entry.run {
        key + when (value) {
            null -> ""
            "" -> ""
            else -> ":${properties[value]}"
        }
    }
    /*=================================================================================*/
    @JvmStatic
    fun Project.androidDependencies() {
        deps.forEach {
            dependencies.add(
                implementation,
                dependency(it)
            )
        }
        testDeps.forEach {
            dependencies.add(
                testImplementation,
                dependency(it)
            )
        }
        androidTestDeps.forEach {
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
        kaptDeps.forEach { dependencies.add(kapt, dependency(it)) }
        annotationProcessors.forEach {
            dependencies.add(
                annotationProcessor,
                dependency(it)
            )
        }
        testAnnotationProcessorDeps.forEach {
            dependencies.add(
                testAnnotationProcessor,
                dependency(it)
            )
        }
    }
    /*=================================================================================*/
    @JvmStatic
    fun File.displayJdl() {
        listFiles()
            ?.first { it.name == WEBAPP }
            ?.listFiles()
            ?.first { it.name == JDL_FILE }
            ?.readText(UTF_8)
            .run { println("$WEBAPP$sep$JDL_FILE:\n$this") }
    }
}
/*=================================================================================*/