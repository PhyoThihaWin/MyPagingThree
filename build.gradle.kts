// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version ("7.3.0") apply (false)
    id("com.android.library") version ("7.3.0") apply (false)
    id("org.jetbrains.kotlin.android") version ("1.7.10") apply (false)
    id("org.jetbrains.kotlin.jvm") version ("1.7.20") apply (false)
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
        classpath ("com.google.gms:google-services:4.3.13")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

fun credentialData(): java.util.Properties {
    val credentialProperties = java.util.Properties()
    credentialProperties.load(java.io.FileInputStream(project.rootProject.file("credential.properties")))
    return credentialProperties
}

val credentialProperties = credentialData()

ext {
    // BASE_URL CONFIG
    set("devBaseUrl", credentialProperties["devBaseUrl"].toString())
    set("qaBaseUrl", credentialProperties["qaBaseUrl"].toString())
    set("uatBaseUrl", credentialProperties["uatBaseUrl"].toString())
    set("prodBaseUrl", credentialProperties["prodBaseUrl"].toString())

    // API KEY CONFIG
    set("credentialClientId", credentialProperties["clientId"].toString())

    // KEYSTORE CONFIG
    set("credentialStoreFile", credentialProperties["storeFile"].toString())
    set("credentialStorePassword", credentialProperties["storePassword"].toString())
    set("credentialKeyAlias", credentialProperties["keyAlias"].toString())
    set("credentialKeyPassword", credentialProperties["keyPassword"].toString())

    // VERSION CONFIG
//    set("versionMajor", credentialProperties["versionMajor"])
//    set("versionMinor", credentialProperties["versionMinor"])
//    set("versionPatch", credentialProperties["versionPatch"])
//    set("versionBuild", credentialProperties["versionBuild"])
}
