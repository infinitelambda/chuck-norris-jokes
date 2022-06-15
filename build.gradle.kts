plugins {
    kotlin("multiplatform") version Version.kotlinVersion apply false
    kotlin("native.cocoapods") version Version.kotlinVersion apply false
    kotlin("plugin.serialization") version Version.kotlinVersion apply false
    kotlin("android") version Version.kotlinVersion apply false

    id("com.android.library") version Version.Plugin.android apply false
    id("com.android.application") version Version.Plugin.android apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}