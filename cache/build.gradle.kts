plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

android {
    namespace = "com.pthw.cache"
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isJniDebuggable = true
            isMinifyEnabled = false
        }
        register("qa") {
            isJniDebuggable = true
            isMinifyEnabled = false
        }
        register("uat") {
            isJniDebuggable = true
            isMinifyEnabled = false
        }
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    testImplementation(TestDep.junit)
    androidTestImplementation(TestDep.androidXJunit)
    androidTestImplementation(Espresso.core)

    implementation(CommonDep.javaxInject)
    implementation(CommonDep.timber)

    hilt()
    moshi()

    implementation(Room.ktx)
    implementation(Room.runtime)
    implementation(Room.paging)
    implementation(Paging.runtimeKtx)
    kapt(Room.compiler)

    implementation(project(":data"))
    implementation(project(":network"))
}