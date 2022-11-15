plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    api(project(":domain"))

    implementation(CommonDep.javaxInject)
    api(KotlinCoroutine.core)
    implementation(Paging.common)
}