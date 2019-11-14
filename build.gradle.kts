import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.application")
    kotlin("multiplatform")
    kotlin("android.extensions")
    kotlin("plugin.serialization")
    /*
    * The plugin:
    * — Adds both debug and release frameworks as output binaries for all iOS and macOS targets.
    * - Creates a `podspec` Gradle task which generates a podspec file for the given project.
    * */
    kotlin("native.cocoapods")
}

val javaVersion = JavaVersion.VERSION_1_8
val androidSourceSetsRoot = File("src/android")

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "org.jetbrains.kotlin.mpp_app_android"
        minSdkVersion(24) // required to be able to use static interfaces in okhttp
        targetSdkVersion(29)
        versionCode = 1
        versionName = AppVersion.version
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    packagingOptions {
        // workaround for https://github.com/Kotlin/kotlinx.coroutines/issues/1064 and related problems
        exclude("META-INF/**.kotlin_module")
    }
    // Enable desugaring to allow usage of `dexing-min-sdk=24` and, therefore, transform
    // artifact 'okhttp.jar (com.squareup.okhttp3:okhttp:3.14.2)' to match attributes
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    sourceSets.all {
        // nest each Android source set inside `androidSourceSetsRoot`
        val oldSourceSetRoot = name
        val newSourceSetRoot = File(androidSourceSetsRoot, oldSourceSetRoot)
        setRoot(newSourceSetRoot.path)

        // allow source files in `kotlin` folders
        java.srcDir(File(newSourceSetRoot, "kotlin"))
    }
}

dependencies {
    commonMainImplementation(enforcedPlatform(kotlin("bom")))
    commonMainImplementation(enforcedPlatform(ktor("bom", Ktor.version)))
    commonMainImplementation(enforcedPlatform(kotlinx("coroutines-bom", Coroutines.version)))
}

version = AppVersion.version

kotlin {
    val android = android("androidApp") {
        compilations.all {
            kotlinOptions.jvmTarget = javaVersion.toString()
        }
    }

    // Create and configure iOS simulator target.
    /*
    * We may need several binaries to use it with Xcode:
    * - iOS arm64 debug --- the binary to run the iOS device in debug mode
    * - iOS arm64 release --- the binary to include into a release version of an app
    * - iOS x64 debug --- the binary for iOS simulator, which uses the desktop mac CPU
    *
    * Note that `native.cocoapods` enables by default generation of debug and release
    * frameworks as output binaries for all iOS and macOS targets.
    * See more here: https://kotlinlang.org/docs/reference/native/cocoapods.html
    * */
    val iosSim = iosX64("iosSim") { }

    // NB: Kotlin/Native supports ObjC pods only!
    /*
    * Here we configure podfile for the project.
    * With cocoapods (enabled by `native.cocoapods` plugin),
    * we don't need to generate Xcode framework anymore;
    * the K/N part, instead, is imported into Xcode via
    * cocoapods mechanism.
    * A short and clear intro is here: https://medium.com/@yuyaHorita/setup-kotlin-native-project-with-cocoapods-7036dd72366f
    * */
    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "A Kotlin/Native module for iOS sample app"
        homepage = "https://github.com/wild-lynx/sample-mpp-app"
    }

    sourceSets {
        /** Returns the multiplatform [KotlinSourceSet] of the provided Android-specific [srcSet] name. */
        operator fun KotlinAndroidTarget.invoke(srcSet: String, setup: KotlinSourceSet.() -> Unit) =
            named(name + srcSet.capitalize(), setup)

        /** Returns the multiplatform [KotlinSourceSet] of the provided Native [srcSet] name. */
        operator fun KotlinNativeTarget.invoke(srcSet: String, setup: KotlinSourceSet.() -> Unit) =
            named(name + srcSet.capitalize(), setup)

        all {
            listOf(
                "kotlin.Experimental"
            ).forEach(languageSettings::useExperimentalAnnotation)
        }
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(kotlinx("coroutines-core-common"))
                implementation(kotlinx("serialization-runtime-common"))
                implementation(ktor("client-serialization"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        android("main") {
            dependencies {
                implementation(kotlin("stdlib-jdk" + javaVersion.majorVersion))
                implementation(kotlinx("coroutines-android"))
                implementation(kotlinx("serialization-runtime"))
                implementation(ktor("client-serialization-jvm"))
                implementation(ktor("client-okhttp"))
                implementation(androidx("appcompat:appcompat", Android.AppCompat.version))
                implementation(androidx("recyclerview:recyclerview", Android.RecyclerView.version))
                implementation(
                    androidx(
                        "constraintlayout:constraintlayout",
                        Android.ConstraintLayout.version
                    )
                )
                implementation("com.jaredrummler:android-device-names:" + AndroidDeviceNames.version)
            }
        }
        android("test") {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("junit:junit:" + JUnit.version)
            }
        }
        android("androidTest") {
            dependencies {
                implementation(androidx("test:runner", Android.Test.Runner.version))
                implementation(androidx("test.ext:junit-ktx", Android.Test.JUnit.Ktx.version))
                implementation(
                    androidx(
                        "test.espresso:espresso-core",
                        Android.Test.Espresso.Core.version
                    )
                )
            }
        }
        iosSim("main") {
            kotlin.setSrcDirs(listOf("src/ios/main"))
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlinx("coroutines-core-native", Coroutines.version))
                implementation(kotlinx("serialization-runtime-native", Serialization.version))
                implementation(ktor("client-serialization-native", Ktor.version))
                implementation(ktor("client-ios", Ktor.version))
            }
        }
        iosSim("test") {
            kotlin.setSrcDirs(listOf("src/ios/test"))
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}