plugins {
    alias(libs.plugins.agp.app)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hiddenapi.refine.plugin)
}

val projectMinSdkVersion: Int by rootProject.extra
val projectTargetSdkVersion: Int by rootProject.extra
val projectCompileSdkVersion: Int by rootProject.extra

val projectSourceCompatibility: JavaVersion by rootProject.extra
val projectTargetCompatibility: JavaVersion by rootProject.extra
val projectKotlinJvmTarget: String by rootProject.extra

android {
    namespace = "xyz.mufanc.boox.enhance"
    compileSdk = projectCompileSdkVersion

    defaultConfig {
        applicationId = "xyz.mufanc.boox.enhance"
        minSdk = projectMinSdkVersion
        targetSdk = projectTargetSdkVersion
        versionCode = 1
        versionName = "1.0"
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

    kotlinOptions {
        jvmTarget = projectKotlinJvmTarget
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    compileOnly(project(":api-stub"))
    compileOnly(libs.xposed)
    implementation(libs.ezxhelper)
    implementation(libs.joor)
}
