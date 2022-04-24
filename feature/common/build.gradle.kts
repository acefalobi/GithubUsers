plugins {
    id("plugins.android-library")
}

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AndroidXDependencies.Versions.compose
    }
}

dependencies {
    implementation(AndroidXDependencies.composeUI)
    implementation(AndroidXDependencies.composeMaterial)
    implementation(AndroidXDependencies.composeMaterial3)
    implementation(AndroidXDependencies.composeMaterialIcons)

    implementation(DependencyInjectionDependencies.hiltComposeNavigation)

    implementation(UtilityDependencies.accompanistInsets)
    implementation(UtilityDependencies.accompanistInsetsUi)
    implementation(UtilityDependencies.accompanistPlaceholder)
    implementation(UtilityDependencies.accompanistSystemUIController)
}
