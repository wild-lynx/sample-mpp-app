rootProject.name = "sample-mpp-app"

gradleWrapper()

pluginManagement {
    plugins {
        Kotlin(
            kotlin("multiplatform"),
            kotlin("android.extensions"),
            kotlin("plugin.serialization"),
            kotlin("native.cocoapods")
        )
        Android.Plugin(
            id("com.android.application"),
            id("com.android.library")
        )
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application",
                "com.android.library"
                -> useModule("com.android.tools.build:gradle:${target.version ?: requested.version}")
            }
        }
    }
}

defaultRepositories {
    // // change the variable value to download a specific build from TeamCity
    // // (currently broken: https://youtrack.jetbrains.com/issue/KT-34246)
    // val customBuildId = 2478816
    // maven("https://teamcity.jetbrains.com/guestAuth/app/rest/builds/id:$customBuildId/artifacts/content/maven")

    kotlinDev()
    kotlinEap()
    kotlinx()
    google()
    mavenCentral()
    jcenter()
}

enableFeaturePreview("GRADLE_METADATA")
