package com.mountaineering.client.render;

import com.mountaineering.MountaineeringMod;
import com.mountaineering.client.ClientMountainData;
import com.mountaineering.system.AltitudeHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MountaineeringMod.MOD_ID, value = Dist.CLIENT)
public class WeatherEffectsRenderer {

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        float weather = ClientMountainData.getWeatherIntensity();
        double meters = AltitudeHelper.yToMeters(mc.player.getY());
        float altFraction = (float) AltitudeHelper.getAltitudeFraction(meters);

        float fogFactor = (weather / 100f) * (0.5f + altFraction * 0.5f);
        if (fogFactor < 0.1f) return;

        float maxReduction = 0.85f;
        float reduction = fogFactor * maxReduction;

        float newEnd = Math.max(8f, event.getFarPlaneDistance() * (1f - reduction));
        float newStart = Math.max(2f, event.getNearPlaneDistance() * (1f - reduction * 0.5f));

        event.setFarPlaneDistance(newEnd);
        event.setNearPlaneDistance(newStart);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor event) {
        float weather = ClientMountainData.getWeatherIntensity();
        if (weather < 30) return;

        float whiteFactor = Math.min(1f, (weather - 30f) / 70f) * 0.6f;

        float r = (float) event.getRed();
        float g = (float) event.getGreen();
        float b = (float) event.getBlue();

        event.setRed(r + (0.9f - r) * whiteFactor);
        event.setGreen(g + (0.9f - g) * whiteFactor);
        event.setBlue(b + (0.95f - b) * whiteFactor);
    }
}
