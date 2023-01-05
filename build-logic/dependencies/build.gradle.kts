plugins {
    `kotlin-dsl`
}

group = "com.stslex.vpnsec.buildlogic"

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "vpnsec.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "vpnsec.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
    }
}