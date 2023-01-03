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
    implementation(project(":core-ui"))
    implementation(project(":core-navigation"))
    implementation(project(":feature-home"))
}