package com.mountaineering.event;

import com.mountaineering.MountaineeringMod;
import com.mountaineering.data.MountainDataCapability;
import com.mountaineering.data.MountainDataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MountaineeringMod.MOD_ID)
public class CapabilityHandler {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(MountainDataCapability.PLAYER_DATA).isPresent()) {
                event.addCapability(
                        new ResourceLocation(MountaineeringMod.MOD_ID, "mountain_data"),
                        new MountainDataProvider()
                );
            }
        }
    }
}
