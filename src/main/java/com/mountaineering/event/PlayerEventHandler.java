package com.mountaineering.event;

import com.mountaineering.MountaineeringMod;
import com.mountaineering.data.MountainDataCapability;
import com.mountaineering.system.AcclimatizationSystem;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MountaineeringMod.MOD_ID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(MountainDataCapability.PLAYER_DATA).ifPresent(oldData -> {
            event.getEntity().getCapability(MountainDataCapability.PLAYER_DATA).ifPresent(newData -> {
                newData.copyFrom(oldData);
            });
        });
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        player.getCapability(MountainDataCapability.PLAYER_DATA).ifPresent(data -> {
            AcclimatizationSystem.onSleep(player, data);
        });
    }
}
