import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

internal fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

internal fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

internal fun DependencyHandler.kaptTest(depName: String) {
    add("kaptTest", depName)
}

internal fun DependencyHandler.compileOnly(depName: String) {
    add("compileOnly", depName)
}

internal fun DependencyHandler.api(depName: String) {
    add("api", depName)
}

internal fun DependencyHandler.testImplementation(depName: String) {
    add("testImplementation", depName)
}

internal fun DependencyHandler.androidTestImplementation(depName: String) {
    add("androidTestImplementation", depName)
}

internal fun DependencyHandler.debugImplementation(dependencyNotation: Any) {
    add("debugImplementation", dependencyNotation)
}


internal fun DependencyHandler.releaseImplementation(dependencyNotation: Any) {
    add("releaseImplementation", dependencyNotation)
}

fun DependencyHandler.uatImplementation(dependencyNotation: Any) {
    add("uatImplementation", dependencyNotation)
}

fun DependencyHandler.qaImplementation(dependencyNotation: Any) {
    add("qaImplementation", dependencyNotation)
}

