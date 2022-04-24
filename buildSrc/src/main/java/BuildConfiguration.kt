@file:Suppress("KDocMissingDocumentation")

import org.gradle.api.artifacts.dsl.RepositoryHandler
import java.util.Properties

object BuildConfiguration {
    const val targetSdkVersion = 32
    const val compileSdkVersion = 32
    const val minSdkVersion = 21

    const val versionCode = 1
    const val versionName = "1.0.0"

    const val applicationId = "com.payconiq.android.github"
}

fun getEnvProperty(properties: Properties, name: String): String =
    properties.getProperty(name)
    ?: error("Unable to get $name from Environment Variables or local.properties")

fun getOptionalEnvProperty(
    properties: Properties,
    name: String,
    defaultValue: String = "\"\""
): String = try {
    getEnvProperty(properties, name)
} catch (e: Throwable) {
    defaultValue
}

fun RepositoryHandler.addCoreRepos() {
    google()
    mavenCentral()
}