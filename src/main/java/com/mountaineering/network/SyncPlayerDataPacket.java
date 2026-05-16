package com.mountaineering.network;

import com.mountaineering.client.ClientMountainData;
import com.mountaineering.data.PlayerMountainData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncPlayerDataPacket {
    private final float o2Base;
    private final float o2Current;
    private final float stamina;
    private final float bodyTemperature;
    private final float coldTolerance;
    private final float acclimatizedAlt;
    private final int secondWindCooldown;

    public SyncPlayerDataPacket(PlayerMountainData data) {
        this.o2Base = data.getO2Base();
        this.o2Current = data.getO2Current();
        this.stamina = data.getStamina();
        this.bodyTemperature = data.getBodyTemperature();
        this.coldTolerance = data.getColdTolerance();
        this.acclimatizedAlt = data.getAcclimatizedAlt();
        this.secondWindCooldown = data.getSecondWindCooldown();
    }

    public SyncPlayerDataPacket(FriendlyByteBuf buf) {
        this.o2Base = buf.readFloat();
        this.o2Current = buf.readFloat();
        this.stamina = buf.readFloat();
        this.bodyTemperature = buf.readFloat();
        this.coldTolerance = buf.readFloat();
        this.acclimatizedAlt = buf.readFloat();
        this.secondWindCooldown = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(o2Base);
        buf.writeFloat(o2Current);
        buf.writeFloat(stamina);
        buf.writeFloat(bodyTemperature);
        buf.writeFloat(coldTolerance);
        buf.writeFloat(acclimatizedAlt);
        buf.writeInt(secondWindCooldown);
    }

    public static void handle(SyncPlayerDataPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ClientMountainData.setO2Base(msg.o2Base);
        ClientMountainData.setO2Current(msg.o2Current);
        ClientMountainData.setStamina(msg.stamina);
        ClientMountainData.setBodyTemperature(msg.bodyTemperature);
        ClientMountainData.setColdTolerance(msg.coldTolerance);
        ClientMountainData.setAcclimatizedAlt(msg.acclimatizedAlt);
        ClientMountainData.setSecondWindCooldown(msg.secondWindCooldown);
    }
}
