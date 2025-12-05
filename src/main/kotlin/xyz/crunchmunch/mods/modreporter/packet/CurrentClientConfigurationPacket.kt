package xyz.crunchmunch.mods.modreporter.packet

import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload

data class CurrentClientConfigurationPacket(
    val branding: String,
    val versionType: String,
    val launchedVersion: String,
    val gpuRenderer: String,
    val gpuVersion: String,
    val gpuBackend: String,
    val gpuVendor: String,
    val extras: Map<String, String>
) : CustomPacketPayload {
    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return ModReporterPackets.CLIENT_CONFIGURATION
    }

    companion object {
        val CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, CurrentClientConfigurationPacket::branding,
            ByteBufCodecs.STRING_UTF8, CurrentClientConfigurationPacket::versionType,
            ByteBufCodecs.STRING_UTF8, CurrentClientConfigurationPacket::launchedVersion,
            ByteBufCodecs.STRING_UTF8, CurrentClientConfigurationPacket::gpuRenderer,
            ByteBufCodecs.STRING_UTF8, CurrentClientConfigurationPacket::gpuVersion,
            ByteBufCodecs.STRING_UTF8, CurrentClientConfigurationPacket::gpuBackend,
            ByteBufCodecs.STRING_UTF8, CurrentClientConfigurationPacket::gpuVendor,
            ByteBufCodecs.map(::HashMap, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.STRING_UTF8), CurrentClientConfigurationPacket::extras,
            ::CurrentClientConfigurationPacket
        )
    }
}
