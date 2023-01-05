plugins {
    id("vpnsec.android.application")
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
    libs.apply {
        implementation(androidx.core.ktx)
        implementation(android.material)
        implementation(bundles.test)
    }
}