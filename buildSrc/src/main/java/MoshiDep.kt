import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.moshi() {
    implementation(Moshi.core)
    implementation(Moshi.adapters)
    implementation(Moshi.kotlin)
    implementation(Moshi.converter)
    kapt(Moshi.code_gen)
}

object Moshi {
    private const val version = "1.15.0"
    const val core = "com.squareup.moshi:moshi:$version"
    const val adapters = "com.squareup.moshi:moshi-adapters:$version"
    const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
    const val code_gen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    const val converter = "com.squareup.retrofit2:converter-gson:2.9.0"
}
