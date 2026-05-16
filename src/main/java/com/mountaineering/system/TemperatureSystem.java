package com.mountaineering.system;

import com.mountaineering.config.MountaineeringConfig;
import com.mountaineering.data.PlayerMountainData;
import com.mountaineering.item.HeavyGearArmorItem;
import com.mountaineering.item.LightGearArmorItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TemperatureSystem {

    public static void tick(Player player, PlayerMountainData data, float weatherIntensity) {
        double meters = AltitudeHelper.getMeters(player);

        double fraction = AltitudeHelper.getAltitudeFraction(meters);
        float tempWarm = MountaineeringConfig.TEMP_AT_MIN_ALT.get().floatValue();
        float tempCold = MountaineeringConfig.TEMP_AT_MAX_ALT.get().floatValue();
        float baseTemp = tempWarm + (tempCold - tempWarm) * (float) fraction;

        float weatherPenalty = weatherIntensity * 0.2f;
        baseTemp -= weatherPenalty;

        if (data.isCachedSheltered()) {
            baseTemp += MountaineeringConfig.SHELTER_TEMP_BONUS.get().floatValue();
            if (data.isCachedNearHeater()) {
                baseTemp += MountaineeringConfig.HEATER_TEMP_BONUS.get().floatValue();
            }
        }

        baseTemp += getGearInsulation(player);

        float current = data.getBodyTemperature();
        float approachRate = 0.02f;
        float newTemp = current + (baseTemp - current) * approachRate;

        data.setBodyTemperature(Mth.clamp(newTemp, 0f, 100f));
    }

    public static float getGearInsulation(Player player) {
        float insulation = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.getItem() instanceof LightGearArmorItem) {
                insulation += MountaineeringConfig.LIGHT_GEAR_INSULATION.get().floatValue() / 4f;
            } else if (stack.getItem() instanceof HeavyGearArmorItem) {
                insulation += MountaineeringConfig.HEAVY_GEAR_INSULATION.get().floatValue() / 4f;
            }
        }
        return insulation;
    }
}
