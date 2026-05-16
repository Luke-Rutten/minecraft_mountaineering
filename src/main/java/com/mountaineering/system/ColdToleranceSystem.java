package com.mountaineering.system;

import com.mountaineering.config.MountaineeringConfig;
import com.mountaineering.data.PlayerMountainData;
import com.mountaineering.effect.ModEffects;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public class ColdToleranceSystem {

    public static void tick(Player player, PlayerMountainData data) {
        float bodyTemp = data.getBodyTemperature();
        float tolerance = data.getColdTolerance();

        float drainThresh = MountaineeringConfig.COLD_DRAIN_THRESHOLD.get().floatValue();
        float regenThresh = MountaineeringConfig.COLD_REGEN_THRESHOLD.get().floatValue();

        if (bodyTemp < drainThresh) {
            float severity = (drainThresh - bodyTemp) / drainThresh;
            float drain = MountaineeringConfig.COLD_DRAIN_RATE.get().floatValue() * (1f + severity);
            tolerance -= drain;
        } else if (bodyTemp > regenThresh) {
            float regen = MountaineeringConfig.COLD_REGEN_RATE.get().floatValue();
            if (data.isCachedSheltered() && data.isCachedNearHeater()) {
                regen *= 3f;
            } else if (data.isCachedSheltered()) {
                regen *= 1.5f;
            }
            tolerance += regen;
        }

        data.setColdTolerance(Mth.clamp(tolerance, 0f, 100f));

        if (data.getColdTolerance() <= 0f) {
            if (player.tickCount % MountaineeringConfig.COLD_DAMAGE_INTERVAL.get() == 0) {
                player.hurt(player.damageSources().freeze(),
                        MountaineeringConfig.COLD_DAMAGE_AMOUNT.get().floatValue());
            }

            data.setFrostbiteAccum(data.getFrostbiteAccum() + 1);
            if (data.getFrostbiteAccum() > 200) {
                if (!player.hasEffect(ModEffects.FROSTBITE.get())) {
                    player.addEffect(new MobEffectInstance(ModEffects.FROSTBITE.get(), 400, 0));
                }
            }
        } else {
            data.setFrostbiteAccum(Math.max(0, data.getFrostbiteAccum() - 2));
        }
    }
}
