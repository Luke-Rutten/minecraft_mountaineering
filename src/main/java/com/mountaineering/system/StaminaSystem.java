package com.mountaineering.system;

import com.mountaineering.config.MountaineeringConfig;
import com.mountaineering.data.PlayerMountainData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class StaminaSystem {

    public static void tick(Player player, PlayerMountainData data, boolean jumped) {
        float stamina = data.getStamina();
        float o2 = data.getO2Current();

        float drain = 0;
        if (player.isSprinting()) {
            drain += MountaineeringConfig.STAMINA_SPRINT_DRAIN.get().floatValue();
        }
        if (player.onClimbable() && player.getDeltaMovement().y > 0) {
            drain += MountaineeringConfig.STAMINA_CLIMB_DRAIN.get().floatValue();
        }
        if (jumped) {
            drain += MountaineeringConfig.STAMINA_JUMP_DRAIN.get().floatValue();
        }

        drain *= EncumbranceSystem.computeWeightFactor(player);
        stamina -= drain;

        if (drain == 0) {
            float regen = MountaineeringConfig.STAMINA_REGEN_BASE.get().floatValue();
            regen *= (o2 / 100f);
            stamina += regen;
        }

        data.setStamina(Mth.clamp(stamina, 0f, 100f));

        if (data.getStamina() < MountaineeringConfig.STAMINA_SPRINT_THRESHOLD.get().floatValue()) {
            player.setSprinting(false);
        }
    }
}
