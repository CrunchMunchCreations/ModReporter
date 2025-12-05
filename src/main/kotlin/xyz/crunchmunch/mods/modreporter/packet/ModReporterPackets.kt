package xyz.crunchmunch.mods.modreporter.packet

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import xyz.crunchmunch.mods.modreporter.ModReporter

object ModReporterPackets {
    val CLIENT_CONFIGURATION = registerC2S("client_configuration", CurrentClientConfigurationPacket.CODEC)
    val MOD_CONFIGURATION = registerC2S("mod_configuration", CurrentModConfigurationPacket.CODEC)

    fun <V : CustomPacketPayload> registerC2S(id: String, codec: StreamCodec<in RegistryFriendlyByteBuf, V>): CustomPacketPayload.Type<V> {
        val type = CustomPacketPayload.Type<V>(ModReporter.id(id))
        PayloadTypeRegistry.playC2S().register(type, codec)

        return type
    }

    fun init() {}
}
