@file:Suppress("PackageDirectoryMismatch" /* to avoid import "noise" */)

abstract class Version(val version: String) {
    final override fun toString() = version
}
