private object Versions {
  //region Android"
  const val gradlePlugin = "3.1.4"
  const val buildTools = "27.0.3"
  const val minSdk = 19
  const val targetSdk = 27
  const val compileSdk = 27
  //endregion"

  //region Libraries
  const val kotlin = "1.2.60"
  const val supportLibrary = "27.1.1"
  const val constraintLayout = "1.1.2"
  const val retrofit = "2.4.0"
  const val okhttp = "3.10.0"
  const val moshi = "1.6.0"
  const val glide = "4.7.1"
  const val dagger = "2.16"
  const val javaxInject = "1"
  const val rxJava = "2.1.14"
  const val rxAndroid = "2.0.2"
  const val androidArchComponents = "1.1.1"
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
  const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
  const val javaxInject = "javax.inject:javax.inject:${Versions.javaxInject}"
  const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
  //endregion

  //region Android
  const val appcompatV7 = "com.android.support:appcompat-v7:${Versions.supportLibrary}"
  const val recyclerView = "com.android.support:recyclerview-v7:${Versions.supportLibrary}"
  const val cardView = "com.android.support:cardview-v7:${Versions.supportLibrary}"
  const val supportDesign = "com.android.support:design:${Versions.supportLibrary}"
  const val supportConstraint =
    "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"
  const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
  const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
  const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
  const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
  const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
  const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
  const val daggerAndroidCompiler = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
  const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
  const val room = "android.arch.persistence.room:runtime:${Versions.androidArchComponents}"
  const val roomCompiler = "android.arch.persistence.room:compiler:${Versions.androidArchComponents}"
  const val roomRxJava = "android.arch.persistence.room:rxjava2:${Versions.androidArchComponents}"
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
  const val room = Libs.room
  const val roomCompiler = Libs.roomCompiler
  const val roomRxJava = Libs.roomRxJava
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
  const val appcompatV7 = Libs.appcompatV7
  const val recyclerView = Libs.recyclerView
  const val cardView = Libs.cardView
  const val supportDesign = Libs.supportDesign
  const val supportConstraint = Libs.supportConstraint
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
