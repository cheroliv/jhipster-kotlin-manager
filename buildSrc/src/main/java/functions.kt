import AndroidDeps.androidTestImplementation
import AndroidDeps.androidTestImplementations
import AndroidDeps.annotationProcessors
import AndroidDeps.implementations
import AndroidDeps.kapts
import AndroidDeps.testAnnotationProcessors
import AndroidDeps.testImplementations
import Constants.WEBAPP_SRC
import DomainDeps.annotationProcessor
import DomainDeps.kapt
import DomainDeps.testAnnotationProcessor
import DomainDeps.testImplementation
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.exclude
import java.util.StringTokenizer

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

fun Map.Entry<String, String?>.toDependency(project: Project) = key + when (value) {
    "" -> ""
    else -> ":${project.properties[value]}"
}
/*=================================================================================*/

fun Project.androidDependencies() {
    implementations.forEach { dependencies.add(DomainDeps.implementation, it.toDependency(this)) }
    testImplementations.forEach {
        dependencies.add(
            testImplementation,
            it.toDependency(this)
        )
    }
    androidTestImplementations.forEach {
        when (it.key) {
            "androidx.test.espresso:espresso-core" -> dependencies.add(
                androidTestImplementation,
                it.toDependency(this)
            ) {
                exclude("com.android.support", "support-annotations")
            }
            else -> dependencies.add(androidTestImplementation, it.toDependency(this))
        }
    }
    kapts.forEach { dependencies.add(kapt, it.toDependency(this)) }
    annotationProcessors.forEach {
        dependencies.add(
            annotationProcessor,
            it.toDependency(this)
        )
    }
    testAnnotationProcessors.forEach {
        dependencies.add(
            testAnnotationProcessor,
            it.toDependency(this)
        )
    }
}
/*=================================================================================*/
fun Project.webAppSrc():List<String> =
    StringTokenizer(properties[WEBAPP_SRC].toString(), ",")
        .toList()
        .map { it.toString() }
/*=================================================================================*/
