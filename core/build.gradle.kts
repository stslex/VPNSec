plugins {
    id("vpnsec.android.library")
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.bundles.koin)
    api(libs.bundles.test)
}

android.namespace = "com.stslex.core"