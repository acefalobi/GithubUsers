package plugins

plugins {
    id("kotlin")
    kotlin("kapt")
}

dependencies {
    implementation(RootDependencies.kotlin)

    implementation(AsyncDependencies.coroutines)

    implementation(UtilityDependencies.timber)

    testImplementation(TestingDependencies.assertJ)
    testImplementation(TestingDependencies.jUnit)
}
