plugins {
    id("vpnsec.android.library")
    id("vpnsec.android.library.compose")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":core-navigation"))
}

android.namespace = "com.stslex.feature_home"