import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL

object GradleWrapper : Version("5.6.4") {
    val distribution = ALL
}

object Kotlin : Version("1.3.50")

object Coroutines : Version("1.3.2")

object Ktor : Version("1.2.5")

object JUnit : Version("4.12")

object Serialization : Version("0.13.0")

object Android {
    object Plugin : Version("3.5.2")

    object AppCompat : Version("1.1.0")

    object RecyclerView : Version("1.0.0")

    object ConstraintLayout : Version("1.1.3")

    object Test {
        object Runner : Version("1.2.0")

        object JUnit : Version("1.1.1") {
            object Ktx : Version(version)
        }

        object Espresso : Version("3.2.0") {
            object Core : Version(version)
        }
    }
}

object AndroidDeviceNames : Version("1.1.9") // GitHub: https://git.io/JeVpP
