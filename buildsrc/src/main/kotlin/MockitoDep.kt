import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.mockito() {
  testImplementation(Mockito.core)
  testImplementation(Mockito.inline)
  testImplementation(Mockito.kotlin)
}

fun DependencyHandler.mockitoAndroid() {
  testImplementation(Mockito.android)
  androidTestImplementation(Mockito.core)
  androidTestImplementation(Mockito.inline)
  androidTestImplementation(Mockito.kotlin)
}


object Mockito {
  private const val version = "4.7.0"
  const val core = "org.mockito:mockito-core:4.7.0"
  const val android = "org.mockito:mockito-android:4.7.0"
  const val inline = "org.mockito:mockito-inline:4.7.0"
  private const val kotlinNharrman = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
  const val kotlin = "org.mockito.kotlin:mockito-kotlin:4.0.0"
}