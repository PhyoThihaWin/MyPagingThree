plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("org.jlleitschuh.gradle.ktlint") // --> ktlint <--
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val appVersionName = "${rootProject.ext["versionMajor"]}.${rootProject.ext["versionMinor"]}.${rootProject.ext["versionPatch"]}"
val appVersionCode = rootProject.ext["versionMajor"].toString().toInt() * 1000000 + rootProject.ext["versionMinor"].toString().toInt() * 10000 + rootProject.ext["versionPatch"].toString().toInt() * 100 + rootProject.ext["versionBuild"].toString().toInt()

android {
    namespace = "com.pthw.mypagingthree"
    compileSdk = 32

    defaultConfig {
        applicationId = "com.pthw.mypagingthree"
        minSdk = 21
        targetSdk = 32
        versionCode = appVersionCode
        versionName = appVersionName
        setProperty("archivesBaseName", "MyPagingThree-$versionName")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(rootProject.ext["credentialStoreFile"] as String)
            storePassword = rootProject.ext["credentialStorePassword"] as String
            keyAlias = rootProject.ext["credentialKeyAlias"] as String
            keyPassword = rootProject.ext["credentialKeyPassword"] as String
            enableV2Signing = true
        }

        getByName("debug") {
            storeFile = file(rootProject.ext["credentialStoreFile"] as String)
            storePassword = rootProject.ext["credentialStorePassword"] as String
            keyAlias = rootProject.ext["credentialKeyAlias"] as String
            keyPassword = rootProject.ext["credentialKeyPassword"] as String
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            resValue("string", "app_name", "MyPagingThree App")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true

//            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            resValue("string", "app_name", "MyPagingThree Debug")
        }
        register("qa") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true

//            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            resValue("string", "app_name", "MyPagingThree QA")
        }
        register("uat") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true

//            applicationIdSuffix = ".uat"
            versionNameSuffix = "-uat"
            resValue("string", "app_name", "MyPagingThree UAT")
        }
    }
//    java {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":appbase"))
    implementation(project(":listdialog"))

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-ktx:1.5.1")

    testImplementation(TestDep.junit)
    androidTestImplementation(TestDep.androidXJunit)
    androidTestImplementation(Espresso.core)

    glide()
    hilt()
    implementation(CommonDep.timber)
    implementation(Paging.runtimeKtx)
    kapt(KotlinCoroutine.core)

    // desugaring lib =>don't update this libs
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    // Google ModernStorage
    implementation("com.google.modernstorage:modernstorage-bom:1.0.0-alpha06")
    implementation("com.google.modernstorage:modernstorage-permissions")
    implementation("com.google.modernstorage:modernstorage-storage")
    implementation("com.google.modernstorage:modernstorage-photopicker")
    implementation("com.squareup.okio:okio")

    implementation(platform("com.google.firebase:firebase-bom:31.1.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    implementation("com.firebaseui:firebase-ui-firestore:8.0.2")
}

ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
}
