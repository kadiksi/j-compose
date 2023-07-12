import java.io.FileInputStream
import java.util.Date
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

// Load keystore
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}
val date = Date()

android {
    compileSdk = AppConfig.targetSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        multiDexEnabled = true
    }

    buildTypes {
        getByName("release") {
            resValue("string", AppConfig.appName, "JCourier 2.0")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
        }
        getByName("debug") {
            initWith(buildTypes.getByName("debug"))
            resValue("string", AppConfig.appName, "JCourier 2.0 Test")
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    signingConfigs {
        maybeCreate("debug")
        getByName("debug") {
            storeFile = file(keystoreProperties.getProperty("devStoreFile"))
            storePassword = (keystoreProperties.getProperty("devStorePassword"))
            keyAlias = (keystoreProperties.getProperty("devKeyAlias"))
            keyPassword = (keystoreProperties.getProperty("devKeyPassword"))
        }

        maybeCreate("release")
        getByName("release") {
            storeFile = file(keystoreProperties.getProperty("releaseStoreFile"))
            storePassword = keystoreProperties.getProperty("releaseStorePassword")
            keyAlias = keystoreProperties.getProperty("releaseKeyAlias")
            keyPassword = keystoreProperties.getProperty("releaseKeyPassword")
        }
    }

    flavorDimensions.add(AppConfig.dimension)
    productFlavors {
        create("dev") {
            dimension = AppConfig.dimension
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "HOST", "\"https://test5.jmart.kz/\"")
            signingConfig = signingConfigs.getByName("debug")
        }
        create("prod") {
            dimension = AppConfig.dimension
            buildConfigField("String", "HOST", "\"https://jmart.kz/\"")
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    namespace = "kz.post.jcourier"
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    android {
        lint {
            baseline = file("lint-baseline.xml")
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.compose.ui:ui:1.4.3")
    implementation ("androidx.compose.material3:material3:1.2.0-alpha02")
    implementation ("androidx.compose.material:material:1.5.0-beta01")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.activity:activity-compose:1.7.2")
    testImplementation ("junit:junit:4.+")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.4.3")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.4.3")
    implementation ("io.socket:socket.io-client:2.0.1")
    implementation ("com.google.dagger:hilt-android:2.45")
    kapt ("com.google.dagger:hilt-compiler:2.45")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation ("io.coil-kt:coil-compose:2.2.2")

    implementation ("androidx.navigation:navigation-compose:2.5.3")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    //Preferences
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    //Timber
    implementation ("com.jakewharton.timber:timber:5.0.1")
    //Swipe-to-refresh
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.28.0")
    //Maps
    implementation ("com.google.maps.android:maps-compose:2.8.0")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
//    Location
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    //Firebase
    implementation (platform("com.google.firebase:firebase-bom:32.1.0"))
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-messaging")
}
//kapt {
//    correctErrorTypes = true
//}