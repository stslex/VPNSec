plugins {
    id("vpnsec.android.application")
    id("vpnsec.android.application.compose")
    id("org.jetbrains.kotlin.android")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.stslex.vpnsec"

    defaultConfig {
        applicationId = "com.stslex.vpnsec"
        versionCode = 1
        versionName = "1.0"
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":core"))
    val composeBom = platform(libs.androidx.compose.bom)
    api(composeBom)
    testApi(composeBom)
    api(libs.bundles.compose)
    api(libs.bundles.accompanist)
    api(libs.androidx.constraintlayout.compose)
    testApi(libs.bundles.test.ui)
}