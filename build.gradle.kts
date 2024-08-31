// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.agp.app) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}

val projectMinSdkVersion by extra(29)
val projectTargetSdkVersion by extra(35)
val projectCompileSdkVersion by extra(35)

val projectSourceCompatibility by extra(JavaVersion.VERSION_17)
val projectTargetCompatibility by extra(JavaVersion.VERSION_17)
val projectKotlinJvmTarget by extra("17")
