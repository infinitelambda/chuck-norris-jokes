object Version {

    const val kotlinVersion = "1.6.21"

    const val ktorVersion = "2.0.2"

    const val serializationVersion = "1.3.2"

    const val coroutinesVersion = "1.6.0-native-mt"

    object Android {

        const val minSdkVersion = 23
        const val targetSdkVersion = 32
        const val compileSdkVersion = targetSdkVersion

        const val coreKtxVersion = "1.7.0"
        const val composeVersion = "1.1.1"
        const val lifecycleVersion = "2.4.1"
        const val activityComposeVersion = "1.4.0"
        const val junitVersion = "1.1.3"
        const val espressoVersion = "3.4.0"
    }

    object Test {
        const val junitVersion = "4.13.2"
    }

    object Plugin {
        const val android = "7.1.2"
    }
}