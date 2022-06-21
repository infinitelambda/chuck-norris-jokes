plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = Version.Android.compileSdkVersion
    defaultConfig {
        applicationId = "com.infinitelambda.chuck.android"
        minSdk = Version.Android.minSdkVersion
        targetSdk = Version.Android.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.infinitelambda.chuck.android.ChuckJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Version.Android.composeVersion
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(Dependency.Project.sdk))

    implementation(Dependency.Androidx.coreKtx)
    implementation(Dependency.Androidx.Compose.ui)
    implementation(Dependency.Androidx.Compose.material)
    implementation(Dependency.Androidx.Compose.uiToolingPreview)
    implementation(Dependency.Androidx.Lifecycle.lifecycle)
    implementation(Dependency.Androidx.Lifecycle.lifecycleCompose)
    implementation(Dependency.Androidx.activityCompose)
    implementation(Dependency.Kotlin.stdLib)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutinesVersion}")


    testImplementation(Dependency.Test.junit)

    androidTestImplementation(Dependency.Androidx.Test.junit)
    androidTestImplementation(Dependency.Androidx.Test.espresso)
    androidTestImplementation(Dependency.Androidx.Compose.uiTest)
    androidTestImplementation(Dependency.AndroidTest.dexopener)
    androidTestImplementation(Dependency.AndroidTest.mockk)

    debugImplementation(Dependency.Androidx.Compose.uiTooling)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}