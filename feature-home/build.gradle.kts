plugins {
    id("vpnsec.android.library")
    id("vpnsec.android.library.compose")
    kotlin("plugin.serialization") version "1.7.21"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":core-navigation"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    api("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.compose.runtime:runtime:1.3.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.3.2")
}

android.namespace = "com.stslex.feature_home"