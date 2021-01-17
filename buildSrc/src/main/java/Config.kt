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
    const val kotlin = "1.4.21"

    //Plugins
    const val versionsPlugin = "0.28.0"
    const val androidToolsPlugin = "4.1.0"
    const val ktlintPlugin = "9.4.1"
    const val serialization = "1.4.20"

    // Android libraries
    const val compatLibrary = "1.2.0"
    const val materialLibrary = "1.2.1"
    const val constraintLayout = "2.0.4"
    const val serializationJson = "1.0.1"
    const val coroutines = "1.4.2"
    const val lifecycle = "2.2.0"

    // third party libs
    const val adapterDelegates = "4.3.0"
    const val glide = "4.11.0"
    const val retrofit = "2.9.0"
    const val serializationConverter = "0.8.0"
    const val okhttp = "4.9.0"
    const val timber = "4.7.1"

    // Libs for testing
    const val espresso = "3.3.0"
    const val junit = "5.7.0"
    const val mockito = "3.6.0"
    const val mockitoKotlin = "2.2.0"
}

object Plugins {
    const val kotlin = "gradle-plugin"
    const val parcelize = "org.jetbrains.kotlin.plugin.parcelize"
    const val versions = "com.github.ben-manes.versions"
    const val androidTools = "com.android.tools.build:gradle:${Versions.androidToolsPlugin}"
    const val androidApp = "com.android.application"
    const val kotlinAndroidApp = "kotlin-android"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val kapt = "kapt"
    const val serializationPath = "org.jetbrains.kotlin:kotlin-serialization:${Versions.serialization}"
    const val serialization = "org.jetbrains.kotlin.plugin.serialization"
}

object Libs {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.compatLibrary}"
    const val material = "com.google.android.material:material:${Versions.materialLibrary}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val serializationJson =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serializationJson}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"

    const val kotlinStdlib = "stdlib-jdk7"

    const val adapterDelegates = "com.hannesdorfmann:adapterdelegates4:${Versions.adapterDelegates}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glideOkhttp = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
    const val glideRecyclerView =
        "com.github.bumptech.glide:recyclerview-integration:${Versions.glide}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val serializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.serializationConverter}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    // Test libraries
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val junit = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
    const val junitParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit}"
    const val mockito = "org.mockito:mockito-inline:${Versions.mockito}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
}