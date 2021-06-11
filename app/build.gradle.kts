plugins {
    id(Plugins.androidApp)
    id(Plugins.kotlinAndroidApp)
    id(Plugins.parcelize)
    kotlin(Plugins.kapt)
}

android {
    buildToolsVersion = Config.androidBuildTools
    compileSdkVersion(Config.androidCompileSdk)

    defaultConfig {
        applicationId = Config.applicationId
        minSdkVersion(Config.androidMinSdk)
        targetSdkVersion(Config.androidTargetSdk)
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs["debug"]
            isDebuggable = true
            buildConfigField("String", "BASE_URL", """"https://api.themoviedb.org/3/"""")
            buildConfigField("String", "API_KEY", """"7d4faff53c09dde00a141ae9c56f2d1b"""")
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Libs.appcompat)
    implementation(Libs.material)
    implementation(Libs.constraintLayout)
    implementation(Libs.coroutines)
    implementation(Libs.lifecycle)
    implementation(Libs.viewModel)
    implementation(Libs.room)
    implementation(Libs.roomKtx) // needed by coroutines
    implementation(Libs.fragmentKtx)
    kapt(Libs.roomCompiler)

    implementation(Libs.adapterDelegates)
    implementation(Libs.glide)
    kapt(Libs.glideCompiler)
    kapt(Libs.moshiCodeGen) // migrate to kotlin serialization - better for multiplatform
    implementation(Libs.glideOkhttp)
    implementation(Libs.glideRecyclerView)
    implementation(Libs.retrofit)
    implementation(Libs.okhttp)
    implementation(Libs.okhttpLogging)
    implementation(Libs.timber)
    implementation(Libs.workManager)
    implementation(Libs.moshiAdapters)
    implementation(Libs.retrofitConverter)
    implementation(Libs.viewBindingProperty)
    implementation(Libs.dagger)
    kapt(Libs.daggerProcessor)

    // testing
    androidTestImplementation(Libs.kaspresso)
}