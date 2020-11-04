object Config {
    // Android config
    const val androidBuildTools = "30.0.0"
    const val androidMinSdk = 21
    const val androidTargetSdk = 30
    const val androidCompileSdk = 30
    const val applicationId = "com.github.mik629.android.fundamentals"
    const val versionCode = 1
    const val versionName = "1.0"
}

object Versions {
    const val kotlin = "1.4.10"

    //Plugins
    const val versionsPlugin = "0.28.0"
    const val androidToolsPlugin = "4.1.0"
    const val ktlintPlugin = "9.4.1"

    // Android libraries
    const val compatLibrary = "1.2.0"
    const val materialLibrary = "1.2.1"
    const val constraintLayout = "2.0.4"

    // Libs for testing
    const val espresso = "3.3.0"
    const val junit = "5.7.0"
    const val mockito = "3.6.0"
    const val mockitoKotlin = "2.2.0"
}

object Plugins {
    const val kotlin = "gradle-plugin"
    const val versions = "com.github.ben-manes.versions"
    const val androidTools = "com.android.tools.build:gradle:${Versions.androidToolsPlugin}"
    const val androidApp = "com.android.application"
    const val kotlinAndroidApp = "kotlin-android"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
}

object Libs {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.compatLibrary}"
    const val material = "com.google.android.material:material:${Versions.materialLibrary}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val kotlinStdlib = "stdlib-jdk7"

    // Test libraries
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val junit = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
    const val junitParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit}"
    const val mockito = "org.mockito:mockito-inline:${Versions.mockito}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
}