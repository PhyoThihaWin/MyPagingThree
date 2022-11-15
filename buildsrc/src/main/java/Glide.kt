import org.gradle.api.artifacts.dsl.DependencyHandler

object Glide {
    private const val version = "4.13.0"
    const val core = "com.github.bumptech.glide:glide:$version"
    const val compiler = "com.github.bumptech.glide:compiler:$version"

}

fun DependencyHandler.glide() {
    implementation(Glide.core)
    kapt(Glide.compiler)
}

