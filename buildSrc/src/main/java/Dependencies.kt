@file:Suppress("KDocMissingDocumentation")

object PluginDependencies {
    const val HILT = "dagger.hilt.android.plugin"
    const val NAVIGATION = "androidx.navigation.safeargs.kotlin"
    const val SPOTLESS = "plugins.spotless"
}

object ProjectDependencies {
    const val app = ":app"

    const val core = ":core"

    const val remote = ":remote"

    const val common = ":feature:common"
    const val featureUser = ":feature:user"
}

object RootDependencies {

    object Versions {
        const val kotlin = "1.6.10"
    }

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
}

object NetworkDependencies {

    object Versions {
        const val okhttp = "4.8.0"
        const val retrofit = "2.9.0"
    }

    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitMock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"

}

object AndroidXDependencies {

    object Versions {
        const val compose = "1.1.1"
        const val composeMaterial3 = "1.0.0-alpha10"
        const val constraintLayoutCompose = "1.0.0-beta02"
        const val customView = "1.2.0-alpha01"
        const val customViewPoolingContainer = "1.0.0-alpha01"
        const val lifecycle = "2.4.1"
        const val navigation = "2.3.0-alpha05"
    }

    const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeMaterial3 = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
    const val composeMaterialIcons =
        "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val constraintLayoutCompose =
        "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayoutCompose}"
    const val customView = "androidx.customview:customview:${Versions.customView}"
    const val customViewPoolingContainer =
        "androidx.customview:customview-poolingcontainer:${Versions.customViewPoolingContainer}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
}

object ViewDependencies {

    object Versions {
        const val coil = "2.0.0-rc03"
    }

    const val coil = "io.coil-kt:coil-compose:${Versions.coil}"
}

object AsyncDependencies {

    object Versions {
        const val coroutines = "1.5.1"
    }

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

}

object DependencyInjectionDependencies {

    object Versions {
        const val hilt = "2.41"
        const val hiltComposeNavigation = "1.0.0-alpha03"
    }

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltComposeNavigation =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltComposeNavigation}"
}

object UtilityDependencies {

    object Versions {
        const val accompanist = "0.23.1"
        const val moshi = "1.12.0"
        const val timber = "4.7.1"
    }

    const val accompanistInsets =
        "com.google.accompanist:accompanist-insets:${Versions.accompanist}"
    const val accompanistInsetsUi =
        "com.google.accompanist:accompanist-insets-ui:${Versions.accompanist}"
    const val accompanistPlaceholder =
        "com.google.accompanist:accompanist-placeholder-material:${Versions.accompanist}"
    const val accompanistSystemUIController =
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object TestingDependencies {

    object Versions {
        const val androidTest = "1.4.0"
        const val androidTestRunner = "1.4.0"
        const val assertJ = "3.16.1"
        const val jUnit = "4.13.2"
        const val mockk = "1.9.3"
        const val robolectric = "4.7.3"
    }

    const val androidTest = "androidx.test:core:${Versions.androidTest}"
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    const val assertJ = "org.assertj:assertj-core:${Versions.assertJ}"
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
}

object ClasspathDependencies {

    object Versions {
        const val gradle = "7.1.3"
        const val hilt = "2.41"
        const val navigation = "2.3.5"
        const val spotless = "5.14.0"
    }

    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinGradle =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${RootDependencies.Versions.kotlin}"
    const val hilt =
        "com.google.dagger:hilt-android-gradle-plugin:${DependencyInjectionDependencies.Versions.hilt}"
    const val navigation =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:${Versions.spotless}"
}