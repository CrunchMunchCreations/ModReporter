package xyz.crunchmunch.mods.modreporter

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.resources.ResourceLocation
import xyz.crunchmunch.mods.modreporter.packet.ModReporterPackets
import java.util.*

class ModReporter : ModInitializer {
    val clientConfigurations = Collections.synchronizedMap(mutableMapOf<UUID, PlayerConfiguration>())
    val modConfigurations = Collections.synchronizedMap(mutableMapOf<UUID, Map<String, String>>())

    override fun onInitialize() {
        ModReporterPackets.init()

        ServerPlayNetworking.registerGlobalReceiver(ModReporterPackets.CLIENT_CONFIGURATION) { packet, ctx ->
            clientConfigurations[ctx.player().uuid] = PlayerConfiguration(
                packet.branding,
                packet.versionType,
                packet.launchedVersion,
                packet.gpuRenderer,
                packet.gpuVersion,
                packet.gpuBackend,
                packet.gpuVersion,
                packet.extras
            )
        }

        ServerPlayNetworking.registerGlobalReceiver(ModReporterPackets.MOD_CONFIGURATION) { packet, ctx ->
            modConfigurations[ctx.player().uuid] = packet.mods
        }

        ServerPlayConnectionEvents.DISCONNECT.register { handler, server ->
            clientConfigurations.remove(handler.player.uuid)
            modConfigurations.remove(handler.player.uuid)
        }

        ServerLifecycleEvents.SERVER_STOPPING.register { server ->
            clientConfigurations.clear()
            modConfigurations.clear()
        }
    }

    companion object {
        const val MOD_ID = "mod_reporter"

        fun id(path: String): ResourceLocation {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
        }
    }
}
