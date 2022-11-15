plugins {
    id ("java-library")
    id ("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(CommonDep.javaxInject)
    testImplementation(TestDep.junit)
    testImplementation(KotlinCoroutine.test)
    api(KotlinCoroutine.core)
    implementation(Paging.common)
}