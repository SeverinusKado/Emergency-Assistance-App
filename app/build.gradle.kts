plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.mednow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mednow"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.auth)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation (libs.google.firebase.database)
    implementation(libs.room.common)
    implementation(libs.foundation.android)
    implementation (libs.logging.interceptor)
    implementation (libs.json.v20231013)
    implementation (libs.tensorflow.lite)
    implementation (libs.tensorflow.lite.support)
    implementation (libs.okhttp.v491)
    implementation (libs.material.v150)
    implementation (libs.places)
    implementation (libs.play.services.location.v2101)
    implementation (libs.play.services.maps.v1802)
    implementation (libs.osmdroid.android)
    implementation (libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}