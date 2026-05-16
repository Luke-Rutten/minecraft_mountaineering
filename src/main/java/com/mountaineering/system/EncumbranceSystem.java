package com.mountaineering.system;

import com.mountaineering.config.MountaineeringConfig;
import com.mountaineering.item.HeavyGearArmorItem;
import com.mountaineering.item.LightGearArmorItem;
import com.mountaineering.item.OxygenTankItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EncumbranceSystem {

    public static float computeTotalWeight(Player player) {
        float weight = 0;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.getItem() instanceof LightGearArmorItem) {
                weight += MountaineeringConfig.LIGHT_GEAR_WEIGHT.get().floatValue() / 4f;
            } else if (stack.getItem() instanceof HeavyGearArmorItem) {
                weight += MountaineeringConfig.HEAVY_GEAR_WEIGHT.get().floatValue() / 4f;
            }
        }

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof OxygenTankItem) {
                weight += MountaineeringConfig.TANK_WEIGHT.get().floatValue() * stack.getCount();
            }
        }

        return weight;
    }

    public static float computeWeightFactor(Player player) {
        float weight = computeTotalWeight(player);
        float freeCarry = MountaineeringConfig.FREE_CARRY_WEIGHT.get().floatValue();
        float scale = MountaineeringConfig.WEIGHT_SCALE_FACTOR.get().floatValue();

        if (weight <= freeCarry) return 1.0f;
        return 1.0f + (weight - freeCarry) * scale;
    }
}
