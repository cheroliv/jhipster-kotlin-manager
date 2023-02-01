import AppConfig.androidTestInstrumentation
import AppConfig.appId
import AppConfig.currentCompileSdk
import AppConfig.currentVersionCode
import AppConfig.currentVersionName
import AppConfig.minSdkVersion
import AppConfig.proguardFile
import AppConfig.proguardRules
import AppConfig.targetSdkVersion
import AndroidDeps.androidTestImplementations
import AndroidDeps.annotationProcessors
import AndroidDeps.implementations
import AndroidDeps.kapts
import AndroidDeps.testAnnotationProcessors
import AndroidDeps.testImplementations
import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    kotlin("android")
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("com.github.triplet.play")
}

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

fun Map.Entry<String,String?>.dep() = key + when (value) {
    null -> ""
    else -> ":${properties[value]}"
}

fun DependencyHandlerScope.dependence() {
    implementations.forEach { implementation(it.dep()) }
    testImplementations.forEach { testImplementation(it.dep()) }
    androidTestImplementations.forEach {
        when (it.key) {
            "androidx.test.espresso:espresso-core" -> androidTestImplementation(it.dep()) {
                exclude("com.android.support", "support-annotations")
            }
            else -> androidTestImplementation(it.dep())
        }
    }
    kapts.forEach { kapt(it.dep()) }
    annotationProcessors.forEach { annotationProcessor(it.dep()) }
    testAnnotationProcessors.forEach { testAnnotationProcessor(it.dep()) }
}


dependencies {
    implementation(project(":domain"))
    dependence()
}