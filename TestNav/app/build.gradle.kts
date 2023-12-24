plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    //id("kotlin-kapt")
    id("kotlin-android")
    //id("kotlinx-serialization")
}

android {
    namespace = "com.example.testnav"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testnav"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


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
    kotlinOptions {
        jvmTarget = "1.8"
    }



    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("com.google.firebase:firebase-crashlytics:18.6.0")
    implementation("com.google.firebase:firebase-analytics:21.5.0")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    implementation("com.google.firebase:firebase-firestore:24.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation ("androidx.navigation:navigation-compose:2.7.5")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("io.coil-kt:coil-compose:1.3.2")

    //implementation ("io.ktor:ktor-client-android:1.7.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    //implementation ("com.google.accompanist:accompanist-coil:0.19.0")

    implementation ("com.google.dagger:hilt-android:2.39")

    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")

    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0-rc01")

    //Retrofit
    //implementation("com.squared.retrofit2:retrofit:2.9.0")
    //implementation("com.squared.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    //Kotlinx
    //implementation("org.jetbrains.kotlinx;kotlinx-serialization-json:1.3.0")

    //Room component
    implementation("androidx.room:room-runtime:2.4.0")

    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-paging:2.6.1")

    //Paging
    implementation("androidx.paging:paging-compose:1.0.0-alpha14")

//    kapt("androidx.room:room-compiler:2.4.0")
//    kapt ("androidx.hilt:hilt-compiler:1.0.0-alpha03")
//    kapt ("com.google.dagger:hilt-android-compiler:2.39")
}
