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

/*
* will be used in the feature
*/
dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    testImplementation(composeBom)
    implementation(libs.bundles.compose)
    testImplementation(libs.bundles.test.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.test)
}