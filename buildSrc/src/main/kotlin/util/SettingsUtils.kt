@file:Suppress("PackageDirectoryMismatch" /* to avoid import "noise" */)

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.initialization.Settings
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.kotlin.dsl.named
import org.gradle.plugin.use.PluginDependencySpec

operator fun Version.invoke(vararg plugins: PluginDependencySpec) =
    plugins.forEach { it.version(this.version) }

fun Settings.gradleWrapper(
    version: String = GradleWrapper.version,
    distribution: Wrapper.DistributionType = GradleWrapper.distribution
) {
    gradle.rootProject {
        afterEvaluate {
            tasks.named<Wrapper>("wrapper") {
                gradleVersion = version
                distributionType = distribution
            }
        }
    }
}

fun Settings.defaultRepositories(repositories: RepositoryHandler.() -> Unit) {
    pluginManagement {
        this.repositories.repositories()
        this.repositories.gradlePluginPortal()
    }
    gradle.allprojects {
        this.buildscript.repositories.repositories()
        this.repositories.repositories()
    }
}
