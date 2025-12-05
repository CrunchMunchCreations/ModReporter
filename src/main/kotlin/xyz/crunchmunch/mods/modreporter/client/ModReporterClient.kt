package xyz.crunchmunch.mods.modreporter.client

import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.ClientBrandRetriever
import xyz.crunchmunch.mods.modreporter.packet.CurrentClientConfigurationPacket
import xyz.crunchmunch.mods.modreporter.packet.CurrentModConfigurationPacket
import xyz.crunchmunch.mods.modreporter.packet.ModReporterPackets

class ModReporterClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register { handler, sender, client ->
            if (ClientPlayNetworking.canSend(ModReporterPackets.CLIENT_CONFIGURATION)) {
                ClientPlayNetworking.send(CurrentClientConfigurationPacket(
                    ClientBrandRetriever.getClientModName(),
                    client.versionType,
                    client.launchedVersion,
                    RenderSystem.getDevice().renderer,
                    RenderSystem.getDevice().version,
                    RenderSystem.getDevice().backendName,
                    RenderSystem.getDevice().vendor,
                    mapOf(
                        "operating_system" to "${System.getProperty("os.name")} (${System.getProperty("os.arch")}) version ${System.getProperty("os.version")}",
                        "java" to "${System.getProperty("java.version")}, ${System.getProperty("java.vendor")}",
                        "java_vm" to "${System.getProperty("java.vm.name")} (${System.getProperty("java.vm.info")}), ${System.getProperty("java.vm.vendor")}",
                        "memory" to "${Runtime.getRuntime().maxMemory()} bytes max, ${Runtime.getRuntime().totalMemory()} bytes total, ${Runtime.getRuntime().freeMemory()} bytes free"
                    )
                ))
            }

            if (ClientPlayNetworking.canSend(ModReporterPackets.MOD_CONFIGURATION)) {
                val mods = FabricLoader.getInstance().allMods.associate { it.metadata.id to it.metadata.version.friendlyString }
                ClientPlayNetworking.send(CurrentModConfigurationPacket(mods))
            }
        }
    }
}
