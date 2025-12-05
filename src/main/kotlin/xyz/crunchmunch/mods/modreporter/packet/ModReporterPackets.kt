package xyz.crunchmunch.mods.modreporter.packet

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import xyz.crunchmunch.mods.modreporter.ModReporter

object ModReporterPackets {
    val CLIENT_CONFIGURATION = registerS2C("client_configuration", CurrentClientConfigurationPacket.CODEC)
    val MOD_CONFIGURATION = registerS2C("mod_configuration", CurrentModConfigurationPacket.CODEC)

    fun <V : CustomPacketPayload> registerS2C(id: String, codec: StreamCodec<in RegistryFriendlyByteBuf, V>): CustomPacketPayload.Type<V> {
        val type = CustomPacketPayload.Type<V>(ModReporter.id(id))
        PayloadTypeRegistry.playS2C().register(type, codec)

        return type
    }

    fun init() {}
}
