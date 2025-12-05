package xyz.crunchmunch.mods.modreporter

data class PlayerConfiguration(
    val branding: String,
    val versionType: String,
    val launchedVersion: String,
    val gpuRenderer: String,
    val gpuVersion: String,
    val gpuBackend: String,
    val gpuVendor: String,
    val extras: Map<String, String>
)