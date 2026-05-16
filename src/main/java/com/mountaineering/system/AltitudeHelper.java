package com.mountaineering.system;

import com.mountaineering.config.MountaineeringConfig;
import net.minecraft.world.entity.player.Player;

public class AltitudeHelper {

    public static double getMeters(Player player) {
        return yToMeters(player.getY());
    }

    public static double yToMeters(double y) {
        int yMin = MountaineeringConfig.Y_MIN.get();
        int yMax = MountaineeringConfig.Y_MAX.get();
        double metersMin = MountaineeringConfig.METERS_MIN.get();
        double metersMax = MountaineeringConfig.METERS_MAX.get();

        double fraction = (y - yMin) / (double) (yMax - yMin);
        return metersMin + fraction * (metersMax - metersMin);
    }

    public static boolean isDeathZone(Player player) {
        return getMeters(player) >= MountaineeringConfig.DEATH_ZONE_ALTITUDE.get();
    }

    public static boolean isDeathZone(double meters) {
        return meters >= MountaineeringConfig.DEATH_ZONE_ALTITUDE.get();
    }

    public static double getAltitudeFraction(double meters) {
        double metersMin = MountaineeringConfig.METERS_MIN.get();
        double metersMax = MountaineeringConfig.METERS_MAX.get();
        return Math.max(0.0, Math.min(1.0, (meters - metersMin) / (metersMax - metersMin)));
    }

    public static String getWeatherTier(float effectiveIntensity) {
        int clearMax = MountaineeringConfig.WEATHER_CLEAR_MAX.get();
        int lightMax = MountaineeringConfig.WEATHER_LIGHT_SNOW_MAX.get();
        int heavyMax = MountaineeringConfig.WEATHER_HEAVY_SNOW_MAX.get();

        if (effectiveIntensity <= clearMax) return "Clear";
        if (effectiveIntensity <= lightMax) return "Light Snow";
        if (effectiveIntensity <= heavyMax) return "Heavy Snow";
        return "Blizzard";
    }
}
