package com.mountaineering.client;

import com.mountaineering.MountaineeringMod;
import com.mountaineering.sound.ModSounds;
import com.mountaineering.system.AltitudeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MountaineeringMod.MOD_ID, value = Dist.CLIENT)
public class ClientSoundHandler {
    private static int heartbeatCooldown = 0;
    private static int breathingCooldown = 0;
    private static int windCooldown = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.isPaused()) return;

        float o2 = ClientMountainData.getO2Current();
        float stamina = ClientMountainData.getStamina();
        double meters = AltitudeHelper.yToMeters(mc.player.getY());
        float weather = ClientMountainData.getWeatherIntensity();

        if (heartbeatCooldown > 0) heartbeatCooldown--;
        if (breathingCooldown > 0) breathingCooldown--;
        if (windCooldown > 0) windCooldown--;

        if (o2 < 25 && heartbeatCooldown <= 0) {
            float volume = (25f - o2) / 25f * 0.6f;
            mc.player.playSound(ModSounds.HEARTBEAT.get(), volume, 0.8f + (o2 / 25f) * 0.4f);
            heartbeatCooldown = o2 < 10 ? 20 : 40;
        }

        if (stamina < 30 && breathingCooldown <= 0) {
            float volume = (30f - stamina) / 30f * 0.5f;
            mc.player.playSound(ModSounds.HEAVY_BREATHING.get(), volume, 1.0f);
            breathingCooldown = 60;
        }

        double altFraction = AltitudeHelper.getAltitudeFraction(meters);
        if (altFraction > 0.3 && windCooldown <= 0) {
            float volume = (float) altFraction * 0.4f + (weather / 100f) * 0.3f;
            mc.level.playLocalSound(
                    mc.player.getX(), mc.player.getY(), mc.player.getZ(),
                    ModSounds.WIND_AMBIENT.get(), SoundSource.AMBIENT,
                    volume, 0.7f + (float) (Math.random() * 0.3), false);
            windCooldown = 100 + (int) (Math.random() * 60);
        }
    }
}
