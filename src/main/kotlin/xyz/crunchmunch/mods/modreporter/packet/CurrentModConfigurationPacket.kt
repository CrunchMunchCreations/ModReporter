package xyz.crunchmunch.mods.modreporter.packet

import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload

data class CurrentModConfigurationPacket(
    val mods: Map<String, String>, // mod ID -> mod version
) : CustomPacketPayload {
    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return ModReporterPackets.CLIENT_CONFIGURATION
    }

    companion object {
        val CODEC = StreamCodec.composite(
            ByteBufCodecs.map(::HashMap, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.STRING_UTF8), CurrentModConfigurationPacket::mods,
            ::CurrentModConfigurationPacket
        )
    }
}
