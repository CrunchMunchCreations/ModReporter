package xyz.crunchmunch.mods.modreporter

data class UnusualModConfiguration(
    val isMissingReporter: Boolean,
    val extraMods: Map<String, String> = emptyMap(),
    val missingMods: Set<String> = emptySet()
)
