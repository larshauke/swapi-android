plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "de.larshauke.swapi_android"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "de.larshauke.swapi_android"
        targetSdk = libs.versions.sdk.compile.get().toInt()
        minSdk = libs.versions.sdk.min.get().toInt()

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
            )
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.search)
    implementation(projects.details)

    implementation(libs.bundles.retrofitSetup)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi.kotlin)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    implementation(libs.androidx.compose.navigation)
    implementation(libs.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}