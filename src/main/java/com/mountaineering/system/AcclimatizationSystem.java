package com.mountaineering.system;

import com.mountaineering.config.MountaineeringConfig;
import com.mountaineering.data.PlayerMountainData;
import net.minecraft.world.entity.player.Player;

public class AcclimatizationSystem {

    public static void onSleep(Player player, PlayerMountainData data) {
        double meters = AltitudeHelper.getMeters(player);
        double deathZone = MountaineeringConfig.DEATH_ZONE_ALTITUDE.get();

        if (meters >= deathZone) return;

        if (data.isUsingOxygenTank()) {
            float decay = MountaineeringConfig.ACCLIM_DECAY_RATE.get().floatValue();
            float metersMin = MountaineeringConfig.METERS_MIN.get().floatValue();
            data.setAcclimatizedAlt(Math.max(metersMin, data.getAcclimatizedAlt() - decay));
            return;
        }

        float gain = MountaineeringConfig.ACCLIM_GAIN_RATE.get().floatValue();
        float current = data.getAcclimatizedAlt();

        if (meters > current) {
            float target = (float) Math.min(meters, deathZone);
            data.setAcclimatizedAlt(Math.min(current + gain, target));
        }
    }
}
