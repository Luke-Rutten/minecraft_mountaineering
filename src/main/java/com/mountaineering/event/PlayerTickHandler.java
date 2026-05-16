package com.mountaineering.event;

import com.mountaineering.MountaineeringMod;
import com.mountaineering.config.MountaineeringConfig;
import com.mountaineering.data.MountainDataCapability;
import com.mountaineering.item.OxygenTankItem;
import com.mountaineering.network.ModNetwork;
import com.mountaineering.network.SyncPlayerDataPacket;
import com.mountaineering.network.SyncWeatherPacket;
import com.mountaineering.system.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = MountaineeringMod.MOD_ID)
public class PlayerTickHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide()) return;

        Player player = event.player;
        player.getCapability(MountainDataCapability.PLAYER_DATA).ifPresent(data -> {
            boolean jumped = data.wasOnGround() && !player.onGround()
                    && player.getDeltaMovement().y > 0.1;

            // Oxygen tank management: find first active tank, drain it
            data.setUsingOxygenTank(false);
            ItemStack activeTank = findActiveTank(player);
            if (activeTank != null) {
                data.setUsingOxygenTank(true);
                OxygenTankItem.drainTick(activeTank, player);
            }

            // Weather
            float effectiveWeather = 0;
            if (player.level() instanceof ServerLevel serverLevel) {
                WeatherEngine weather = WeatherEngine.get(serverLevel);
                double meters = AltitudeHelper.getMeters(player);
                effectiveWeather = weather.getEffectiveIntensity(meters);
            }

            // Shelter detection (cached, refreshed every 20 ticks)
            if (data.getShelterCheckCooldown() <= 0) {
                data.setCachedSheltered(ShelterHelper.isSheltered(player));
                data.setCachedNearHeater(ShelterHelper.isNearHeater(player));
                data.setShelterCheckCooldown(20);
            } else {
                data.setShelterCheckCooldown(data.getShelterCheckCooldown() - 1);
            }

            OxygenSystem.tick(player, data, jumped);
            StaminaSystem.tick(player, data, jumped);
            TemperatureSystem.tick(player, data, effectiveWeather);
            ColdToleranceSystem.tick(player, data);

            data.setWasOnGround(player.onGround());

            // Second wind: brief recovery at near-death
            if (data.getSecondWindCooldown() > 0) {
                data.setSecondWindCooldown(data.getSecondWindCooldown() - 1);
            }
            if (data.getO2Current() < 5 && data.getStamina() < 5
                    && data.getSecondWindCooldown() <= 0) {
                data.setStamina(Math.min(100, data.getStamina() + 20));
                data.setO2Current(Math.min(data.getO2Base(), data.getO2Current() + 15));
                data.setSecondWindCooldown(6000);
            }

            if (player instanceof ServerPlayer sp) {
                if (player.tickCount % MountaineeringConfig.SYNC_INTERVAL.get() == 0) {
                    ModNetwork.CHANNEL.send(
                            PacketDistributor.PLAYER.with(() -> sp),
                            new SyncPlayerDataPacket(data)
                    );
                }
            }
        });
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return;

        server.getAllLevels().forEach(level -> {
            if (level.dimension() == Level.OVERWORLD) {
                WeatherEngine weather = WeatherEngine.get(level);
                weather.tick();

                if (level.getGameTime() % MountaineeringConfig.SYNC_INTERVAL.get() == 0) {
                    SyncWeatherPacket packet = new SyncWeatherPacket(
                            weather.getWeatherIntensity(),
                            weather.getStormPressure(),
                            weather.getInstability()
                    );
                    for (ServerPlayer sp : level.players()) {
                        ModNetwork.CHANNEL.send(
                                PacketDistributor.PLAYER.with(() -> sp),
                                packet
                        );
                    }
                }
            }
        });
    }

    private static ItemStack findActiveTank(Player player) {
        ItemStack offhand = player.getOffhandItem();
        if (offhand.getItem() instanceof OxygenTankItem && OxygenTankItem.getOxygen(offhand) > 0) {
            return offhand;
        }
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof OxygenTankItem && OxygenTankItem.getOxygen(stack) > 0) {
                return stack;
            }
        }
        return null;
    }
}
