plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                compileOnly(libs.javax.inject)
                implementation(libs.rxjava)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.kotlin.junit)
                implementation(libs.mockk)
            }
        }
    }
}