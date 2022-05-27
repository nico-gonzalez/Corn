private object Versions {
    //region Android
    const val gradlePlugin = "7.2.1"
    const val minSdk = 21
    const val targetSdk = 30
    const val compileSdk = 32
    const val compose = "1.2.0-beta01"
    //endregion

    //region Libraries
    const val kotlin = "1.6.21"
    const val material = "1.2.0"
    const val appCompat = "1.2.0"
    const val recyclerview = "1.1.0"
    const val cardview = "1.0.0"
    const val constraintLayout = "2.0.0"
    const val retrofit = "2.9.0"
    const val okhttp = "4.8.1"
    const val moshi = "1.9.3"
    const val glide = "4.11.0"
    const val dagger = "2.42"
    const val javaxInject = "1"
    const val rxJava = "2.2.19"
    const val rxAndroid = "2.1.1"
    const val rxKotlin = "2.4.0"
    //endregion

    //region Test
    const val junit = "4.12"
    const val espresso = "3.1.0"
    const val testRunner = "1.1.0"
    const val testRules = "1.1.0"
    const val hamcrest = "1.3"
    const val mockito = "2.12.0"
    const val mockitoKotlin = "1.5.0"
    //endregion

    //region Application
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionBuild = 0

    const val versionCode =
            versionMajor * 10000 + versionMinor * 1000 + versionPatch * 100 + versionBuild
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"
    //endregion
}

object Libs {

    //region Libraries
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val okhttpLoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val javaxInject = "javax.inject:javax.inject:${Versions.javaxInject}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    //endregion

    //region Android
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerAndroidCompiler =
            "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeMaterialIcons = "androidx.compose.material:material-icons-core:${Versions.compose}"
    const val composeMaterialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val composeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val composeRxJava = "androidx.compose.runtime:runtime-rxjava2:${Versions.compose}"
    const val composeUiTest = "androidx.ui:ui-test:${Versions.compose}"
    //endregion

    //region Testing
    const val junit = "junit:junit:${Versions.junit}"
    const val kotlinJUnit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoKotlin = "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
    const val androidRunner = "androidx.test:runner:${Versions.testRunner}"
    const val androidRules = "androidx.test:rules:${Versions.testRules}"
    const val hamcrest = "org.hamcrest:hamcrest-all:${Versions.hamcrest}"
    //endregion
}

object Plugins {
    const val androidPlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}
