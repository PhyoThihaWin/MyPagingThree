plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("org.jlleitschuh.gradle.ktlint") //--> ktlint <--
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.pthw.mypagingthree"
    compileSdk = BuildConfigConst.compileSdk

    defaultConfig {
        applicationId = "com.pthw.mypagingthree"
        minSdk = BuildConfigConst.minSdk
        targetSdk = BuildConfigConst.targetSdk
        versionCode = BuildConfigConst.appVersionCode
        versionName = BuildConfigConst.appVersionName
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
            isMinifyEnabled = ReleaseBuild.isMinifyEnabled
            isShrinkResources = ReleaseBuild.isShrinkResources
            isDebuggable = ReleaseBuild.isDebuggable

            resValue("string", "app_name", "MyPagingThree App")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = DebugBuild.isMinifyEnabled
            isShrinkResources = DebugBuild.isShrinkResources
            isDebuggable = DebugBuild.isDebuggable

//            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            resValue("string", "app_name", "MyPagingThree Debug")
        }
        register("qa") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = DebugBuild.isMinifyEnabled
            isShrinkResources = DebugBuild.isShrinkResources
            isDebuggable = DebugBuild.isDebuggable

//            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            resValue("string", "app_name", "MyPagingThree QA")
        }
        register("uat") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = UatBuild.isMinifyEnabled
            isShrinkResources = UatBuild.isShrinkResources
            isDebuggable = UatBuild.isDebuggable

//            applicationIdSuffix = ".uat"
            versionNameSuffix = "-uat"
            resValue("string", "app_name", "MyPagingThree UAT")
        }
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    //desugaring lib =>don't update this libs
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
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("org.imaginativeworld.whynotimagecarousel:whynotimagecarousel:2.1.0")

    //--photoview
    implementation("com.github.chrisbanes:PhotoView:2.3.0")
    //--recycler layout style chip chap
    implementation("com.google.android.flexbox:flexbox:3.0.0")

}

ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
}
