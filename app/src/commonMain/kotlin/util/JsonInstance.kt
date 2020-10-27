package util

import kotlinx.serialization.json.Json

val nonstrict: Json = Json {
    isLenient = true
    ignoreUnknownKeys = true
    allowSpecialFloatingPointValues = true
    useArrayPolymorphism = true
}
