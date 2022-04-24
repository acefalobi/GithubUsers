package plugins

import AsyncDependencies
import BuildConfiguration
import DependencyInjectionDependencies
import ProjectDependencies
import RootDependencies
import TestingDependencies
import UtilityDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = BuildConfiguration.compileSdkVersion

    @Suppress("UnstableApiUsage")
    defaultConfig {
        minSdk = BuildConfiguration.minSdkVersion
        targetSdk = BuildConfiguration.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions.sourceCompatibility = JavaVersion.VERSION_11
    compileOptions.targetCompatibility = JavaVersion.VERSION_11

    lint {
        checkAllWarnings = true
        warningsAsErrors = true
    }

    testOptions.unitTests.isIncludeAndroidResources = true
    testOptions.unitTests.isReturnDefaultValues = true

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

dependencies {
    implementation(RootDependencies.kotlin)
    implementation(project(ProjectDependencies.core))

    implementation(AsyncDependencies.coroutines)
    implementation(AsyncDependencies.coroutinesAndroid)

    implementation(UtilityDependencies.timber)

    implementation(DependencyInjectionDependencies.hilt)
    kapt(DependencyInjectionDependencies.hiltCompiler)

    testImplementation(AsyncDependencies.coroutinesTest)

    testImplementation(TestingDependencies.androidTest)
    testImplementation(TestingDependencies.assertJ)
    testImplementation(TestingDependencies.jUnit)
    testImplementation(TestingDependencies.mockk)
    testImplementation(TestingDependencies.robolectric)

    androidTestImplementation(TestingDependencies.androidTestRunner)
}

kapt {
    correctErrorTypes = true
}
