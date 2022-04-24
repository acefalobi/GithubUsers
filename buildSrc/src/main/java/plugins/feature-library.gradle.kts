package plugins

import AndroidXDependencies
import DependencyInjectionDependencies
import ProjectDependencies
import UtilityDependencies
import ViewDependencies

plugins {
    id("plugins.android-library")
//    id("androidx.navigation.safeargs.kotlin")
}

android {
    defaultConfig {
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AndroidXDependencies.Versions.compose
    }
}

dependencies {
    implementation(project(ProjectDependencies.common))

    implementation(DependencyInjectionDependencies.hiltComposeNavigation)

    implementation(AndroidXDependencies.composeUI)
    implementation(AndroidXDependencies.composeMaterial)
    implementation(AndroidXDependencies.composeMaterial3)
    implementation(AndroidXDependencies.composeMaterialIcons)
    implementation(AndroidXDependencies.composePreview)
    implementation(AndroidXDependencies.composeTooling)
    implementation(AndroidXDependencies.constraintLayoutCompose)
    implementation(AndroidXDependencies.lifecycleRuntime)
    implementation(AndroidXDependencies.lifecycleViewModel)
    debugImplementation(AndroidXDependencies.customView)
    debugImplementation(AndroidXDependencies.customViewPoolingContainer)

    implementation(ViewDependencies.coil)

    implementation(UtilityDependencies.accompanistInsets)
    implementation(UtilityDependencies.accompanistInsetsUi)
    implementation(UtilityDependencies.accompanistPlaceholder)
    implementation(UtilityDependencies.accompanistSystemUIController)
}
