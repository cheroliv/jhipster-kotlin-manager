import AndroidDeps.androidTestImplementations
import AndroidDeps.annotationProcessors
import AndroidDeps.implementations
import AndroidDeps.kapts
import AndroidDeps.testAnnotationProcessors
import AndroidDeps.testImplementations
import AppConfig.androidTestInstrumentation
import AppConfig.appId
import AppConfig.currentCompileSdk
import AppConfig.currentVersionCode
import AppConfig.currentVersionName
import AppConfig.minSdkVersion
import AppConfig.proguardFile
import AppConfig.proguardRules
import AppConfig.targetSdkVersion
import DomainDeps.toDependency
import org.gradle.api.JavaVersion.VERSION_1_8
import AndroidDeps.androidDependencies

/*=================================================================================*/
plugins {
    kotlin("android")
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("com.github.triplet.play")
}
/*=================================================================================*/
dependencies {
    implementation(project(":domain"))
    androidDependencies()
}
/*=================================================================================*/
//fun Project.androidDependencies() {
//    implementations.forEach { dependencies.add("implementation", it.toDependency(this)) }
//    testImplementations.forEach { dependencies.add("testImplementation", it.toDependency(this)) }
//    androidTestImplementations.forEach {
//        when (it.key) {
//            "androidx.test.espresso:espresso-core" -> dependencies.add(
//                "androidTestImplementation",
//                it.toDependency(this)
//            ) {
//                exclude("com.android.support", "support-annotations")
//            }
//            else -> dependencies.add("androidTestImplementation", it.toDependency(this))
//        }
//    }
//    kapts.forEach { dependencies.kapt(it.toDependency(this)) }
//    annotationProcessors.forEach { dependencies.annotationProcessor(it.toDependency(this)) }
//    testAnnotationProcessors.forEach { dependencies.testAnnotationProcessor(it.toDependency(this)) }
//}
/*=================================================================================*/


android {
    namespace = appId
    compileSdk = currentCompileSdk
    defaultConfig {
        applicationId = appId
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = currentVersionCode
        versionName = currentVersionName
        testInstrumentationRunner = androidTestInstrumentation
        javaCompileOptions {
            mapOf(
                "room.schemaLocation" to "$projectDir/schemas",
                "room.incremental" to "true",
                "room.expandProjection" to "true"
            ).forEach { annotationProcessorOptions.arguments[it.key] = it.value }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile(proguardFile), proguardRules)
        }
    }
    compileOptions {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }
    kotlinOptions { jvmTarget = VERSION_1_8.toString() }
    viewBinding { android.buildFeatures.viewBinding = true }
    packagingOptions { resources.excludes.add("META-INF/atomicfu.kotlin_module") }
}
/*=================================================================================*/




