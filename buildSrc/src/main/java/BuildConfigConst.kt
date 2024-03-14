object BuildConfigConst {
    const val compileSdk = 34
    const val minSdk = 21
    const val targetSdk = 34

    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionBuild = 2

    const val appVersionName = "$versionMajor.$versionMinor.$versionPatch"
    const val appVersionCode =
        versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild
}

object ReleaseBuild {
    val isDebuggable = false
    val isMinifyEnabled = true
    val isShrinkResources = true
}

object UatBuild {
    val isDebuggable = true
    val isMinifyEnabled = true
    val isShrinkResources = true
}

object DebugBuild {
    val isDebuggable = true
    val isMinifyEnabled = false
    val isShrinkResources = false
}