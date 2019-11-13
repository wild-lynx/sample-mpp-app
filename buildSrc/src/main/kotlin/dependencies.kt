fun kotlinx(module: String, version: String? = null): Any =
    "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" } ?: ""}"

fun ktor(module: String, version: String? = null): Any =
    "io.ktor:ktor-$module${version?.let { ":$it" } ?: ""}"

fun androidx(groupAndModule: String, version: String? = null): Any =
    "androidx.$groupAndModule${version?.let { ":$it" } ?: ""}"
