@file:Suppress("UnstableApiUsage")

include(":feature:common")

include(":feature:user")

include(":remote")

include(":core")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Github Users"
include(":app")
