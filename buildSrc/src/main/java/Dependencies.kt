object OkHttp {
    private const val version = "4.10.0"
    const val client = "com.squareup.okhttp3:okhttp:$version"
    const val logger = "com.squareup.okhttp3:logging-interceptor:$version"
    const val mock_web_server = "com.squareup.okhttp3:mockwebserver:$version"
}

object Retrofit {
    private const val version = "2.9.0"
    const val core = "com.squareup.retrofit2:retrofit:$version"
    const val moshi_converter = "com.squareup.retrofit2:converter-moshi:$version"
}

internal object OldCommonLibs {
    const val desugar_lib = "com.android.tools:desugar_jdk_libs:1.0.10"
    const val phrase = "com.squareup.phrase:phrase:1.1.0"
    const val sonar = "com.facebook.sonar:sonar:0.0.1"
    const val rabbkt = "com.aungkyawpaing.rabbkt:rabbkt:1.0.1"
    const val easy_image = "com.github.jkwiecien:EasyImage:3.0.3"
    const val android_crop = "com.soundcloud.android:android-crop:1.0.1@aar"
    const val ucrop = "com.github.yalantis:ucrop:2.2.2"
    const val fastscroll = "com.simplecityapps:recyclerview-fastscroll:2.0.0"
    const val colorpicker = "com.github.QuadFlask:colorpicker:0.0.13"
    const val circle_image_view = "de.hdodenhof:circleimageview:3.0.0"
    const val shape_of_views = "com.github.florent37:shapeofview:1.4.7"
}

object KotlinCoroutine {
    private const val version = "1.6.4"
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    const val playservice = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$version"
}

object Paging {
    private const val version = "3.1.1"
    const val common = "androidx.paging:paging-common-ktx:$version"
    const val runtimeKtx = "androidx.paging:paging-runtime-ktx:$version"
}

object Room {
    private const val version = "2.6.1"
    const val paging = "androidx.room:room-paging:$version"
    const val ktx = "androidx.room:room-ktx:$version"
    const val runtime = "androidx.room:room-runtime:$version"
    const val compiler = "androidx.room:room-compiler:$version"
}

object DataStoreDep {
    const val datastorePreferences = "androidx.datastore:datastore-preferences:1.0.0"
}