plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("org.jlleitschuh.gradle.ktlint")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(CommonDep.javaxInject)
    testImplementation(TestDep.junit)
    testImplementation(KotlinCoroutine.test)
    api(KotlinCoroutine.core)
    implementation(Paging.common)
}

ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
}
