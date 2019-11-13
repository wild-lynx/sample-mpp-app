import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.bintray(user: String, repository: String) = maven("https://$user.bintray.com/$repository")

fun RepositoryHandler.bintrayKotlin(repository: String) = bintray(user = "kotlin", repository = repository)
fun RepositoryHandler.kotlinDev() = bintrayKotlin("kotlin-dev")
fun RepositoryHandler.kotlinEap() = bintrayKotlin("kotlin-eap")
fun RepositoryHandler.kotlinx() = bintrayKotlin("kotlinx")
