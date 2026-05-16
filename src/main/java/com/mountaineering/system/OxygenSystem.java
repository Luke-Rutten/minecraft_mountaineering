package com.mountaineering.system;

import com.mountaineering.config.MountaineeringConfig;
import com.mountaineering.data.PlayerMountainData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class OxygenSystem {

    public static void tick(Player player, PlayerMountainData data, boolean jumped) {
        double meters = AltitudeHelper.getMeters(player);

        float baseO2 = computeBaseO2(meters, data);

        if (AltitudeHelper.isDeathZone(meters)) {
            data.setDeathZoneTicks(data.getDeathZoneTicks() + 1);
            float decay = MountaineeringConfig.DEATH_ZONE_O2_DECAY.get().floatValue()
                    * data.getDeathZoneTicks();
            baseO2 -= decay;
        } else {
            data.setDeathZoneTicks(Math.max(0, data.getDeathZoneTicks() - 2));
        }

        baseO2 = Mth.clamp(baseO2, 0f, 100f);
        data.setO2Base(baseO2);

        float exertion = computeExertion(player, data, jumped);
        float current = data.getO2Current();

        current -= exertion;

        float recovery = MountaineeringConfig.O2_RECOVERY_RATE.get().floatValue();
        if (current < baseO2) {
            current = Math.min(current + recovery, baseO2);
        } else if (current > baseO2) {
            current = Math.max(current - recovery * 3f, baseO2);
        }

        data.setO2Current(Mth.clamp(current, 0f, 100f));

        if (data.getO2Current() <= MountaineeringConfig.O2_DAMAGE_THRESHOLD.get().floatValue()) {
            if (player.tickCount % MountaineeringConfig.O2_DAMAGE_INTERVAL.get() == 0) {
                player.hurt(player.damageSources().generic(),
                        MountaineeringConfig.O2_DAMAGE_AMOUNT.get().floatValue());
            }
        }
    }

    private static float computeBaseO2(double meters, PlayerMountainData data) {
        double metersMin = MountaineeringConfig.METERS_MIN.get();
        double deathZone = MountaineeringConfig.DEATH_ZONE_ALTITUDE.get();

        if (meters <= metersMin) return 100f;

        double gap = Math.max(0, meters - data.getAcclimatizedAlt());
        double maxGap = deathZone - metersMin;

        float factor = 1.0f - (float) (gap / maxGap) * 0.7f;
        float baseO2 = 100f * Mth.clamp(factor, 0.1f, 1.0f);

        if (data.isUsingOxygenTank()) {
            baseO2 += MountaineeringConfig.TANK_O2_BOOST.get().floatValue();
        }

        return Math.min(100f, baseO2);
    }

    private static float computeExertion(Player player, PlayerMountainData data, boolean jumped) {
        float exertion = 0;

        if (player.isSprinting()) {
            exertion += MountaineeringConfig.O2_EXERTION_SPRINT.get().floatValue();
        }

        if (jumped) {
            exertion += MountaineeringConfig.O2_EXERTION_JUMP.get().floatValue();
        }

        if (player.onClimbable()) {
            exertion += MountaineeringConfig.O2_EXERTION_CLIMB.get().floatValue();
        }

        exertion *= EncumbranceSystem.computeWeightFactor(player);

        return exertion;
    }
}
