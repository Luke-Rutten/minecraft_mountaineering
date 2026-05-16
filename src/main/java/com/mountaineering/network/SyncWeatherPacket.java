package com.mountaineering.network;

import com.mountaineering.client.ClientMountainData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncWeatherPacket {
    private final float weatherIntensity;
    private final float stormPressure;
    private final float instability;

    public SyncWeatherPacket(float weatherIntensity, float stormPressure, float instability) {
        this.weatherIntensity = weatherIntensity;
        this.stormPressure = stormPressure;
        this.instability = instability;
    }

    public SyncWeatherPacket(FriendlyByteBuf buf) {
        this.weatherIntensity = buf.readFloat();
        this.stormPressure = buf.readFloat();
        this.instability = buf.readFloat();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(weatherIntensity);
        buf.writeFloat(stormPressure);
        buf.writeFloat(instability);
    }

    public static void handle(SyncWeatherPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ClientMountainData.setWeatherIntensity(msg.weatherIntensity);
        ClientMountainData.setStormPressure(msg.stormPressure);
        ClientMountainData.setInstability(msg.instability);
    }
}
