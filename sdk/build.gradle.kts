plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.library")
}

version = "1.0"

kotlin {
    android()
    ios()
    js(IR) {
        browser()
        binaries.executable()
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "sdk"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependency.Ktor.clientCore)
                implementation(Dependency.Ktor.contentNegotiation)
                implementation(Dependency.Ktor.clientSerialization)
                implementation(Dependency.Ktor.logging)

                implementation(Dependency.Kotlinx.coroutinesCore)

                implementation(Dependency.Kotlinx.serialization)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))

                implementation(Dependency.Ktor.clientMock)

                implementation(Dependency.Kotlinx.coroutinesTest)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependency.Ktor.clientAndroid)
            }
        }
        val androidTest by getting {

        }

        val iosMain by getting {
            dependencies {
                implementation(Dependency.Ktor.clientIos)
            }
        }
        val iosTest by getting

        val jsMain by getting {
            dependencies {
                implementation(Dependency.Ktor.clientJs)
            }
        }
        val jsTest by getting
    }
}

android {
    compileSdk = Version.Android.compileSdkVersion
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Version.Android.minSdkVersion
        targetSdk = Version.Android.targetSdkVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}