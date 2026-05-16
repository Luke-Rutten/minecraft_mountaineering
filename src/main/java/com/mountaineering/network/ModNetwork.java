package com.mountaineering.network;

import com.mountaineering.MountaineeringMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MountaineeringMod.MOD_ID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.messageBuilder(SyncPlayerDataPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SyncPlayerDataPacket::encode)
                .decoder(SyncPlayerDataPacket::new)
                .consumerMainThread(SyncPlayerDataPacket::handle)
                .add();
        CHANNEL.messageBuilder(SyncWeatherPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SyncWeatherPacket::encode)
                .decoder(SyncWeatherPacket::new)
                .consumerMainThread(SyncWeatherPacket::handle)
                .add();
    }
}
