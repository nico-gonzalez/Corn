private object Versions {
    //region Android"
    const val gradlePlugin = "4.1.0-rc01"
    const val buildTools = "29.0.2"
    const val minSdk = 21
    const val targetSdk = 30
    const val compileSdk = 30
    //endregion"

    //region Libraries
    const val kotlin = "1.4.0"
    const val material = "1.2.0"
    const val appCompat = "1.2.0"
    const val recyclerview = "1.1.0"
    const val cardview = "1.0.0"
    const val constraintLayout = "2.0.0"
    const val retrofit = "2.4.0"
    const val okhttp = "3.10.0"
    const val moshi = "1.6.0"
    const val glide = "4.7.1"
    const val dagger = "2.28.3"
    const val javaxInject = "1"
    const val rxJava = "2.1.14"
    const val rxAndroid = "2.0.2"
    //endregion

    //region Test
    const val junit = "4.12"
    const val espresso = "3.0.1"
    const val testRunner = "1.0.1"
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

private object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

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
    //endregion

    //region Testing
    const val junit = "junit:junit:${Versions.junit}"
    const val kotlinJUnit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoKotlin = "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"
    const val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib =
        "com.android.support.test.espresso:espresso-contrib:${Versions.espresso}"
    const val espressoIntents =
        "com.android.support.test.espresso:espresso-intents:${Versions.espresso}"
    const val androidRunner = "com.android.support.test:runner:${Versions.testRunner}"
    const val androidRules = "com.android.support.test:rules:${Versions.testRunner}"
    const val hamcrest = "org.hamcrest:hamcrest-all:${Versions.hamcrest}"
    //endregion
}

object Plugins {
    const val androidPlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Data {
    const val kotlin = Libs.kotlin
    const val moshi = Libs.moshi
    const val moshiKotlin = Libs.moshiKotlin
    const val retrofit = Libs.retrofit
    const val retrofitMoshi = Libs.retrofitMoshi
    const val retrofitRxJava = Libs.retrofitRxJava
    const val okhttpLoggingInterceptor = Libs.okhttpLoggingInterceptor
    const val javaxInject = Libs.javaxInject
    const val rxJava = Libs.rxJava
}

object DataTest {
    const val junit = Libs.junit
    const val kotlinJUnit = Libs.kotlinJUnit
    const val mockito = Libs.mockito
    const val mockitoKotlin = Libs.mockitoKotlin
}

object Domain {
    const val kotlin = Libs.kotlin
    const val rxJava = Libs.rxJava
    const val javaxInject = Libs.javaxInject
}

object DomainTest {
    const val junit = Libs.junit
    const val kotlinJUnit = Libs.kotlinJUnit
    const val mockito = Libs.mockito
    const val mockitoKotlin = Libs.mockitoKotlin
}

object Presentation {
    const val kotlin = Libs.kotlin
    const val rxJava = Libs.rxJava
    const val javaxInject = Libs.javaxInject
}

object PresentationTest {
    const val junit = Libs.junit
    const val kotlinJUnit = Libs.kotlinJUnit
    const val mockito = Libs.mockito
    const val mockitoKotlin = Libs.mockitoKotlin
}

object App {
    const val kotlin = Libs.kotlin
    const val appcompatV7 = Libs.appcompat
    const val recyclerView = Libs.recyclerview
    const val cardView = Libs.cardview
    const val supportDesign = Libs.material
    const val supportConstraint = Libs.constraintlayout
    const val glide = Libs.glide
    const val glideCompiler = Libs.glideCompiler
    const val dagger = Libs.dagger
    const val daggerCompiler = Libs.daggerCompiler
    const val daggerAndroid = Libs.daggerAndroid
    const val daggerAndroidSupport = Libs.daggerAndroidSupport
    const val daggerAndroidCompiler = Libs.daggerAndroidCompiler
    const val javaxInject = Libs.javaxInject
    const val rxAndroid = Libs.rxAndroid
}

object AppTest {
    const val junit = Libs.junit
    const val kotlinJUnit = Libs.kotlinJUnit
    const val mockitoAndroid = Libs.mockitoAndroid
    const val mockitoKotlin = Libs.mockitoKotlin
    const val espressoCore = Libs.espressoCore
    const val espressoContrib = Libs.espressoContrib
    const val espressoIntents = Libs.espressoIntents
    const val androidRunner = Libs.androidRunner
    const val androidRules = Libs.androidRules
    const val hamcrest = Libs.hamcrest
    const val daggerCompiler = Libs.daggerCompiler
    const val daggerAndroidCompiler = Libs.daggerAndroidCompiler
}
