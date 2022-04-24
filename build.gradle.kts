import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
    }
    repositories.addCoreRepos()

    dependencies {
        classpath(ClasspathDependencies.kotlinGradle)
        classpath(ClasspathDependencies.gradle)
        classpath(ClasspathDependencies.hilt)
        classpath(ClasspathDependencies.navigation)
        classpath(ClasspathDependencies.spotless)
    }
}

allprojects {
    plugins.apply(PluginDependencies.SPOTLESS)

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"

        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.ExperimentalStdlibApi"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi"
    }
}
