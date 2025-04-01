plugins {
    // android
    id("com.android.library")
    // jetbrains
    id("org.jetbrains.kotlin.android")
    // maven
    id("maven-publish")
}

android {

    namespace  = "ir.farsroidx.m31"
    compileSdk = 35

    defaultConfig {
        multiDexEnabled           = true
        minSdk                    = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {

        release {

            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "r8-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // Android-X
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")

}

publishing {

    publications {

        register<MavenPublication>("release") {

            groupId    = "ir.farsroidx.m31"
            artifactId = "andromeda-extensions"
            version    = "2.1.0"

            afterEvaluate {

                from(
                    components["release"]
                )
            }
        }
    }
}