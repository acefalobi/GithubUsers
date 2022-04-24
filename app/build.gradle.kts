plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id(PluginDependencies.HILT)
    id(PluginDependencies.NAVIGATION)
}

android {
    compileSdk = BuildConfiguration.compileSdkVersion

    defaultConfig {
        applicationId = BuildConfiguration.applicationId

        minSdk = BuildConfiguration.minSdkVersion
        targetSdk = BuildConfiguration.targetSdkVersion

        versionCode = BuildConfiguration.versionCode
        versionName = BuildConfiguration.versionName

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        setProperty("archivesBaseName", "$applicationId-app-$versionName")
    }

    buildFeatures {
        compose = true
    }

    signingConfigs {
        create("release") {
            storeFile = file("../app_signing_key.jks")
            storePassword = "app_password"
            keyAlias = "app"
            keyPassword = "app_password"
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true

            resValue("string", "app_name", "Github Users Debug")
        }
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")

            resValue("string", "app_name", "Github Users")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AndroidXDependencies.Versions.compose
    }

    lint {
        checkAllWarnings = true
        warningsAsErrors = true
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(RootDependencies.kotlin)

    implementation(project(ProjectDependencies.core))

    implementation(project(ProjectDependencies.common))
    implementation(project(ProjectDependencies.remote))

    implementation(project(ProjectDependencies.featureUser))

    implementation(DependencyInjectionDependencies.hilt)
    implementation(DependencyInjectionDependencies.hiltComposeNavigation)
    kapt(DependencyInjectionDependencies.hiltCompiler)

    implementation(UtilityDependencies.timber)
}
