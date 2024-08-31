plugins {
    alias(libs.plugins.android.library)
}

val projectMinSdkVersion: Int by rootProject.extra
val projectCompileSdkVersion: Int by rootProject.extra

val projectSourceCompatibility: JavaVersion by rootProject.extra
val projectTargetCompatibility: JavaVersion by rootProject.extra

android {
    namespace = "hidden.api.stub"
    compileSdk = projectCompileSdkVersion

    defaultConfig {
        minSdk = projectMinSdkVersion
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = projectSourceCompatibility
        targetCompatibility = projectTargetCompatibility
    }
}

dependencies {
    compileOnly(libs.hiddenapi.refine.annotation)
    annotationProcessor(libs.hiddenapi.refine.annotation.processor)
}
