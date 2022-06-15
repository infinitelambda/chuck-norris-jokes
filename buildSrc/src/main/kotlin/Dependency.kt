object Dependency {

    object Ktor {
        const val clientCore = "io.ktor:ktor-client-core:${Version.ktorVersion}"
        const val clientAndroid = "io.ktor:ktor-client-okhttp:${Version.ktorVersion}"
        const val clientJs = "io.ktor:ktor-client-js:${Version.ktorVersion}"
        const val clientIos = "io.ktor:ktor-client-ios:${Version.ktorVersion}"
        const val clientMock = "io.ktor:ktor-client-mock:${Version.ktorVersion}"

        const val clientSerialization = "io.ktor:ktor-client-serialization:${Version.ktorVersion}"

        const val logging = "io.ktor:ktor-client-logging:${Version.ktorVersion}"
    }

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlinVersion}"
    }

    object Kotlinx {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.serializationVersion}"

        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutinesVersion}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutinesVersion}"
    }

    object Androidx {
        const val coreKtx = "androidx.core:core-ktx:${Version.Android.coreKtxVersion}"

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Version.Android.composeVersion}"
            const val material = "androidx.compose.material:material:${Version.Android.composeVersion}"
            const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Version.Android.composeVersion}"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:${Version.Android.composeVersion}"
            const val uiTooling = "androidx.compose.ui:ui-tooling:${Version.Android.composeVersion}"
        }

        object Lifecycle {
            const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.Android.lifecycleVersion}"
            const val lifecycleCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.Android.lifecycleVersion}"
        }

        const val activityCompose = "androidx.activity:activity-compose:${Version.Android.activityComposeVersion}"

        object Test {
            const val junit = "androidx.test.ext:junit:${Version.Android.junitVersion}"
            const val espresso = "androidx.test.espresso:espresso-core:${Version.Android.espressoVersion}"
        }
    }

    object Test {
        const val junit = "junit:junit:${Version.Test.junitVersion}"
    }

    object Project {
        const val sdk = ":sdk"
    }
}