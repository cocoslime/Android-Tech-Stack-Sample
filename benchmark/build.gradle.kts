plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.cocoslime.benchmark"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    defaultConfig {
        minSdk = 27
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    buildTypes {
//        // This benchmark buildType is used for benchmarking, and should function like your
//        // release build (for example, with minification on). It"s signed with a debug key
//        // for easy local/CI testing.
//        create("benchmark") {
//            isDebuggable = true
//            signingConfig = getByName("debug").signingConfig
//            matchingFallbacks += listOf("release")
//        }
//    }

    targetProjectPath = ":app"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)

    implementation("androidx.tracing:tracing-perfetto:1.0.0")
    implementation("androidx.tracing:tracing-perfetto-binary:1.0.0")
}

//androidComponents {
//    beforeVariants(selector().all()) {
//        it.enable = it.buildType == "benchmark"
//    }
//}
