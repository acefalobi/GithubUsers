import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("plugins.android-library")
}

android {
    defaultConfig {
        buildConfigField(
            "String",
            "GITHUB_USERNAME",
            getOptionalEnvProperty(gradleLocalProperties(rootDir), "GITHUB_USERNAME")
        )
        buildConfigField(
            "String",
            "GITHUB_PASSWORD",
            getOptionalEnvProperty(gradleLocalProperties(rootDir), "GITHUB_PASSWORD")
        )
    }
}

dependencies {
    implementation(NetworkDependencies.retrofit)
    implementation(NetworkDependencies.moshiConverter)
    implementation(NetworkDependencies.loggingInterceptor)
    testImplementation(NetworkDependencies.mockWebServer)
    testImplementation(NetworkDependencies.retrofitMock)

    implementation(UtilityDependencies.moshiKotlin)
}
