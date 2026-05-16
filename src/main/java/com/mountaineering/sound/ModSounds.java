package com.mountaineering.sound;

import com.mountaineering.MountaineeringMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MountaineeringMod.MOD_ID);

    public static final RegistryObject<SoundEvent> HEARTBEAT = SOUNDS.register("heartbeat",
            () -> SoundEvent.createVariableRangeEvent(
                    new ResourceLocation(MountaineeringMod.MOD_ID, "heartbeat")));

    public static final RegistryObject<SoundEvent> HEAVY_BREATHING = SOUNDS.register("heavy_breathing",
            () -> SoundEvent.createVariableRangeEvent(
                    new ResourceLocation(MountaineeringMod.MOD_ID, "heavy_breathing")));

    public static final RegistryObject<SoundEvent> WIND_AMBIENT = SOUNDS.register("wind_ambient",
            () -> SoundEvent.createVariableRangeEvent(
                    new ResourceLocation(MountaineeringMod.MOD_ID, "wind_ambient")));

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
