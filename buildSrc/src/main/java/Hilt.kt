import org.gradle.api.artifacts.dsl.DependencyHandler

object Hilt {
    private const val version = "2.50"
    const val android = "com.google.dagger:hilt-android:$version"
    const val compiler = "com.google.dagger:hilt-compiler:$version"
}

fun DependencyHandler.hilt() {
    implementation(Hilt.android)
    kapt(Hilt.compiler)
}