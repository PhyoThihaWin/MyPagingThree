plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

android {
    namespace = "com.pthw.network"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField ("String", "BASE_URL", "\"${rootProject.ext["prodBaseUrl"]}\"")
            buildConfigField ("String", "CLIENT_ID", "\"${rootProject.ext["credentialClientId"]}\"")
        }
        debug {
            isJniDebuggable = true
            isMinifyEnabled = false

            buildConfigField ("String", "BASE_URL", "\"${rootProject.ext["devBaseUrl"]}\"")
            buildConfigField ("String", "CLIENT_ID", "\"${rootProject.ext["credentialClientId"]}\"")
        }
        register("qa") {
            isJniDebuggable = true
            isMinifyEnabled = false

            buildConfigField ("String", "BASE_URL", "\"${rootProject.ext["qaBaseUrl"]}\"")
            buildConfigField ("String", "CLIENT_ID", "\"${rootProject.ext["credentialClientId"]}\"")
        }
        register("uat") {
            isJniDebuggable = true
            isMinifyEnabled = false

            buildConfigField ("String", "BASE_URL", "\"${rootProject.ext["uatBaseUrl"]}\"")
            buildConfigField ("String", "CLIENT_ID", "\"${rootProject.ext["credentialClientId"]}\"")
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
    api(project(":data"))

    hilt()

    testImplementation(TestDep.junit)
    androidTestImplementation(TestDep.androidXJunit)
    androidTestImplementation(Espresso.core)

    api(Retrofit.core)
    implementation(Retrofit.moshi_converter)

    implementation(Moshi.core)
    kapt(Moshi.code_gen)

    api(OkHttp.client)
    implementation(OkHttp.logger)
    testImplementation(OkHttp.mock_web_server)

    implementation(CommonDep.timber)
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    uatImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    qaImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    kaptTest(Moshi.code_gen)
    implementation(KotlinCoroutine.test)

    implementation (Paging.common)
    implementation (Paging.runtimeKtx)
}